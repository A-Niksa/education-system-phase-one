package logic.menus.services;

import logic.models.abstractions.Course;
import logic.models.roles.Professor;
import utils.database.data.CoursesDB;
import utils.timing.Weekday;
import utils.timing.WeeklyDate;

import java.util.LinkedList;

public class ProfessorScheduleLoader {
    public static LinkedList<WeeklyDate> getProfessorsCourseInformationPerDay(Professor targetProfessor, Weekday weekday) {
        LinkedList<Course> professorsCourses = getProfessorsCourses(targetProfessor);
        LinkedList<WeeklyDate> professorsCoursesInfoPerDay = new LinkedList<>();
        LinkedList<WeeklyDate> classDatesList;
        for (Course course : professorsCourses) {
            classDatesList = course.getClassDatesInWeek();
            for (WeeklyDate classDate : classDatesList) {
                if (classDate.getWeekday() == weekday) {
                    professorsCoursesInfoPerDay.add(classDate);
                }
            }
        }
        return professorsCoursesInfoPerDay;
    }

    public static LinkedList<Course> getProfessorsCourses(Professor targetProfessor) {
        LinkedList<Course> coursesList = CoursesDB.getList();
        LinkedList<Course> coursesOfProfessor = new LinkedList<>();
        for (Course course : coursesList) {
            if (professorTeachesCourse(targetProfessor, course)) {
                coursesOfProfessor.add(course);
            }
        }
        return coursesOfProfessor;
    }

    private static boolean professorTeachesCourse(Professor targetProfessor, Course course) {
        Professor teachingProfessor = course.getTeachingProfessor();

        String targetProfessorID = targetProfessor.getTeachingID();
        String teachingProfessorID = teachingProfessor.getTeachingID();

        return targetProfessorID.equals(teachingProfessorID);
    }
}
