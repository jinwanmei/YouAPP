package j.jave.kernal.streaming.kryo;

import com.esotericsoftware.kryo.Kryo;

import j.jave.kernal.jave.serializer.JSerializer;
import j.jave.kernal.jave.serializer.JSerializerFactory;

public class KryoSerializerFactory extends JSerializerFactory {

	private KryoGetter kryoGetter=new KryoGetter() {
		@Override
		void affactKryo(Class<?> clazz) {
		}
	};
	
	public void setKryoGetter(KryoGetter kryoGetter) {
		this.kryoGetter = kryoGetter;
	}
	
	@Override
	public JSerializer newSerializer(Class<?> clazz) {
		Kryo kryo=kryoGetter.getKryo(clazz);
		KryoSerializer serializer=new KryoSerializer(kryo);
		return serializer;
	}

}
