package logic.models.roles;

public class EducationDeputy extends Professor {
    public EducationDeputy(String firstName, String lastName, String nationalID, String phoneNumber, String emailAddress,
                           String password, String teachingID, int officeNumber, AcademicRank academicRank) {
        super(firstName, lastName, nationalID, phoneNumber, emailAddress, password, teachingID, officeNumber,
                academicRank, AdministrativeRole.EDUCATION_DEPUTY);
    }
}
