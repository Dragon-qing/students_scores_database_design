package mainview;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import sqlQuery.*;
import utils.JDBCUtils;

public class Mainview {
    /**
     * 用于str转Date
     */
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) throws SQLException, ClassNotFoundException, InterruptedException {

        Scanner input = new Scanner(System.in);
        int select;
        do {
            Thread.sleep(1000);
            menu();
            System.out.println("请输入需要的服务： ");
            select = input.nextInt();
            switch (select) {
                case 1:

                    //统计按照成绩分组的人数

                    Group h= new Group();
                    h.method();
                    break;
                case 2:
                    change();

                    break;
                case 3:
                    add();
                    break;
                case 4:
                    Delete();
                    break;
                case 5:

                     //查询各科最高分

                    MaxScoreOfCourse a = new MaxScoreOfCourse();
                    a.method();
                    break;
                case 6:

                    //各位同学总成绩

                    GetAllStuScore b = new GetAllStuScore();
                    b.method();
                    break;

                case 7:

                     //按照学号查询各科成绩

                    GetStuInfoById c = new GetStuInfoById();
                    c.method();
                    break;
                case 8:

                     //按照课程号查询成绩并降序排列
                    GetStudentScoreByCno d = new GetStudentScoreByCno();
                    d.method();
                    break;
                case 9:

                    //查询某班级的学生不及格学生的课程信息

                    GetFStudentByClass e = new GetFStudentByClass();
                    e.method();
                    break;
                case 10:
                    //查询某一科的平均成绩和方差
                    AVGByCno f = new AVGByCno();
                    f.method();
                    break;
                case 11:

                     //按照学号查询必修选修公选课的平均分

                    AVGById g = new AVGById();
                    g.method();
                    break;
                case 12:
                    System.out.println("期待您的下次使用,Bye~");
                default:
                    System.out.println("输入有误，请重新输入");
                    break;
            }
        } while (select != 12);
        input.close();
    }


    public static void menu() {
        System.out.println("*****************  欢迎使用本系统     *********************");
        System.out.println(" ");
        System.out.println("                  （1）分组                               ");
        System.out.println("                  （2）修改                               ");
        System.out.println("                  （3）增加                               ");
        System.out.println("                  （4）删除                               ");
        System.out.println("                  （5）查询每门课最高分                      ");
        System.out.println("                  （6）查询每个同学的总分                    ");
        System.out.println("                  （7）按照学号查询成绩                      ");
        System.out.println("                  （8）按照课程号查询学生成绩并降序排列         ");
        System.out.println("                  （9）按照学院和班级查询不及格学生            ");
        System.out.println("                  （10）按照课程号分别查询平均成绩和方差        ");
        System.out.println("                  （11）按照学号分别查询必修选修公选课的平均成绩 ");
        System.out.println("                  （12）退出                               ");
        System.out.println(" ");
        System.out.println("**************************************************************");
    }


    /**
     * 通过学号修改学生信息
     */
    public static void change() throws SQLException {
        Connection conn = null;
        //用于执行语句
        PreparedStatement name = null;
        PreparedStatement information = null;
        //用于接受结果
        ResultSet rs = null;


        Scanner input = new Scanner(System.in);
        try {
            conn = JDBCUtils.getConnection();
            //修改（SQL)
            String sql_change = "update Student set Sname = ?,Ssex = ?,Birthday = ? where Sno = ?";
            String sql = "select * from Student where Sno = ?";
            name = conn.prepareStatement(sql_change);
            information = conn.prepareStatement(sql);
            //名字
            String sname;
            String ssex;
            java.util.Date birthday;
            int id;
            System.out.println("请输入要修改的学生的学号：");
            id = input.nextInt();
            information.setInt(1, id);
            rs = information.executeQuery();
            if(!rs.next()){

                throw new SQLException("学生不存在！！！");
            }else{
                System.out.println("该学生的信息如下：");
                System.out.println("************************************");
                System.out.println("学号\t姓名\t性别\t生日");
                do {
                    id = rs.getInt("Sno");
                    sname = rs.getString("Sname");
                    ssex = rs.getString("Ssex");
                    birthday = rs.getDate("Birthday");
                    System.out.println(String.format("%-8d%-8s%-6s%-10s",id,sname,ssex,simpleDateFormat.format(birthday)));
                }while (rs.next());
            }

            System.out.println("************************************");
            System.out.println("请输入修改后的姓名：");
            sname = input.next();
            name.setString(1, sname);
            System.out.println("请输入修改后的性别：");
            ssex = input.next();
            name.setString(2, ssex);
            System.out.println("请输入修改后的生日：(格式为yyyy-MM-dd)");
            String s;
            s = input.next();

            //将字符串转换为Util.Date
            //parse方法转换
            birthday = simpleDateFormat.parse(s);
            name.setDate(3, new Date(birthday.getTime()));
            name.setInt(4, id);
            name.executeUpdate();
            System.out.println("修改成功");
            System.out.println("修改后的学生信息如下：");
            information.setInt(1, id);
            rs = information.executeQuery();
            System.out.println("************************************");
            System.out.println("学号\t姓名\t性别\t生日");
            while (rs.next()) {
                id = rs.getInt("Sno");
                sname = rs.getString("Sname");
                ssex = rs.getString("Ssex");
                birthday = rs.getDate("Birthday");
                System.out.println(id + "\t" + sname + "\t" + ssex + "\t" + simpleDateFormat.format(birthday));
            }
            System.out.println("************************************");
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            System.out.println("error！！！");
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (name != null){
                name.close();
            }
            if( information != null){
                information.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * 添加学生信息
     * @throws SQLException
     */
    public static void add() throws SQLException {
        Connection conn = null;
        //用于执行语句
        PreparedStatement name = null;
        PreparedStatement information = null;
        //用于接受结果
        ResultSet rs = null;

        Scanner input = new Scanner(System.in);

        try {
            conn = JDBCUtils.getConnection();
            //增加（SQL)
            String sql_add = "insert into Student(Sno,Sname,Ssex,Birthday) values(?,?,?,?)";
            String sql = "select * from Student where Sno = ?";
            name = conn.prepareStatement(sql_add);
            information = conn.prepareStatement(sql);
            String sname;//名字
            String ssex;
            java.util.Date birthday;
            int id;
            System.out.println("请输入要增加学生的学号：");
            id = input.nextInt();
            name.setInt(1, id);
            System.out.println("请输入要增加学生的姓名：");
            sname = input.next();
            name.setString(2, sname);
            System.out.println("请输入要增加学生的性别：");
            ssex = input.next();
            name.setString(3, ssex);
            System.out.println("请输入要增加学生的生日(格式yyyy-MM-dd)：");
            String s = input.next();
            //将字符串转换为Util.Date
            //parse方法转换
            birthday = simpleDateFormat.parse(s);
            name.setDate(4, new Date(birthday.getTime()));
            name.executeUpdate();

            System.out.println("增加成功");
            System.out.println("增加的学生信息如下：");
            information.setInt(1, id);
            rs = information.executeQuery();
            System.out.println("************************************");
            System.out.println("学号\t姓名\t性别\t生日");
            while (rs.next()) {
                id = rs.getInt("Sno");
                sname = rs.getString("Sname");
                ssex = rs.getString("Ssex");
                birthday = rs.getDate("Birthday");
                System.out.println(id + "\t" + sname + "\t" + ssex + "\t" + simpleDateFormat.format(birthday));
            }
            System.out.println("************************************");
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            System.out.println("数据连接错误！！！");
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (name != null){
                name.close();
            }
            if( information != null){
                information.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }


    /**
     * 通过学号删除学生信息
     * @throws SQLException
     */
    public static void Delete() throws SQLException {
        Connection conn = null;
        //用于执行语句
        PreparedStatement name = null;
        PreparedStatement information = null;
        //用于接受结果
        ResultSet rs = null;

        Scanner input = new Scanner(System.in);

        try {
            conn = JDBCUtils.getConnection();
            //删除（SQL)
            String sql_delete = "delete from Student where Sno = ?";
            String sql = "select * from Student";
            name = conn.prepareStatement(sql_delete);
            information = conn.prepareStatement(sql);
            int sure;
            String sname;//名字
            String ssex;
            java.util.Date birthday;
            int id;
            System.out.println("请输入要删除的学生的学号：");
            id = input.nextInt();
            System.out.println("请问你确定要删除吗？（1）Yes（2）No");
            sure = input.nextInt();
            if (1 == sure) {
                name.setInt(1, id);
                name.executeUpdate();
                System.out.println("删除成功！");
                System.out.println("剩余学生信息如下：");
                rs = information.executeQuery();
                System.out.println("************************************");
                System.out.println("学号\t姓名\t性别\t生日");
                while (rs.next()) {
                    id = rs.getInt("Sno");
                    sname = rs.getString("Sname");
                    ssex = rs.getString("Ssex");
                    birthday = rs.getDate("Birthday");
                    System.out.println(id + "\t" + sname + "\t" + ssex + "\t" + simpleDateFormat.format(birthday));
                }
                System.out.println("************************************");
            } else {
                System.out.println("已取消删除操作！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据连接错误！！！");
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (name != null){
                name.close();
            }
            if( information != null){
                information.close();
            }

            if (rs != null) {
                rs.close();
            }
        }
    }
}
