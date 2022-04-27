package logic.menus.addition;

import logic.models.abstractions.Course;
import logic.models.abstractions.Department;
import logic.models.roles.Professor;
import utils.database.data.ProfessorsDB;
import utils.timing.TimeInDay;
import utils.timing.Weekday;
import utils.timing.WeeklyDate;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

public class CourseConstruction {
    public static void constructCourse(String courseName, String courseID, String instructorsName, int numberOfCredits,
                                       String courseLevelString, String firstClassWeekdayString, int firstClassStartHour,
                                       int firstClassStartMinute, int firstClassEndHour, int firstClassEndMinute,
                                       String secondClassWeekdayString, int secondClassStartHour, int secondClassStartMinute,
                                       int secondClassEndHour, int secondClassEndMinute, Date selectedExamDate, int examHour,
                                       int examMinute, Department department) {
        LocalDateTime examTime = convertToLocalDateTime(selectedExamDate, examHour, examMinute);
        Course.CourseLevel courseLevel = getLevelEnum(courseLevelString);
        Professor teachingProfessor = ProfessorsDB.getProfessorWithName(instructorsName);
        LinkedList<WeeklyDate> classDatesInWeek = getClassDatesInWeek(firstClassWeekdayString, firstClassStartHour,
                firstClassStartMinute, firstClassEndHour, firstClassEndMinute, secondClassWeekdayString,
                secondClassStartHour, secondClassStartMinute, secondClassEndHour, secondClassEndMinute, courseName,
                instructorsName);

        Course newCourse = new Course(courseName, examTime, numberOfCredits, courseLevel, courseID, teachingProfessor,
                classDatesInWeek);
        department.addCourse(newCourse);
    }

    private static LocalDateTime convertToLocalDateTime(Date selectedDate, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(selectedDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        LocalDateTime defenseTime = LocalDateTime.of(year, month, day, hour, minute);
        return defenseTime;
    }

    private static LinkedList<WeeklyDate> getClassDatesInWeek(String firstClassWeekdayString, int firstClassStartHour,
                                                              int firstClassStartMinute, int firstClassEndHour,
                                                              int firstClassEndMinute, String secondClassWeekdayString,
                                                              int secondClassStartHour, int secondClassStartMinute,
                                                              int secondClassEndHour, int secondClassEndMinute,
                                                              String courseName, String instructorsName) {
        LinkedList<WeeklyDate> classDatesInWeek = new LinkedList<>();
        WeeklyDate firstWeeklyDate = getWeeklyDate(firstClassWeekdayString, firstClassStartHour, firstClassStartMinute,
                firstClassEndHour, firstClassEndHour, courseName, instructorsName);
        WeeklyDate secondWeeklyDate = getWeeklyDate(secondClassWeekdayString, secondClassStartHour, secondClassStartMinute,
                secondClassEndHour, secondClassEndMinute, courseName, instructorsName);
        classDatesInWeek.add(firstWeeklyDate);
        classDatesInWeek.add(secondWeeklyDate);
        return classDatesInWeek;
    }

    private static WeeklyDate getWeeklyDate(String weekdayString, int startHour, int startMinute, int endHour,
                                            int endMinute, String courseName, String instructorsName) {
        TimeInDay classStartTime = new TimeInDay(startHour, startMinute, 0);
        TimeInDay classEndTime = new TimeInDay(endHour, endMinute, 0);
        WeeklyDate weeklyDate = new WeeklyDate(getWeekdayEnum(weekdayString), classStartTime, classEndTime,
                courseName, instructorsName);
        return weeklyDate;
    }

    private static Weekday getWeekdayEnum(String enumString) {
        if (enumString.equals("Saturday")) {
            return Weekday.SATURDAY;
        } else if (enumString.equals("Sunday")) {
            return Weekday.SUNDAY;
        } else if (enumString.equals("Monday")) {
            return Weekday.MONDAY;
        } else if (enumString.equals("Tuesday")) {
            return Weekday.TUESDAY;
        } else if (enumString.equals("Wednesday")) {
            return Weekday.WEDNESDAY;
        } else if (enumString.equals("Thursday")) {
            return Weekday.THURSDAY;
        } else if (enumString.equals("Friday")) {
            return Weekday.FRIDAY;
        }
        return null;
    }

    public static Course.CourseLevel getLevelEnum(String enumString) {
        if (enumString.equals("Bachelors")) {
            return Course.CourseLevel.BACHELORS;
        } else if (enumString.equals("Graduate")) {
            return Course.CourseLevel.GRADUATE;
        } else if (enumString.equals("PhD")) {
            return Course.CourseLevel.PHD;
        }
        return null;
    }
}
