package logic.menus.enrolment;

import logic.models.abstractions.Course;
import logic.models.abstractions.Department;
import logic.models.roles.Professor;
import utils.database.data.DepartmentsDB;

import java.util.LinkedList;

public class DepartmentListManager {
    public static LinkedList<Course> getDepartmentCourses(Professor oneDepartmentProfessor) {
        Department department = DepartmentsDB.getProfessorsDepartment(oneDepartmentProfessor);
        LinkedList<Course> coursesList = (LinkedList<Course>) department.getListOfCourses().clone();
        return coursesList;
    }

    public static String[] getProfessorsNames(Professor oneDepartmentProfessor) {
        Department department = DepartmentsDB.getProfessorsDepartment(oneDepartmentProfessor);
        LinkedList<Professor> professorsList = department.getListOfProfessors();
        String[] professorsNames = new String[professorsList.size()];
        Professor professor;
        for (int i = 0; i < professorsList.size(); i++) {
            professor = professorsList.get(i);
            professorsNames[i] = professor.getFirstName() + " " + professor.getLastName();
        }
        return professorsNames;
    }

    public static LinkedList<Professor> getProfessorsListBarDean(Professor oneDepartmentProfessor) {
        Department department = DepartmentsDB.getProfessorsDepartment(oneDepartmentProfessor);
        LinkedList<Professor> professorsList = (LinkedList<Professor>) department.getListOfProfessors().clone();
        removeDeanFromList(professorsList);
        return professorsList;
    }

    private static void removeDeanFromList(LinkedList<Professor> professorsPerDepartment) {
        Professor professor;
        for (int i = 0; i < professorsPerDepartment.size(); i++) {
            professor = professorsPerDepartment.get(i);
            if (professor.getAdministrativeRole() == Professor.AdministrativeRole.DEAN) {
                professorsPerDepartment.remove(i);
                return;
            }
        }
    }
}
