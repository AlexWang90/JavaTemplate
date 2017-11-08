package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * Created by hzwangjian1 on 2017/11/8.
 */
public class ProperityHandler {
    private static Logger logger = LoggerFactory.getLogger(ProperityHandler.class);

    /**
     * 从外部文件读取properties
     * @param propertiesFilePath
     * @return
     */
    public static Properties loadPropertiesFromFile(String propertiesFilePath){
        Properties properties = new Properties();
        try {
            properties.load(new BufferedReader(new FileReader(new File(propertiesFilePath))));
        }catch (Exception e){
            logger.error("", e);
        }
        return properties;
    }


    /**
     * 从resources文件读取properties
     * @param resourceFileName
     * @return
     */
    public static Properties loadPropertiesFromResource(String resourceFileName){
        Properties properties = new Properties();
        try {
            InputStreamReader streamReader = new InputStreamReader(ProperityParameters.class.getClassLoader().getResourceAsStream(resourceFileName), "UTF-8");
            properties.load(streamReader);
            streamReader.close();
        } catch (IOException e) {
            logger.error("", e);
        }
        return properties;
    }

    public static void main(String[] args) {
        Properties propertiesFromOutterFile = loadPropertiesFromFile("E://temp/code/servers.properties");
        logger.info(propertiesFromOutterFile.getProperty("hostID"));

        Properties propertiesFromResource = loadPropertiesFromResource("servers.properties");
        logger.info(propertiesFromResource.getProperty("hostID"));
    }

}
