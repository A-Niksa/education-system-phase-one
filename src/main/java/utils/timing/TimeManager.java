package utils.timing;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeManager {
    public static String getTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd | HH:mm:ss");
        LocalDateTime currentTime = LocalDateTime.now();
        return dateTimeFormatter.format(currentTime);
    }
}
