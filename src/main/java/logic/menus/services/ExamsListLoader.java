package logic.menus.services;

import logic.models.abstractions.Course;
import logic.models.roles.Professor;
import logic.models.roles.Student;

import java.util.Collections;
import java.util.LinkedList;

import static logic.menus.services.ProfessorScheduleLoader.getProfessorsCourses;
import static logic.menus.services.StudentScheduleLoader.getStudentsCourses;

public class ExamsListLoader {
    private static ExamDateComparator comparator = new ExamDateComparator();

    public static LinkedList<Course> getSortedListOfCoursesPerExam(Professor targetProfessor) {
        LinkedList<Course> coursesPerProfessor = getProfessorsCourses(targetProfessor);
        Collections.sort(coursesPerProfessor, comparator);
        return coursesPerProfessor;
    }

    public static LinkedList<Course> getSortedListOfCoursesPerExam(Student targetStudent) {
        LinkedList<Course> coursesPerStudent = getStudentsCourses(targetStudent);
        Collections.sort(coursesPerStudent, comparator);
        return coursesPerStudent;
    }
}
