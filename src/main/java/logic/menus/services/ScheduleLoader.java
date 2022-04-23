package logic.menus.services;

import logic.models.abstractions.Course;
import logic.models.roles.Student;
import utils.database.data.CoursesDB;
import utils.timing.Weekday;
import utils.timing.WeeklyDate;

import java.util.LinkedList;

public class ScheduleLoader {
    public static LinkedList<WeeklyDate> getStudentsCourseInformationPerDay(Student targetStudent, Weekday weekday) {
        LinkedList<Course> studentsCourses = getStudentsCourses(targetStudent);
        LinkedList<WeeklyDate> studentsCoursesInfoPerDay = new LinkedList<>();
        LinkedList<WeeklyDate> classDatesList;
        for (Course course : studentsCourses) {
            classDatesList = course.getClassDatesInWeek();
            for (WeeklyDate classDate : classDatesList) {
                if (classDate.getWeekday() == weekday) {
                    studentsCoursesInfoPerDay.add(classDate);
                }
            }
        }
        return studentsCoursesInfoPerDay;
    }

    private static LinkedList<Course> getStudentsCourses(Student targetStudent) {
        LinkedList<Course> coursesList = CoursesDB.getList();
        LinkedList<Course> coursesPerTargetStudent = new LinkedList<>();
        for (Course course : coursesList) {
            if (studentIsInCourse(targetStudent, course)) {
                coursesPerTargetStudent.add(course);
            }
        }
        return coursesPerTargetStudent;
    }

    private static boolean studentIsInCourse(Student targetStudent, Course course) {
        LinkedList<Student> listOfStudentsInCourse = course.getListOfStudents();
        String targetStudentID = targetStudent.getStudentID();
        for (Student student : listOfStudentsInCourse) {
            if (student.getStudentID().equals(targetStudentID)) {
                return true;
            }
        }
        return false;
    }
}
