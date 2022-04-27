package logic.models.abstractions;

public class StudentStatus {
    private String studentID;
    private String courseName;
    private Double score;
    private boolean hasBeenScored;
    private boolean scoreIsFinalized;
    private String protestOfStudent;
    private String responseOfProfessor;

    public StudentStatus(String studentID, String courseName, Double score) {
        this.studentID = studentID;
        this.courseName = courseName;
        this.score = score;
        hasBeenScored = true;
        scoreIsFinalized = false;
        protestOfStudent = "";
        responseOfProfessor = "";
    }

    public StudentStatus(String studentID, String courseName) {
        this.studentID = studentID;
        this.courseName = courseName;
        hasBeenScored = false;
        scoreIsFinalized = false;
        protestOfStudent = "";
        responseOfProfessor = "";
    }

    public boolean hasBeenScored() {
        return hasBeenScored;
    }

    public void setHasBeenScored(boolean hasBeenScored) {
        this.hasBeenScored = hasBeenScored;
    }

    public void setScoreIsFinalized(boolean scoreIsFinalized) {
        this.scoreIsFinalized = scoreIsFinalized;
    }

    public String getScoreString() {
        return score == null ? "" : score + "";
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String scoreIsFinalizedString() {
        if (scoreIsFinalized) {
            return "Yes";
        } else {
            return "No";
        }
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