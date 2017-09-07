package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by hzwangjian1 on 2016/11/30.
 */
public class ProperityParameters {
    private static Logger logger = LoggerFactory.getLogger(ProperityParameters.class);
    public static boolean paramsLoaded = false;

    //数据库数据
    public static String hostID = "";
    public static String database = "";
    public static String username = "";
    public static String password = "";

    static{
        loadFromPropertiesFile("servers.properties");
    }

    //读取资源文件
    public static void loadFromPropertiesFile(String paramsFile) {

        try {
            if (paramsLoaded == true) {
                logger.info("ProperityParameters have been loaded!");
                return;
            }

            paramsLoaded = true;
            Properties properties = new Properties();
            try {
                InputStreamReader streamReader = new InputStreamReader(ProperityParameters.class.getClassLoader().getResourceAsStream(paramsFile), "UTF-8");
                properties.load(streamReader);
                streamReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            hostID = properties.getProperty("hostID");
            database = properties.getProperty("database");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
        }catch (Exception e){
            logger.error("", e);
        }
    }
}
