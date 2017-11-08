package util;

import algorithm.ProParameters;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by hzwangjian1 on 2017/9/14.
 */
public class C3p0Util {
    private static Logger logger = LoggerFactory.getLogger(C3p0Util.class);

    private static ComboPooledDataSource dataSource = null;

    static {
        //dataSource资源只能初始化一次
        dataSource = new ComboPooledDataSource();
        try {
            Properties properties = ProperityHandler.loadPropertiesFromFile(ProParameters.PropertiesFilePath);
            dataSource.setDriverClass("com.mysql.jdbc.Driver");
            String connectStr = String.format("jdbc:mysql://%s:%s/%s", properties.getProperty("hostip"), properties.getProperty("hostport"), properties.getProperty("database"));
            logger.info("mysql connect:" + connectStr);
            dataSource.setJdbcUrl(connectStr);
            dataSource.setPassword(properties.getProperty("password"));
            dataSource.setUser(properties.getProperty("username"));
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    /**
     * 释放连接
     *
     * @param connection
     */
    public static void releaseConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接
     *
     * @return
     * @throws SQLException
     */
    public synchronized static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * 关闭C3P0连接池
     * @return
     */
    public static boolean closeC3P0(){
        try {
            dataSource.close();
            return true;
        }catch (Exception e){
            logger.error("", e);
        }
        return false;
    }

    public static void main(String[] args) throws Exception{
        Connection con = C3p0Util.getConnection();
        logger.info("" + con.isClosed());
        con.close();
        dataSource.close();
    }

}
