package main.java.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hzwangjian1 on 2016/12/5.
 */
public class MainEntry {
    private static Logger logger = LoggerFactory.getLogger(MainEntry.class);

    public static void main(String[] args) {
        logger.info("main entry");

        String method = "";
        if(args.length>0){
            method = args[0].trim();
        }

        if(method.equals("method_one")){

        }
        else{
            logger.info("cannot recognize method:" + method);
        }
    }
}
