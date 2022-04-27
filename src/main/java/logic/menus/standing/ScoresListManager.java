package logic.menus.standing;

import logic.models.abstractions.Course;
import logic.models.abstractions.Department;
import logic.models.abstractions.StudentStatus;
import logic.models.abstractions.Transcript;
import logic.models.roles.Professor;
import logic.models.roles.Student;
import utils.database.data.CoursesDB;
import utils.database.data.DepartmentsDB;
import utils.database.data.StudentsDB;

import java.util.LinkedList;

public class ScoresListManager {
    public static String[] getProfessorsCoursesNames(Professor targetProfessor) {
        LinkedList<Course> professorsCoursesList = getProfessorsCourses(targetProfessor);
        String[] coursesNames = new String[professorsCoursesList.size()];
        for (int i = 0; i < professorsCoursesList.size(); i++) {
            coursesNames[i] = professorsCoursesList.get(i).getCourseName();
        }
        return coursesNames;
    }

    public static LinkedList<Course> getProfessorsCourses(Professor targetProfessor) {
        LinkedList<Course> professorsCoursesList = new LinkedList<>();
        LinkedList<Course> coursesList = CoursesDB.getList();
        String targetProfessorID = targetProfessor.getTeachingID();
        String courseInstructorID;
        for (Course course : coursesList) {
            courseInstructorID = course.getTeachingProfessor().getTeachingID();
            if (targetProfessorID.equals(courseInstructorID)) {
                professorsCoursesList.add(course);
            }
        }
        return professorsCoursesList;
    }

    public static LinkedList<StudentStatus> getStudentStatusesWithCourseName(String targetCourseName) {
        LinkedList<Course> coursesList = CoursesDB.getList();
        String potentialCourseName;
        for (Course course : coursesList) {
            potentialCourseName = course.getCourseName();
            if (potentialCourseName.equals(targetCourseName)) {
                return course.getStudentsStatusesList();
            }
        }
        return null;
    }

    public static void updateStudentsTranscript(StudentStatus studentStatus) {
        String studentID = studentStatus.getStudentID();
        Student student = StudentsDB.getStudentWithID(studentID);
        Department department = DepartmentsDB.getStudentsDepartment(student);
        String departmentName = department.getDepartmentName();
        Transcript transcript = student.getTranscript();

        Course course = CoursesDB.getCourseWithName(studentStatus.getCourseName(), departmentName);
        transcript.addCourse(course);

        student.updateInDatabase();
    }

    public static boolean allStudentsHaveScores(LinkedList<StudentStatus> studentStatusesListClone) {
        for (StudentStatus studentStatusClone : studentStatusesListClone) {
            if (studentStatusClone.getScore() == null) {
                return false;
            }
        }
        return true;
    }

    public static void mapAllStudentsToScores(LinkedList<StudentStatus> studentStatusesListClone) {
        String studentID;
        Student student;
        String departmentName;
        String courseName;
        Course course;
        Double score;
        for (StudentStatus studentStatusClone : studentStatusesListClone) {
            studentID = studentStatusClone.getStudentID();
            courseName = studentStatusClone.getCourseName();
            student = StudentsDB.getStudentWithID(studentID);
            departmentName = DepartmentsDB.getStudentsDepartment(student).getDepartmentName();
            course = CoursesDB.getCourseWithName(courseName, departmentName);
            score = studentStatusClone.getScore();
            course.mapStudentToScore(student, score);
        }
    }

    public static boolean allStudentsHaveBeenGivenScores(LinkedList<StudentStatus> studentStatusesList) {
        for (StudentStatus studentStatus : studentStatusesList) {
            if (studentStatus.getScore() == null) {
                return false;
            }
        }
        return true;
    }

    public static void finalizeAllScores(LinkedList<StudentStatus> studentStatusesList, String departmentName) {
        String courseName;
        Course course;
        String studentID;
        Student student;
        for (StudentStatus studentStatus : studentStatusesList) {
            courseName = studentStatus.getCourseName();
            course = CoursesDB.getCourseWithName(courseName, departmentName);
            studentID = studentStatus.getStudentID();
            student = StudentsDB.getStudentWithID(studentID);
            course.finalizeScore(student);
        }
    }
}