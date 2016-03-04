package j.jave.kernal.jave.random;

import j.jave.kernal.jave.support.detect.JFieldDetector;
import j.jave.kernal.jave.support.detect.JFieldInfoProvider.JFieldInfoGen;
import j.jave.kernal.jave.support.detect.JFieldOnSingleClassDetector;
import j.jave.kernal.jave.utils.JCollectionUtils;

import java.lang.reflect.Field;
import java.util.List;

public class JSimpleObjectPopulate  extends JAbstractFieldPopulate{
	
	@Override
	public void populate(final Object object) throws Exception {
		
		JFieldInfoGen<FieldRelated> fieldInfo=new JFieldInfoGen<FieldRelated>() {
			@Override
			public FieldRelated getInfo(Field field, Class<?> classIncudeField) {
				FieldRelated fieldRelated=new FieldRelated();
				fieldRelated.setField(field);
				fieldRelated.setRandom(getRandom(field,object.getClass()));
				fieldRelated.setObjectClass(object.getClass());
				return fieldRelated;
			}
		};
		JFieldDetector<FieldRelated> fieldDetect=new JFieldOnSingleClassDetector<FieldRelated>(fieldInfo);
		fieldDetect.detect(object.getClass());
		
		List<FieldRelated>  fieldRelateds=  fieldDetect.getFieldInfos();
		
		JCollectionUtils.each(fieldRelateds, new JCollectionUtils.CollectionCallback<FieldRelated>() {
			@Override
			public void process(FieldRelated element) throws Exception {
				
				JRandom<?> random=element.getRandom();
				if(random==null) return;
				Object value=null;
				if(random instanceof JFieldRandom<?>){
					value=((JFieldRandom<?>)random).random(element.getField(), element.getObjectClass());
				}
				else{
					value=random.random();
				}
				element.getField().set(object, value);
			}
		});
		
	}
	
	
	
	
}