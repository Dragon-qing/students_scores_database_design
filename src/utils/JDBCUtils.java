package utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 连接池工具类
 * @author wmx
 * @version 0.1
 */
public class JDBCUtils {
    private static DataSource ds;
    static {
        try {
            Properties pro=new Properties();
            pro.load(JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties"));
            ds= DruidDataSourceFactory.createDataSource(pro);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 返回连接对象
     */
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
    /**
     * 释放资源
     */
    public static void close(Connection conn, Statement stmt, ResultSet rs){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(stmt!=null){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(conn!=null){
            try {
                conn.close(); //归还连接
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 释放资源
     */
    public static void close(Connection conn, Statement stmt){
        close(conn,stmt,null);
    }
    /**
     * 获取连接池
     */
    public static DataSource getDataSource(){
        return ds;
    }
}
