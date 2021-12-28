package sqlQuery;

import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * 按照课程号查询学生成绩并降序排列
 */
public class GetStudentScoreByCno {
    public Connection conn ;
    public ResultSet rs = null;
    public PreparedStatement pstmt = null;
    public void method() {
        Scanner in = new Scanner(System.in);
        System.out.println("按照课程号查询学生成绩并降序排列");
        System.out.println("请输入您要查询的课程号：");
        String sc = in.next();
        String sql = "SELECT Student.Sname,course.Cname,S_C.grade\n" +
                "FROM Student,S_C,course\n" +
                "WHERE S_C.Sno=Student.Sno AND S_C.Cno=course.cno AND course.Cno=?\n" +
                "ORDER BY grade  DESC; ";
        try {
            conn = JDBCUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, sc);
            this.rs = pstmt.executeQuery();
            System.out.println("****************************************************************************");
            System.out.println("学生姓名"+"    "+"课程名"+"      "+"成绩");
            while(rs.next()) {
                String Sname = rs.getString("Sname");

                String Cname = rs.getString("Cname");

                int Grade = rs.getInt("grade");

                System.out.println(String.format("%-10s%-10s%-10s",Sname,Cname,Grade));
            }
            System.out.println("****************************************************************************");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(conn, pstmt, rs);
        }


    }
}
