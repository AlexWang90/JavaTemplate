package model;

import hdfs.HdfsOperate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import algorithm_test.sift.SoftQuantization;
import util.PathUtil;

/**
       _   __  __  _  __   __        __ ___,_ _,__   __,_
     /  \ | |/  _ \ \/ /   \ \  |\  | |  __  |  _  \| _  \
   /  _  \| |   __/\ \`     \ \|  \| |  (__| | | | |\__) |
 /_ /  \__|_|\_,__|/\_\      \____,_| \___,_|_| |_|  ,__|

 __   _   __  _  __            _  _,_ _,__,__  __,_
 /  \ | |/  _ \ \/ /     |` |\ | |/ __  |  _  \| _  \
 / `_ \| |   __/\ \`      \ \| \/ | (__| | | | |\__) |
 /_/  \_|_|\_,__|/\_\       \___,__|\___,_|_| |_| ,___|

 * Created by Alex Wang on 2016/12/5.
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
        else if (method.equalsIgnoreCase("test_curr_path")){
            logger.info("PathUtil.getCurrentDir:" + PathUtil.getCurrentDir());
        }
        else{
            logger.info("cannot recognize method:" + method);
        }
    }
}
