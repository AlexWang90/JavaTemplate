package spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Created by hzwangjian1 on 2017/5/16.
 */
public class SparkExample implements Serializable{
    private static Logger logger = LoggerFactory.getLogger(SparkExample.class);

    public void runTest() {
        SparkConf conf = new SparkConf();
        conf.set("spark.driver.maxResultSize", "8g");
        conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer");
        conf.set("spark.kryo.registrator", "spark.MyRegister");
        conf.set("spark.akka.frameSize", "1024");
        conf.set("spark.kryoserializer.buffer.mb", "1024");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> baseRddx = sc.textFile("hzwangjian1/docduplicate/toutiaoFeatureBaseOne/toutiaoFeatureRealtime_2017*");
        logger.info("size of baseRddx:" + baseRddx.count());
    }
}
