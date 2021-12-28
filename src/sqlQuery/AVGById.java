package sqlQuery;

import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * 按照学号分别查询必修选修公选课的平均成绩
 */
public class AVGById {
    public Connection conn = null;
    public ResultSet rs = null;
    PreparedStatement pstmt = null;
    public void method() {

        Scanner in = new Scanner(System.in);

        System.out.println("按照学号分别查询必修选修公选课的平均成绩");
        System.out.println("请输入您要查询的学号：");
        String sc = in.next();

        String sql = "SELECT TY,AVG(grade) as 平均成绩  \n" +
                "FROM S_C,course\n" +
                "WHERE S_C.Cno=course.Cno AND Sno=?\n" +
                "GROUP BY TY;";
        try{
            conn = JDBCUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, sc);
            rs = pstmt.executeQuery();
            System.out.println("****************************************************************************");
            System.out.println(String.format("%-10s%-10s", "课程种类","平均成绩"));
            while(rs.next()) {
                String TY = rs.getString("TY");
                int avg = rs.getInt("平均成绩");
                System.out.println(String.format("%-12s%-10d", TY, avg));
            }
            System.out.println("****************************************************************************");
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtils.close(conn, pstmt, rs);
        }


    }
}
