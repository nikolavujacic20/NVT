package ftn.ntp.util;
import java.text.SimpleDateFormat;
import java.util.Date;
public class DateConverter {

    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return sdf.format(date);
    }
}
