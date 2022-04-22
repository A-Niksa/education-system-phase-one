package logic.models.abstractions;

public class StudentStatus {
    private String studentID;
    private String courseName;
    private Double score;
    private boolean scoreIsFinalized;
    private String protestOfStudent;
    private String responseOfProfessor;

    public StudentStatus(String studentID, String courseName, Double score) {
        this.studentID = studentID;
        this.courseName = courseName;
        this.score = score;
        scoreIsFinalized = false;
        protestOfStudent = "";
        responseOfProfessor = "";
    }

    public void setScoreAsFinalized() {
        scoreIsFinalized = true;
    }

    public Double getScore() {
        return score;
    }

    public boolean scoreIsFinalized() {
        return scoreIsFinalized;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getProtestOfStudent() {
        return protestOfStudent;
    }

    public void setProtestOfStudent(String protestOfStudent) {
        this.protestOfStudent = protestOfStudent;
    }

    public String getResponseOfProfessor() {
        return responseOfProfessor;
    }

    public void setResponseOfProfessor(String responseOfProfessor) {
        this.responseOfProfessor = responseOfProfessor;
    }

    public String getStudentID() {
        return studentID;
    }
}