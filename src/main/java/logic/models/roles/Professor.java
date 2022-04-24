package logic.models.roles;

import logic.models.abstractions.Department;
import utils.database.data.DepartmentsDB;
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
                     String password, String teachingID, int officeNumber, AcademicRank academicRank,
                     AdministrativeRole administrativeRole) {
        super(firstName, lastName, nationalID, phoneNumber, emailAddress, password);
        this.teachingID = teachingID;
        this.officeNumber = officeNumber;
        this.academicRank = academicRank;
        this.administrativeRole = administrativeRole;
        ProfessorsDB.addToDatabase(this);
    }

    public String getDepartmentName() {
        Department department = DepartmentsDB.getProfessorsDepartment(this);
        return department.getDepartmentName();
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

    public String getAcademicRankString() {
        switch (academicRank) {
            case ASSISTANT:
                return "Assistant Professor";
            case ASSOCIATE:
                return "Associate Professor";
            case FULL:
                return "Full Professor";
        }
        return "";
    }

    public AcademicRank getAcademicRank() {
        return academicRank;
    }

    public void setAcademicRank(AcademicRank academicRank) {
        this.academicRank = academicRank;
    }

    public String getAdministrativeRoleString() {
        switch (administrativeRole) {
            case NORMAL:
                return "Normal Professor";
            case EDUCATION_DEPUTY:
                return "Education Deputy";
        }
        return "";
    }

    public AdministrativeRole getAdministrativeRole() {
        return administrativeRole;
    }

    public void setAdministrativeRole(AdministrativeRole administrativeRole) {
        this.administrativeRole = administrativeRole;
    }

    public void updateInDatabase() {
        ProfessorsDB.removeFromDatabase(this);
        ProfessorsDB.addToDatabase(this);
    }
}