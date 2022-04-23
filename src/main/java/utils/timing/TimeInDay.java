package utils.timing;

public class TimeInDay {
    private int hour;
    private int minute;
    private int second;

    public TimeInDay(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public String getTimeInDayString() {
        String hh = formatNumberAsTwoDigits(hour);
        String mm = formatNumberAsTwoDigits(minute);
        String ss = formatNumberAsTwoDigits(second);
        return hh + ":" + mm + ":" + ss;
    }

    public String formatNumberAsTwoDigits(int number) {
        if (number < 10) {
            return "0" + number;
        }
        return "" + number;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }
}
