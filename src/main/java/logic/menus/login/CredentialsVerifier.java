package logic.menus.login;

import logic.models.roles.Professor;
import logic.models.roles.Student;
import utils.database.data.ProfessorsDB;
import utils.database.data.StudentsDB;

import java.util.LinkedList;

public class CredentialsVerifier {
    public static LoggedInAccount checkCredentials(String username, String password) {
        LoggedInAccount studentLoginAccount = checkStudentCredentials(username, password);
        LoggedInAccount professorLoginAccount = checkProfessorCredentials(username, password);
        if (studentLoginAccount.credentialsIsValid) {
            return studentLoginAccount;
        } else if (professorLoginAccount.credentialsIsValid) {
            return professorLoginAccount;
        } else {
            return new LoggedInAccount(false, AccountType.NONE, null);
            // ^ studentLoginAccount and professorLoginAccount are null by now
            // however, for explicitness of the code, we recreated a null LoggedInAccount
        }
    }

    private static LoggedInAccount checkStudentCredentials(String username, String password) {
        LinkedList<Student> studentsList = StudentsDB.getList();
        for (Student student : studentsList) {
            if (username.equals(student.getStudentID()) && password.equals(student.getPassword())) {
                return new LoggedInAccount(true, AccountType.STUDENT, student);
            }
        }
        return new LoggedInAccount(false, AccountType.NONE, null);
    }

    private static LoggedInAccount checkProfessorCredentials(String username, String password) {
        LinkedList<Professor> professorsList = ProfessorsDB.getList();
        for (Professor professor : professorsList) {
            if (username.equals(professor.getTeachingID()) && password.equals(professor.getPassword())) {
                return new LoggedInAccount(true, AccountType.PROFESSOR, professor);
            }
        }
        return new LoggedInAccount(false, AccountType.NONE, null);
    }
}
