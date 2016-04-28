package api;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by luffarvante on 2016-04-28.
 */
public class DateTest {

    @Test
    public void test() {
        DateFormat d = new SimpleDateFormat("yyyy-MM-dd");
        String tomorrow = "2016-04-28";
        String today = "2016-04-29";
        Date tom;
        Date tod;
        try {
            tom = d.parse(tomorrow);
            tod = d.parse(today);

            System.out.println(tom.compareTo(tod));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
