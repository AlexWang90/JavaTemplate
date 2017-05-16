package spark;

import com.esotericsoftware.kryo.Kryo;
import org.apache.spark.serializer.KryoRegistrator;

/**
 * Created by hzwangjian1 on 2017/5/4.
 */
public class MyRegister implements KryoRegistrator {


    @Override
    public void registerClasses(Kryo kryo) {

        kryo.register(SparkExample.class);
        kryo.register(StreamExample.class);
    }
}
