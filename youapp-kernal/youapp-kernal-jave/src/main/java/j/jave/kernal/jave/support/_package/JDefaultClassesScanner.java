package j.jave.kernal.jave.support._package;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.utils.JClassPathUtils;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * scan all sub-classes , extends a super class or implements an interface, via passing a predefined class loader,otherwise uses the default
 * current thread class loader. 
 * And you can also provide the files from which you want to  load,to prevent loading files from the class path.
 * The default scanner delegates the tasks to {@link JJARDefaultScanner} or {@link JFileSystemDefaultScanner}.
 * @author J
 * @see  {@link JJARDefaultScanner} 
 * @see {@link JFileSystemDefaultScanner}
 */
public class JDefaultClassesScanner extends JClassesScanDefaultConfiguration implements JClassesScan , JClassesScanConfig {
	
	private static final JLogger LOGGER =JLoggerFactory.getLogger(JDefaultClassesScanner.class);
	
	private final Class<?> clazz;
	
	private Collection<File> files;
	
	/**
	 * @param clazz super-class, or super-interface
	 */
	public JDefaultClassesScanner(Class<?> clazz) {
		this(clazz,null);
	}
	
	/**
	 * default classed scanner.
	 * @param clazz super-class or super-interface
	 * @param files all files from which the scanner scan. if null, all files in the class path are loaded.
	 */
	public JDefaultClassesScanner(Class<?> clazz,Collection<File> files) {
		this.clazz=clazz;
		if(files==null){
			try{
				this.files= JClassPathUtils.getClassPathFilesFromSystem();
				
				// for web
				URL libUrl=Thread.currentThread().getContextClassLoader().getResource("../lib");
				LOGGER.info("expected to find [WEB-INF/lib] : "+ (libUrl==null?"NULL":libUrl.toString()));
				if(libUrl!=null){
					File file=new File(libUrl.toURI());
					if(file.isDirectory()){
						this.files.add(file);
					}
				}
				
				URI uri=Thread.currentThread().getContextClassLoader().getResource("").toURI();
				LOGGER.info("expected to find [WEB-INF/classes] : "+ (uri==null?"NULL":uri.toString()));
				File file=new File(uri);
				if(file.isDirectory()){
					this.files.add(file);
				}
			}catch(Exception e){
				
			}
			
		}
		else{
			this.files=files;
		}
	}
	
	
	
	@Override
	public Set<Class<?>> scan() {
		
		Set<Class<?>> classes=new HashSet<Class<?>>();
		for (Iterator<File> iterator = files.iterator(); iterator.hasNext();) {
			File classPathFile =  iterator.next();
			if(classPathFile.exists()){
				String fileName=classPathFile.getName();
				
				if(fileName.endsWith(".jar")){
					JJARDefaultScanner defaultScan=new JJARDefaultScanner(classPathFile);
					defaultScan.setClassLoader(classLoader);
					defaultScan.setIncludePackages(includePackages);
					classes.addAll(JClassesResolve.get().getSubClass(defaultScan, clazz));
				}
				else{
					JFileSystemDefaultScanner defaultScan=new JFileSystemDefaultScanner(classPathFile);
					defaultScan.setClassLoader(classLoader);
					defaultScan.setIncludePackages(includePackages);
					classes.addAll(JClassesResolve.get().getSubClass(defaultScan, clazz));
				}
			}
		}
		
		return classes;
	}

	
}