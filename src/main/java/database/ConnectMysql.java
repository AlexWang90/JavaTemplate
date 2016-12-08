package database;

import model.ProperityParameters;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by hzwangjian1 on 2016/8/13.
 */
public class ConnectMysql {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ConnectMysql.class);

    public final String driver = "com.mysql.jdbc.Driver";
    public Connection conn = null;
    public PreparedStatement pst = null;

    public void initConnection(){
//        sql.initConnection("10.122.134.200:3306", "recsys", "recsys", "oJyKfrdT9AI9");//连接数据库
        initConnection(ProperityParameters.hostID, ProperityParameters.database, ProperityParameters.username, ProperityParameters.password);
    }

    /**
     * 检查数据库是否正常连接
     */
    public void checkMysqlConnection(){
        try {
            if(conn == null || conn.isClosed()){
                initConnection();
            }
        }catch(Exception e){
            logger.error("sql connect error", e );
        }
    }

    public void initConnection(String hostID, String database, String username, String password){
        try {
            Class.forName(driver);//指定连接类型
            conn = DriverManager.getConnection("jdbc:mysql://"+hostID+"/"+database+"?user="+username+"&password="+password);
            logger.info("[ConnectMysql]-->initialize connection succeed.");
        }
        catch (Exception e) {
            logger.error("[ConnectMysql]-->initialize connection error.",e);
        }
    }

    public void closeConnection(){
        try {
            if(null!= conn) conn.close();
            if(null!= pst) pst.close();
            logger.info("[ConnectMysql]-->mysql connection closed.");
        } catch (SQLException e) {
            logger.error("[ConnectMysql]-->close connection error.",e);
        }
    }

    public static void main(String[] args) throws Exception {
        ConnectMysql sql = new ConnectMysql();
        sql.initConnection("localhost:3306","knowledge","root","");
        sql.closeConnection();
    }

}
