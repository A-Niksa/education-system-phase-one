package logic.models.roles;

public class Dean extends Professor {
    public Dean(String firstName, String lastName, String nationalID, String phoneNumber, String emailAddress,
                String password, String teachingID, int officeNumber, Professor.AcademicRank academicRank) {
        super(firstName, lastName, nationalID, phoneNumber, emailAddress, password, teachingID, officeNumber,
                academicRank, AdministrativeRole.DEAN);
    }
}
