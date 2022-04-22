package logic.models.roles;

import logic.models.abstractions.Course;
import logic.models.abstractions.Department;
import utils.database.data.StudentsDB;

import java.util.LinkedList;

public class Student extends User {
    public enum AcademicStatus {
        CURRENTLY_STUDYING, GRADUATED, DROPPED_OUT
    }

    private double totalGPA;
    private Professor advisingProfessor;
    private int yearOfEntry;
    private AcademicStatus academicStatus;

    public Student(String firstName, String lastName, String nationalID, String phoneNumber, String emailAddress,
                   String password, double totalGPA, Professor advisingProfessor, int yearOfEntry,
                   AcademicStatus academicStatus) {
        super(firstName, lastName, nationalID, phoneNumber, emailAddress, password);
        this.totalGPA = totalGPA;
        this.advisingProfessor = advisingProfessor;
        this.yearOfEntry = yearOfEntry;
        this.academicStatus = academicStatus;
        StudentsDB.addToDatabase(this);
    }

    public double getTotalGPA() {
        return totalGPA;
    }

    public void setTotalGPA(double totalGPA) {
        this.totalGPA = totalGPA;
    }

    public Professor getAdvisingProfessor() {
        return advisingProfessor;
    }

    public void setAdvisingProfessor(Professor advisingProfessor) {
        this.advisingProfessor = advisingProfessor;
    }

    public int getYearOfEntry() {
        return yearOfEntry;
    }

    public void setYearOfEntry(int yearOfEntry) {
        this.yearOfEntry = yearOfEntry;
    }

    public AcademicStatus getAcademicStatus() {
        return academicStatus;
    }

    public void setAcademicStatus(AcademicStatus academicStatus) {
        this.academicStatus = academicStatus;
    }
}