import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class test {
    public static void main(String[] args) throws ParseException {
        java.util.Date birthday;
        String s = "1111-10-2";
        //将字符串转换为Util.Date
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //parse方法转换
        birthday = simpleDateFormat.parse(s);

        System.out.println(birthday);
        String date = simpleDateFormat.format(new Date());
        System.out.println(date);

    }
}
