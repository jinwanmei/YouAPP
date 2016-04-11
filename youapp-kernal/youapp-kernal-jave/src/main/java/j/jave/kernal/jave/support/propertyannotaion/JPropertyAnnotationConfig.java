package j.jave.kernal.jave.support.propertyannotaion;

import java.util.ArrayList;
import java.util.List;

public class JPropertyAnnotationConfig {

	private List<? extends JPropertyAnnotationHandler> propertyAnnotationHandlers=new ArrayList<JPropertyAnnotationHandler>();
	
	private List<Class<?>> classIdentifiers=new ArrayList<Class<?>>(2);

	public List<? extends JPropertyAnnotationHandler> getPropertyAnnotationHandlers() {
		return propertyAnnotationHandlers;
	}

	public void setPropertyAnnotationHandlers(
			List<? extends JPropertyAnnotationHandler> propertyAnnotationHandlers) {
		this.propertyAnnotationHandlers = propertyAnnotationHandlers;
	}

	public List<Class<?>> getClassIdentifiers() {
		return classIdentifiers;
	}

	public void setClassIdentifiers(List<Class<?>> classIdentifiers) {
		this.classIdentifiers = classIdentifiers;
	}
	
	
	
}
