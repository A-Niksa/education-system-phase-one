package logic.menus.standing;

import logic.models.abstractions.Course;
import logic.models.abstractions.Department;
import logic.models.abstractions.StudentStatus;
import logic.models.roles.Professor;
import logic.models.roles.Student;
import utils.database.data.CoursesDB;
import utils.database.data.DepartmentsDB;

import java.util.LinkedList;

public class ScoresListMaster {
    public static String[] getDepartmentsCoursesNames(String departmentName) {
        Department department = DepartmentsDB.getDepartmentWithName(departmentName);
        LinkedList<Course> coursesListPerDepartment = department.getListOfCourses();
        String[] coursesNames = new String[coursesListPerDepartment.size()];
        for (int i = 0; i < coursesListPerDepartment.size(); i++) {
            coursesNames[i] = coursesListPerDepartment.get(i).getCourseName();
        }
        return coursesNames;
    }

    public static String[] getDepartmentsProfessorsNames(String departmentName) {
        Department department = DepartmentsDB.getDepartmentWithName(departmentName);
        LinkedList<Professor> professorsListPerDepartment = department.getListOfProfessors();
        String[] professorsNames = new String[professorsListPerDepartment.size()];
        Professor professor;
        for (int i = 0; i < professorsListPerDepartment.size(); i++) {
            professor = professorsListPerDepartment.get(i);
            professorsNames[i] = professor.getFirstName() + " " + professor.getLastName();
        }
        return professorsNames;
    }

    public static String[] getDepartmentsStudentsIDs(String departmentName) {
        Department department = DepartmentsDB.getDepartmentWithName(departmentName);
        LinkedList<Student> studentsListPerDepartment = department.getListOfStudents();
        String[] studentsIDs = new String[studentsListPerDepartment.size()];
        for (int i = 0; i < studentsListPerDepartment.size(); i++) {
            studentsIDs[i] = studentsListPerDepartment.get(i).getStudentID();
        }
        return studentsIDs;
    }

    public static LinkedList<StudentStatus> getStudentStatusesWithProfessorName(String targetProfessorName) {
        LinkedList<StudentStatus> studentStatusesPerProfessor = new LinkedList<>();
        LinkedList<Course> coursesList = CoursesDB.getList();
        Professor professor;
        String professorName;
        for (Course course : coursesList) {
            professor = course.getTeachingProfessor();
            professorName = professor.getFirstName() + " " + professor.getLastName();
            if (professorName.equals(targetProfessorName)) {
                studentStatusesPerProfessor.addAll(course.getStudentsStatusesList());
            }
        }
        return studentStatusesPerProfessor;
    }

    public static LinkedList<StudentStatus> getStudentStatusesWithStudentName(String targetStudentID) {
        LinkedList<StudentStatus> studentStatusesPerStudent = new LinkedList<>();
        LinkedList<StudentStatus> studentStatusesPerCourse;
        LinkedList<Course> coursesList = CoursesDB.getList();
        for (Course course : coursesList) {
            studentStatusesPerCourse = course.getStudentsStatusesList();
            for (StudentStatus studentStatus : studentStatusesPerCourse) {
                if (studentStatus.getStudentID().equals(targetStudentID)) {
                    studentStatusesPerStudent.add(studentStatus);
                    break;
                }
            }
        }
        return studentStatusesPerStudent;
    }
}
