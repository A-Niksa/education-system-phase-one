package logic.menus.login;

import logic.models.roles.Professor;
import logic.models.roles.Student;
import utils.database.data.ProfessorsDB;
import utils.database.data.StudentsDB;

import java.util.LinkedList;

public class CredentialsVerifier {
    public static LoggedInAccount checkCredentials(String username, String password) {
        boolean isValidStudentLogin = checkStudentCredentials(username, password);
        boolean isValidProfessorLogin = checkProfessorCredentials(username, password);
        if (isValidStudentLogin) {
            return new LoggedInAccount(true, AccountType.STUDENT);
        } else if (isValidProfessorLogin) {
            return new LoggedInAccount(true, AccountType.PROFESSOR);
        } else {
            return new LoggedInAccount(false, AccountType.NONE);
        }
    }

    private static boolean checkStudentCredentials(String username, String password) {
        LinkedList<Student> studentsList = StudentsDB.getList();
        for (Student student : studentsList) {
            System.out.println(student.getStudentID() + " " + student.getPassword());
            if (username.equals(student.getStudentID()) && password.equals(student.getPassword())) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkProfessorCredentials(String username, String password) {
        LinkedList<Professor> professorsList = ProfessorsDB.getList();
        for (Professor professor : professorsList) {
            if (username.equals(professor.getTeachingID()) && password.equals(professor.getPassword())) {
                return true;
            }
        }
        return false;
    }
}
