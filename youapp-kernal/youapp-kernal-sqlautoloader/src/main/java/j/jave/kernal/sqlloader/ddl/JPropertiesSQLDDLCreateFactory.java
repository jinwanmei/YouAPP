package j.jave.kernal.sqlloader.ddl;

import j.jave.kernal.sqlloader.JDBConfig;
import j.jave.kernal.sqlloader.JDBConfigNames;
import j.jave.kernal.sqlloader.JSQLLoaderException;
import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;
import me.bunny.kernel._c.utils.JStringUtils;

import java.io.InputStream;


/**
 * create concrete <code>JSQLDDLCreate</code> object from properties.
 * see {@link JDBConfigNames} to know some property-value. 
 * @author J
 * @see JH2DBSQLDDLCreate
 * @see JDBConfigNames
 */
public class JPropertiesSQLDDLCreateFactory extends JAbstractSQLDDLCreateFactory implements JDBConfigNames{

	private static final JLogger LOGGER=JLoggerFactory.getLogger(JPropertiesSQLDDLCreateFactory.class);
	
	@Override
	public JSQLDDLCreate getObject() {
		try {
			
			JDBConfig configuration= parse(null);
			String driver=configuration.getDriver();
			String url=configuration.getUrl();
			String userName=configuration.getUserName();
			String password=configuration.getPassword();
			boolean auto=configuration.isAuto();
			String dialect=configuration.getDialect();
			
			if(!auto){
				return new JEmptySQLDDLCreate(driver, url, userName, password);
			}
			
			if(dialect!=null&&H2.equals(dialect.trim())){
				// h2
				JH2DBSQLDDLCreate h2=new JH2DBSQLDDLCreate(driver, url, userName, password);
				if(JStringUtils.isNotNullOrEmpty(jarName)){
					h2.setJarName(jarName);
				}
				if(JStringUtils.isNotNullOrEmpty(packageName)){
					h2.setPackageName(packageName);
				}
				return h2;
			}
			
		} catch (Exception e) {
			LOGGER.error("in create ddl sql creating factory", e);
			throw new JSQLLoaderException(e);
		}
		return null;
	}

	@Override
	public JDBConfig parse(InputStream inputStream) {
		return new JDBConfig().parse(inputStream);
	}

}
