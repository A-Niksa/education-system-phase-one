package logic.models.roles;

import logic.models.abstractions.Course;
import logic.models.abstractions.Department;
import logic.models.abstractions.StudentStatus;
import logic.models.abstractions.Transcript;
import utils.database.data.CoursesDB;
import utils.database.data.DepartmentsDB;
import utils.database.data.StudentsDB;
import utils.logging.LogIdentifier;
import utils.logging.MasterLogger;

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
    private Transcript transcript;

    public Student(String firstName, String lastName, String nationalID, String phoneNumber, String emailAddress,
                   String studentID, String password, Professor advisingProfessor, int yearOfEntry,
                   AcademicStatus academicStatus, SoughtDegree soughtDegree) {
        super(firstName, lastName, nationalID, phoneNumber, emailAddress, password);
        this.studentID = studentID;
        this.advisingProfessor = advisingProfessor;
        this.yearOfEntry = yearOfEntry;
        this.academicStatus = academicStatus;
        this.soughtDegree = soughtDegree;
        transcript = new Transcript();
        StudentsDB.addToDatabase(this);
    }

    public String getStudentID() {
        return studentID;
    }

    public int getPassedCredits() {
        LinkedList<String> passedCoursesIDs = transcript.getPassedCoursesIDs();
        int passedCredits = 0;
        Course course;
        StudentStatus studentStatus;
        for (String courseID : passedCoursesIDs) {
            course = CoursesDB.getCourseWithID(courseID);
            studentStatus = course.getStudentsStatus(this);
            if (studentStatus.scoreIsFinalized()) {
                passedCredits += course.getNumberOfCredits();
            }
        }
        return passedCredits;
    }

    public String getTotalGPAString() {
        double GPA = getTotalGPA();
        if (GPA == -1) {
            return "N/A";
        }
        return String.valueOf(GPA);
    }

    public double getTotalGPA() {
        LinkedList<String> passedCoursesIDs = transcript.getPassedCoursesIDs();
        StudentStatus studentStatus;
        double scorePerCourse;
        double totalScore = 0;
        int totalWeight = 0;
        Course course;
        for (String courseID : passedCoursesIDs) {
            course = CoursesDB.getCourseWithID(courseID);
            studentStatus = course.getStudentsStatus(this);
            if (studentStatus.scoreIsFinalized()) {
                scorePerCourse = studentStatus.getScore();
                totalScore += scorePerCourse * course.getNumberOfCredits();
                totalWeight += course.getNumberOfCredits();
            }
        }
        double GPA;
        if (totalWeight != 0) {
            GPA = totalScore / totalWeight;
        } else {
            MasterLogger.log("no courses have been passed", LogIdentifier.ERROR, "getTotalGPA",
                    "logic.models.roles.Student");
            GPA = -1;
        }
        setTotalGPA(GPA);
        return GPA;
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
                return "Currently Studying";
            case GRADUATED:
                return "Graduated";
            case DROPPED_OUT:
                return "Dropped Out";
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

    public String getSoughtDegreeString() {
        switch (soughtDegree) {
            case BACHELORS:
                return "Bachelors";
            case GRADUATE:
                return "Graduate";
            case PHD:
                return "PhD";
        }
        return "";
    }

    public SoughtDegree getSoughtDegree() {
        return soughtDegree;
    }

    public void setSoughtDegree(SoughtDegree soughtDegree) {
        this.soughtDegree = soughtDegree;
    }

    public String getDepartmentName() {
        Department department = DepartmentsDB.getStudentsDepartment(this);
        return department.getDepartmentName();
    }

    public Transcript getTranscript() {
        return transcript;
    }

    public LinkedList<StudentStatus> getTemporaryAcademicStatuses() {
        LinkedList<StudentStatus> temporaryAcademicStatuses = new LinkedList<>();
        Course course;
        StudentStatus studentStatus;
        for (String courseID : transcript.getPassedCoursesIDs()) {
            course = CoursesDB.getCourseWithID(courseID);
            studentStatus = course.getStudentsStatus(this);
            if (!studentStatus.scoreIsFinalized()) {
                temporaryAcademicStatuses.add(studentStatus);
            }
        }
        return temporaryAcademicStatuses;
    }
}