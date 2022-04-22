package logic.models.roles;

import logic.models.abstractions.Course;
import logic.models.abstractions.Department;
import utils.database.data.StudentsDB;

import java.util.LinkedList;

public class Student extends User {
    public enum AcademicStatus {
        CURRENTLY_STUDYING, GRADUATED, DROPPED_OUT
    }

    public enum SoughtDegree {
        BACHELORS, GRADUATE, PHD
    }

    private String studentID;
    private double totalGPA;
    private Professor advisingProfessor;
    private int yearOfEntry;
    private AcademicStatus academicStatus;
    private SoughtDegree soughtDegree;

    public Student(String firstName, String lastName, String nationalID, String phoneNumber, String emailAddress,
                   String studentID, String password, double totalGPA, Professor advisingProfessor, int yearOfEntry,
                   AcademicStatus academicStatus, SoughtDegree soughtDegree) {
        super(firstName, lastName, nationalID, phoneNumber, emailAddress, password);
        this.studentID = studentID;
        this.totalGPA = totalGPA;
        this.advisingProfessor = advisingProfessor;
        this.yearOfEntry = yearOfEntry;
        this.academicStatus = academicStatus;
        this.soughtDegree = soughtDegree;
        StudentsDB.addToDatabase(this);
    }

    public String getStudentID() {
        return studentID;
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

    public String getAcademicStatusString() {
        switch (academicStatus) {
            case CURRENTLY_STUDYING:
                return "Currently studying";
            case GRADUATED:
                return "Graduated";
            case DROPPED_OUT:
                return "Dropped out";
        }
        return "";
    }

    public AcademicStatus getAcademicStatus() {
        return academicStatus;
    }

    public void setAcademicStatus(AcademicStatus academicStatus) {
        this.academicStatus = academicStatus;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public SoughtDegree getSoughtDegree() {
        return soughtDegree;
    }

    public void setSoughtDegree(SoughtDegree soughtDegree) {
        this.soughtDegree = soughtDegree;
    }
}