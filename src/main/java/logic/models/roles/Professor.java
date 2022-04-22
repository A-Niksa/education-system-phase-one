package logic.models.roles;

import logic.models.abstractions.Department;
import utils.database.data.ProfessorsDB;

public class Professor extends User {
    public enum AcademicRank {
        ASSISTANT, ASSOCIATE, FULL
        // ostadyar, daneshyar, ostade tamam
    }

    public enum AdministrativeRole {
        NORMAL, EDUCATION_DEPUTY, DEAN
    }

    private String teachingID;
    private int officeNumber;
    private AcademicRank academicRank;
    private AdministrativeRole administrativeRole;

    public Professor(String firstName, String lastName, String nationalID, String phoneNumber, String emailAddress,
                     String password, String teachingID, int officeNumber,AcademicRank academicRank,
                     AdministrativeRole administrativeRole) {
        super(firstName, lastName, nationalID, phoneNumber, emailAddress, password);
        this.teachingID = teachingID;
        this.officeNumber = officeNumber;
        this.academicRank = academicRank;
        this.administrativeRole = administrativeRole;
        ProfessorsDB.addToDatabase(this);
    }

    public String getTeachingID() {
        return teachingID;
    }

    public void setTeachingID(String teachingID) {
        this.teachingID = teachingID;
    }

    public int getOfficeNumber() {
        return officeNumber;
    }

    public void setOfficeNumber(int officeNumber) {
        this.officeNumber = officeNumber;
    }

    public AcademicRank getAcademicRank() {
        return academicRank;
    }

    public void setAcademicRank(AcademicRank academicRank) {
        this.academicRank = academicRank;
    }
}