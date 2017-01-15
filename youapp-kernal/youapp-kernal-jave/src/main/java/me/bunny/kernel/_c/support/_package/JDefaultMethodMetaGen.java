package me.bunny.kernel._c.support._package;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import me.bunny.kernel._c.support._package.JMethodInfoProvider.JMethodInfoGen;

public class JDefaultMethodMetaGen implements JMethodInfoGen<JDefaultMethodMeta> {

	@Override
	public JDefaultMethodMeta getInfo(Method method, Class<?> classIncudeMethod) {
		JDefaultMethodMeta defaultMethodMeta=new JDefaultMethodMeta();
		defaultMethodMeta.setMethodName(method.getName());
		defaultMethodMeta.setAnnotations(method.getAnnotations());
		Parameter[] parameters=method.getParameters();
		JDefaultParamMeta[] defaultParamMetas=new JDefaultParamMeta[parameters.length];
		for(int i=0;i<parameters.length;i++){
			Parameter parameter=parameters[i];
			JDefaultParamMeta defaultParamMeta=new JDefaultParamMeta();
			defaultParamMeta.setIndex(i);
			defaultParamMeta.setName(parameter.getName());
			defaultParamMeta.setType(parameter.getType());
			defaultParamMetas[i]=defaultParamMeta;
		}
		defaultMethodMeta.setParamMetas(defaultParamMetas);
		return defaultMethodMeta;
	}
}