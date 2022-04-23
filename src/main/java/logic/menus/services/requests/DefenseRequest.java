package logic.menus.services.requests;

import logic.models.roles.Student;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class DefenseRequest extends Request {
    private Date selectedDate;
    private String selectedHour;
    private String selectedMinute;

    public DefenseRequest(Student requestingStudent, Date selectedDate, String selectedHour, String selectedMinute) {
        super(requestingStudent);
        this.selectedDate = selectedDate;
        this.selectedHour = selectedHour;
        this.selectedMinute = selectedMinute;
        requestType = RequestType.DEFENSE;
        attachDefenseTimeToStudent();
    }

    private void attachDefenseTimeToStudent() {
        LocalDateTime defenseTime = getLocalDateTimeOfDefense();
        requestingStudent.setDefenseTime(defenseTime);
    }

    public LocalDateTime getLocalDateTimeOfDefense() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(selectedDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = Integer.parseInt(selectedHour);
        int minute = Integer.parseInt(selectedMinute);

        LocalDateTime defenseTime = LocalDateTime.of(year, month, day, hour, minute);
        return defenseTime;
    }

    public void nullifyDefenseTime() {
        requestingStudent.setDefenseTime(null);
    }
}
