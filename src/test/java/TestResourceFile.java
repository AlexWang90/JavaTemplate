package test.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by hzwangjian1 on 2017/8/14.
 */
public class TestResourceFile {

    private static Logger logger = LoggerFactory.getLogger(TestResourceFile.class);

    public static void testResource()throws Exception {
        File file = new File(Test.class.getClassLoader().getResource("kmeansModel10000c_500i_17000000_format").getFile());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        int count = 0;
        while((line = reader.readLine())!= null){
            count ++;
        }
        logger.info("" + count);
        reader.close();
    }

    public static void main(String[] args) throws Exception{
        testResource();
    }
}
