package logic.menus.services;

import logic.models.abstractions.Course;
import logic.models.roles.Student;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;

import static logic.menus.services.ScheduleLoader.getStudentsCourses;

public class ExamsListLoader {
    public static LinkedList<Course> getSortedListOfCoursesPerExam(Student targetStudent) {
        LinkedList<Course> coursesPerStudent = getStudentsCourses(targetStudent);
        ExamDateComparator comparator = new ExamDateComparator();
        Collections.sort(coursesPerStudent, comparator);
        return coursesPerStudent;
    }
}
