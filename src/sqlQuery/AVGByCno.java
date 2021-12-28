package sqlQuery;
import utils.JDBCUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * 按照课程号分别查询平均成绩和方差
 */
public class AVGByCno {
    public Connection conn = null;
    public ResultSet rs = null;
    PreparedStatement pstmt = null;

    public void method() {
        Scanner in = new Scanner(System.in);
        System.out.println("按照课程号分别查询平均成绩和方差");
        System.out.println("请输入您要查询的课程号：");
        String sc = in.next();
        String sql = "SELECT course.Cname,AVG(grade)as 平均成绩,STDDEV(grade)as 方差\n" +
                "FROM S_C,course\n" +
                "WHERE course.Cno=S_C.Cno AND course.Cno=?\n" +
                "GROUP BY CName;";
        System.out.println(sql);
        try {
            conn = JDBCUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, sc);
            rs = pstmt.executeQuery();
            System.out.println("****************************************************************************");
            System.out.println("课程种类"+"\t\t"+"平均成绩"+"\t\t"+"方差");
            while(rs.next()) {
                String Cname = rs.getString("Cname");

                int avg = rs.getInt("平均成绩");
                double avr = rs.getDouble("方差");

                System.out.println(Cname+"\t\t"+avg+"\t\t\t"+avr);
            }
            System.out.println("****************************************************************************");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.close(conn, pstmt, rs);
        }

    }

}
