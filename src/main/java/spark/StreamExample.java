package spark;

import com.alibaba.fastjson.JSON;
import database.ToutiaoDataObject;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hzwangjian1 on 2017/5/16.
 */
public class StreamExample implements Serializable{
    private static Logger logger = LoggerFactory.getLogger(StreamExample.class);
    private static SimpleDateFormat simpleDateFormatNorm = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");

    public void test() {
        SparkConf conf = new SparkConf();
        conf.set("spark.driver.maxResultSize", "8g");
        conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer");
        conf.set("spark.kryo.registrator", "spark.MyRegister");
        conf.set("spark.akka.frameSize", "1024");
        conf.set("spark.kryoserializer.buffer.mb", "1024");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaStreamingContext ssc = new JavaStreamingContext(sc, Durations.minutes(2));

        JavaDStream<String> rddx = ssc.textFileStream("/user/recsys/hzwangjian1/streaming/input");
        JavaDStream<String> result = rddx.map(new Function<String, String>() {
            @Override
            public String call(String line) throws Exception {
                String result = "";
                try {
                    ToutiaoDataObject object = JSON.parseObject(line, ToutiaoDataObject.class);
                    result = object.getDocid() + "\t" + object.getTitle();
                    return result;
                } catch (Exception e) {
                    logger.error("", e);
                }
                return null;
            }
        }).filter(new NullFilter());


        result.foreachRDD(new Function<JavaRDD<String>, Void>() {
            @Override
            public Void call(JavaRDD<String> v1) throws Exception {
                if(v1.count() > 0) {
                    v1.saveAsTextFile("/user/recsys/hzwangjian1/streaming/output/" + simpleDateFormatNorm.format(new Date()));
                }
                return null;
            }
        });


        // Start the context
        ssc.start();
        ssc.awaitTermination();
    }

    /**
     * 过滤空的结果
     */
    class NullFilter implements Function<String, Boolean> {
        @Override
        public Boolean call(String v1) throws Exception {
            if (v1 == null || v1.trim().equals("")) {
                return false;
            }

            return true;
        }
    }

}
