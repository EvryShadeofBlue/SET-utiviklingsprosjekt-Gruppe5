import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) {

        Date currentDate = new Date();
        SimpleDateFormat dateTime = new SimpleDateFormat("dd.MM.yyyy HH.mm");
        String date = dateTime.format(currentDate);
        System.out.println(date);


    }
}
