package sqlQuery;

import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * 按照学院和班级查询不及格学生
 */
public class GetFStudentByClass {
    public Connection conn = null;
    public ResultSet rs = null;
    public PreparedStatement pstmt = null;
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public void method()  {
        Scanner in = new Scanner(System.in);
        System.out.println("按照学院和班级查询不及格学生");
        System.out.println("请输入您要查询的专业：");
        String sc = in.next();
        System.out.println("请输入您要查询的班级：");
        String sd = in.next();
        String sql = "SELECT Student.*,course.Cname,S_C.grade\n" +
                "FROM Student,S_C,course\n" +
                "WHERE Student.Sno=S_C.Sno AND course.Cno=S_C.Cno AND grade<60 AND Student.Academy=? AND Class=?";
        try {
            conn = JDBCUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,sc);
            pstmt.setString(2,sd);
            rs = pstmt.executeQuery();
            System.out.println("****************************************************************************");
            System.out.println(String.format("%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s", "学号","学生姓名","班级","性别","年龄","学院","宿舍","课程名","成绩"));
            while(rs.next()) {
                String sno = rs.getString("Sno");
                String sname = rs.getString("Sname");
                String Class = rs.getString("Class");
                String sex = rs.getString("Ssex");
                Date birthday = rs.getDate("Birthday");
                String academy = rs.getString("Academy");
                String dormitory = rs.getString("dorm");
                String Cname = rs.getString("Cname");
                int grade = rs.getInt("grade");

                System.out.println(String.format("%-12s%-10s%-10s%-8s%-12s%-8s%-11s%-10s%-10d",sno,sname,Class,sex,simpleDateFormat.format(birthday),academy,dormitory,Cname,grade));
            }
            System.out.println("****************************************************************************");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            JDBCUtils.close(conn, pstmt, rs);
        }


    }
}
