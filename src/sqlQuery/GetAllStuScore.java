package sqlQuery;

import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 查询每个同学的总成绩并降序排列
 */
public class GetAllStuScore {
    public Connection conn = null;
    public ResultSet rs = null;
    public PreparedStatement pstmt = null;
    public void method() {
        System.out.println("查询每个同学的总成绩并降序排列");

        String sql = "select Student.Sno,Student.Sname,sum(S_C.grade) as '总成绩'\n" +
                "from Student,S_C\n" +
                "where S_C.Sno=Student.Sno\n" +
                "group by Student.Sno,Student.Sname\n" +
                "order by sum(S_C.grade) desc;\n" +
                "\n";

        try {
            conn = JDBCUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            System.out.println("****************************************************************************");
            System.out.println("学号"+" "+"学生姓名"+" "+"总成绩");
            while(rs.next()) {
                String Sno = rs.getString("Sno");
                String Sname = rs.getString("Sname");
                int Sum = rs.getInt("总成绩");

                System.out.println(Sno+"\t"+Sname+"\t"+Sum);
            }
            System.out.println("****************************************************************************");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.close(conn, pstmt, rs);
        }

    }
}
