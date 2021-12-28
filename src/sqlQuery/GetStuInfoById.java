package sqlQuery;

import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * 按照学号查询学生成绩信息
 */
public class GetStuInfoById {
    public Connection conn = null;
    public ResultSet rs = null;
    public PreparedStatement pstmt = null;
    public void method()  {
        Scanner in = new Scanner(System.in);
        System.out.println("按照学号查询学生成绩信息");
        System.out.println("请输入您要查询的学号：");
        String sc = in.next();
        String sql = "SELECT Student.Sname , course.Cno , course.Cname , TY , S_C.grade  as 成绩 , S_C.Rgrade as 重修成绩 , S_C.Mgrade as 补考成绩\n" +
                "FROM  course , S_C , Student\n" +
                "WHERE  course.Cno=S_C.Cno AND S_C.Sno = Student.Sno AND Student.Sno=? ";

        try {
            conn = JDBCUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, sc);
            rs = pstmt.executeQuery();
            System.out.println("****************************************************************************");
            System.out.println("学生姓名"+"\t\t"+"课程号"+"\t\t"+"课程名称"+"\t\t"+"课程类型"+"\t\t"+"期末成绩"+"\t\t"+"重修成绩"+"\t\t"+"补考成绩");
            while(rs.next()) {
                String Sname = rs.getString("Sname");
                String Cno = rs.getString("Cno");
                String Cname = rs.getString("Cname");
                String TY = rs.getString("TY");
                int Grade = rs.getInt("成绩");
                int rgrade = rs.getInt("重修成绩");
                int mgrade = rs.getInt("补考成绩");
                System.out.println(String.format("%-10s%-12s%-10s%-10s%-12d%-12d%-10d",Sname, Cno, Cname, TY, Grade, rgrade, mgrade));
            }
            System.out.println("****************************************************************************");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(conn, pstmt, rs);
        }


    }
}
