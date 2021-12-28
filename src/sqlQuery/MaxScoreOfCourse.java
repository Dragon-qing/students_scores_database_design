package sqlQuery;
import utils.JDBCUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 查询每门课最高分
 */
public class MaxScoreOfCourse {
    public Connection conn = null;
    public ResultSet rs = null;
    public PreparedStatement pstmt = null;
    public void method()  {

        System.out.println("查询每门课最高分");
        String sql = "SELECT course.Cname, MAX(S_C.grade) as 最高分\n" +
                "FROM S_C,course\n" +
                "WHERE S_C.Cno=course.Cno\n" +
                "group by Cname ";
        try {
            conn = JDBCUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            System.out.println("****************************************************************************");
            System.out.println("课程名"+"\t\t"+"最高成绩");
            while(rs.next()) {
                String Cname = rs.getString("Cname");
                int max = rs.getInt("最高分");

                System.out.println(Cname+"\t\t"+max);
            }
            System.out.println("****************************************************************************");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.close(conn, pstmt, rs);
        }


    }
}
