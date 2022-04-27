package logic.menus.services;

import logic.models.abstractions.Course;

import java.time.LocalDateTime;
import java.util.Comparator;

public class ExamDateComparator implements Comparator<Course> {
    @Override
    public int compare(Course firstCourse, Course secondCourse) {
        LocalDateTime dateOfFirstExam = firstCourse.getExamTime();
        LocalDateTime dateOfSecondExam = secondCourse.getExamTime();
        return dateOfFirstExam.compareTo(dateOfSecondExam);
    }
}
