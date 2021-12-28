package sqlQuery;

import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 查询各个成绩分段的人数
 */
public class Group {
    public Connection conn = null;
    public ResultSet rs = null;
    public PreparedStatement pstmt = null;
    public void method() {
        System.out.println("查询各个成绩分段的人数");


        String sql = "select course.Cno , course.Cname , (\n" +
                "  case when S_C.grade >= 85 then '85-100'\n" +
                "       when  S_C.grade >= 70 and  S_C.grade < 85 then '70-85'\n" +
                "       when  S_C.grade>= 60 and  S_C.grade < 70 then '60-70'\n" +
                "       else '0-60'\n" +
                "  end) as 分数段, \n" +
                "  count(1)as 人数 \n" +
                "from course,S_C\n" +
                "where course.Cno = S_C.Cno \n" +
                "group by course.Cno , course.Cname , (\n" +
                "  case when  S_C.grade >= 85 then '85-100'\n" +
                "       when  S_C.grade >= 70 and  S_C.grade < 85 then '70-85'\n" +
                "       when  S_C.grade >= 60 and  S_C.grade < 70 then '60-70'\n" +
                "       else '0-60'\n" +
                "  end)\n" +
                "order by  course.Cno , course.Cname , 分数段";
        try{
            conn = JDBCUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            System.out.println("****************************************************************************");
            System.out.println("课程号"+"\t"+"课程名"+"\t"+"成绩分段"+"\t"+"人数");
            while(rs.next()) {
                String Cno = rs.getString("Cno");
                String Cname = rs.getString("Cname");
                String px = rs.getString("分数段");
                int count = rs.getInt("人数");
                System.out.println(Cno+"\t\t"+Cname+"\t"+px+"\t"+count);
            }
            System.out.println("****************************************************************************");
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCUtils.close(conn, pstmt, rs);
        }
    }
}
