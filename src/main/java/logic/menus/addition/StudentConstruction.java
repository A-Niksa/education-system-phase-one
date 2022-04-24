package logic.menus.addition;

import logic.models.abstractions.Department;
import logic.models.roles.Professor;
import logic.models.roles.Student;
import utils.database.data.ProfessorsDB;

public class StudentConstruction {
    public static void constructStudent(String studentID, String password, String nationalID, String firstName,
                                        String lastName, String phoneNumber, String emailAddress, int yearOfEntry,
                                        String academicStatusString, String soughtDegreeString,
                                        String advisingProfessorName, Department department) {
        Student.AcademicStatus academicStatus = getStatusEnum(academicStatusString);
        Student.SoughtDegree soughtDegree = getDegreeEnum(soughtDegreeString);
        Professor advisingProfessor = ProfessorsDB.getProfessorWithName(advisingProfessorName);

        Student newStudent = new Student(firstName, lastName, nationalID, phoneNumber, emailAddress, studentID, password,
                advisingProfessor, yearOfEntry, academicStatus, soughtDegree);
        department.addStudent(newStudent);
    }

    private static Student.AcademicStatus getStatusEnum(String enumString) {
        if (enumString.equals("Currently Studying")) {
            return Student.AcademicStatus.CURRENTLY_STUDYING;
        } else if (enumString.equals("Graduated")) {
            return Student.AcademicStatus.GRADUATED;
        } else if (enumString.equals("Dropped Out")) {
            return Student.AcademicStatus.DROPPED_OUT;
        }
        return null;
    }

    private static Student.SoughtDegree getDegreeEnum(String enumString) {
        if (enumString.equals("Bachelors")) {
            return Student.SoughtDegree.BACHELORS;
        } else if (enumString.equals("Graduate")) {
            return Student.SoughtDegree.GRADUATE;
        } else if (enumString.equals("PhD")) {
            return Student.SoughtDegree.PHD;
        }
        return null;
    }
}
