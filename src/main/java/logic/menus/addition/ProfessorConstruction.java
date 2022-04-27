package logic.menus.addition;

import logic.models.abstractions.Department;
import logic.models.roles.Professor;

public class ProfessorConstruction {
    public static void constructProfessor(String teachingID, String password, String nationalID, String firstName,
                                          String lastName, String phoneNumber, String emailAddress, int officeNumber,
                                          String academicRankString, Department department) {
        Professor.AcademicRank academicRank = getRankEnum(academicRankString);

        Professor newProfessor = new Professor(firstName, lastName, nationalID, phoneNumber, emailAddress, password,
                teachingID, officeNumber, academicRank, Professor.AdministrativeRole.NORMAL);
        department.addProfessor(newProfessor);
    }

    private static Professor.AcademicRank getRankEnum(String enumString) {
        if (enumString.equals("Assistant Professor")) {
            return Professor.AcademicRank.ASSISTANT;
        } else if (enumString.equals("Associate Professor")) {
            return Professor.AcademicRank.ASSOCIATE;
        } else if (enumString.equals("Full Professor")) {
            return Professor.AcademicRank.FULL;
        }
        return null;
    }
}
