package j.jave.framework.support.sqlloader;

import j.jave.framework.support._package.JFileClassPathDefaultScan;
import j.jave.framework.support._package.JJARDefaultScan;
import j.jave.framework.support._package.JPackageResolve;
import j.jave.framework.support.sqlloader.ddl.JSQLDDLCreate;
import j.jave.framework.support.sqlloader.ddl.JSQLDDLLoader;
import j.jave.framework.support.sqlloader.dml.JSQLDMLLoader;
import j.jave.framework.utils.JClassPathUtils;
import j.jave.framework.utils.JStringUtils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * basic class to execute SQL. 
 * extract common method. 
 * @author J
 *
 */
public abstract class JAbstractSQLCreate extends JDefaultSQLConfigure implements JSQLDDLCreate {

	protected final Logger LOGGER=LoggerFactory.getLogger(getClass());
	
	private final String className;
	private final String url;
	private final String user;
	private final String password;
	
	/**
	 * global scope .  
	 */
	protected List<String> tables=new ArrayList<String>(); 
	
	/**
	 * table records count. 
	 */
	protected Map<String, Integer> tableRecords=new HashMap<String, Integer>();
	
	/**
	 * for synchronized 
	 */
	private Object object=new Object();
	
	public JAbstractSQLCreate(String className,String url, String user, String password) {
		this.className=className;
		this.url=url;
		this.user=user;
		this.password=password;
	}
	
	
	@Override
	public void create() {
		Connection conn=null;
		Statement stat =null;
		try {  
            Class.forName(className);  
            conn = DriverManager.getConnection(url,  
                    user, password);  
            conn.setAutoCommit(false);  // off auto commit.  
            
            stat = conn.createStatement();  
            // initialize existing tables . 
            this.tables=existTables(conn);
            if(this.tables==null){
            	throw new IllegalStateException("only empty collections supported.");
            }
            
            if(tables!=null){
            	for(int i=0;i<tables.size();i++){
            		String tableName=tables.get(i);
            		ResultSet resultSet= stat.executeQuery("select count(1) cnt from "+tableName);
            		int count=Integer.MAX_VALUE;  // prevent executing DML . 
            		if(resultSet.next()){
            			count=resultSet.getInt("cnt");
            		}
            		synchronized(this.object){
            			if(!tableRecords.containsKey(tableName)) tableRecords.put(tableName, count);
            		}
            	}
            }
            
            LOGGER.info("ALL EXISTING TABLES : ");
            for (Iterator<String> iterator = tables.iterator(); iterator.hasNext();) {
				String string =  iterator.next();
				LOGGER.info(string);
			}
            
            // get sql laoder classes.  
            Set<Class<?>> classes = getJSQLLoaderClasses(null);
			
            //process on classes above
            if(classes!=null){
            	for (Iterator<Class<?>> iterator = classes.iterator(); iterator.hasNext();) {
					Class<?> clazz =iterator.next();
					List<String> sqls=new ArrayList<String>();
					if(JSQLDDLLoader.class.isAssignableFrom(clazz)){
						JSQLDDLLoader sqlddlLoader= (JSQLDDLLoader) clazz.newInstance();
						sqls=sqlddlLoader.load();
					}
					else if(JSQLDMLLoader.class.isAssignableFrom(clazz)){
						JSQLDMLLoader sqlddlLoader= (JSQLDMLLoader) clazz.newInstance();
						sqls=sqlddlLoader.load();
					}
					execute(stat, tables, sqls);
				}
            }
            conn.commit();
            LOGGER.info("load sql completely! ");
        } catch (Exception e) {  
        	LOGGER.error(" load sql: "+e.getMessage(), e);
        	try {
				conn.rollback();
			} catch (SQLException e1) {
				LOGGER.error("roll back exception! ",e);
			}
        }finally{
        	try {
        		if(stat!=null){
        			stat.close();
        		}
			} catch (SQLException e) {
				LOGGER.error("close statement ", e);
			}  
            try {
            	if(conn!=null){
            		conn.close();
            	}
			} catch (SQLException e) {
				LOGGER.error("close connection ", e);
			}  
        }

	}

	/**
	 * default to get all implements {@link JSQLDDLLoader},
	 * you can override the method to get ones from different super class . 
	 * @param clazz , default to {@link JSQLDDLLoader}.class if null.  
	 * @return
	 */
	protected Set<Class<?>> getJSQLLoaderClasses(Class<?> clazz) {
		
		if(clazz==null) clazz=JSQLDDLLoader.class;
		
		// scanning JSQLDDLLoader implementation. 
		ClassLoader classLoader=Thread.currentThread().getContextClassLoader();
		Set<Class<?>> classes=new HashSet<Class<?>>();
		
		Set<File> files= JClassPathUtils.getClassPathFilesFromSystem();
		for (Iterator<File> iterator = files.iterator(); iterator.hasNext();) {
			File classPathFile =  iterator.next();
			if(classPathFile.exists()){
				String fileName=classPathFile.getName();
				
				
				if(fileName.endsWith(".jar")){
					JJARDefaultScan defaultScan=new JJARDefaultScan(classPathFile);
					defaultScan.setClassLoader(classLoader);
					if(this.packageName!=null){
						defaultScan.setIncludePackages(new String[]{this.packageName});
					}
					classes.addAll(JPackageResolve.get().getImplements(defaultScan, clazz));
				}
				else{
					JFileClassPathDefaultScan defaultScan=new JFileClassPathDefaultScan(classPathFile);
					defaultScan.setClassLoader(classLoader);
					if(this.packageName!=null){
						defaultScan.setIncludePackages(new String[]{this.packageName});
					}
					classes.addAll(JPackageResolve.get().getImplements(defaultScan, clazz));
				}
			}
		}
		return classes;
	}
	
	private void execute(Statement stat, List<String> tables,
			List<String> sqls) throws SQLException {
		for (Iterator<String> iterator = sqls.iterator(); iterator.hasNext();) {
			String sql =  iterator.next();
			if(JStringUtils.isNullOrEmpty(sql)) continue;
			boolean canDo=validateOnSQL(sql);
			if(canDo){
				LOGGER.info("processed : "+sql);
				stat.execute(sql);
			}
		}
	}
	
	
	/**
	 * execute validation on the SQL. 
	 * @param sql
	 * @return true of can execute it, false if otherwise. 
	 */
	protected boolean validateOnSQL(String sql){
		return true;
	}
	
	
	/**
	 * return existing tables that had been created .
	 * important must not close the connection . 
	 * @return
	 */
	protected abstract List<String> existTables(Connection connection);

	/**
	 * check whether the jar file matches. 
	 * @param jarFile
	 * @return
	 */
	protected boolean matches(File jarFile){
		boolean valid=jarFile.getName().contains(jarName);
		return valid;
	}
	
	
}
