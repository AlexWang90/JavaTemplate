package model;

import hdfs.HdfsOperate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import algorithm_test.sift.SoftQuantization;

/**
 * Created by hzwangjian1 on 2016/12/5.
 */
public class MainEntry {
    private static Logger logger = LoggerFactory.getLogger(MainEntry.class);

    public static void main(String[] args)throws Exception{
        logger.info("main entry");

        String method = "";
        if(args.length>0){
            method = args[0].trim();
        }

        if(method.equals("method_one")){

        }
        else if(method.equalsIgnoreCase("test_sift_quantization_small")){
            SoftQuantization softQuantization = new SoftQuantization();
            softQuantization.testSoftQuantizationSmall();
        }
        else if(method.equalsIgnoreCase("test_sift_quantization_big")){
            SoftQuantization softQuantization = new SoftQuantization();
            softQuantization.testSoftQuantizationBig();
        }
        else{
            logger.info("cannot recognize method:" + method);
        }
    }
}
