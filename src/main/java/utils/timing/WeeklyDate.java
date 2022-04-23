package utils.timing;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class WeeklyDate {
    private Weekday weekday;
    private TimeInDay startTime;
    private TimeInDay endTime;
    private String courseName;
    private String professorName;

    public WeeklyDate(Weekday weekday, TimeInDay startTime, TimeInDay endTime, String courseName, String professorName) {
        this.weekday = weekday;
        this.startTime = startTime;
        this.endTime = endTime;
        this.courseName = courseName;
        this.professorName = professorName;
    }

    public Weekday getWeekday() {
        return weekday;
    }

    public String getStartTimeString() {
        return startTime.getTimeInDayString();
    }

    public TimeInDay getStartTime() {
        return startTime;
    }

    public String getEndTimeString() {
        return endTime.getTimeInDayString();
    }

    public TimeInDay getEndTime() {
        return endTime;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getProfessorName() {
        return professorName;
    }
}
