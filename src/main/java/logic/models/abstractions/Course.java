package logic.models.abstractions;

import logic.models.roles.Professor;
import logic.models.roles.Student;
import utils.database.data.CoursesDB;
import utils.database.data.DepartmentsDB;
import utils.timing.WeeklyDate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class Course {
    public enum CourseLevel {
        BACHELORS,
        GRADUATE,
        PHD
    }

    private String courseName;
    private LocalDateTime examTime;
    private Professor teachingProfessor;
    private int numberOfCredits;
    private CourseLevel courseLevel;
    private String courseID;
    private LinkedList<Student> listOfStudents;
    private LinkedList<StudentStatus> studentScores;
    private LinkedList<WeeklyDate> classDatesInWeek;

    public Course(String courseName, LocalDateTime examTime, int numberOfCredits, CourseLevel courseLevel,
                  String courseID, Professor teachingProfessor, LinkedList<WeeklyDate> classDatesInWeek) {
        this.courseName = courseName;
        this.examTime = examTime;
        this.numberOfCredits = numberOfCredits;
        this.courseLevel = courseLevel;
        this.courseID = courseID;
        this.teachingProfessor = teachingProfessor;
        this.classDatesInWeek = classDatesInWeek;
        listOfStudents = new LinkedList<>();
        studentScores = new LinkedList<>();
        CoursesDB.addToDatabase(this);
    }

    private void addStudentToStudentScoresList(Student student) {
        studentScores.add(new StudentStatus(student.getStudentID(), courseName));
    }

    public void mapStudentToScore(Student student, Double score) {
        removeDuplicateStudentScore(student);
        studentScores.add(new StudentStatus(student.getStudentID(), courseName, score));
        student.getTranscript().addCourse(this); // adding the course to the student's passed courses
        updateInDatabase();
        // here, we refer to passed courses as the ones that the student has taken, not the ones that he/she has necessarily passed
    }

    private void removeDuplicateStudentScore(Student student) {
        String targetStudentID = student.getStudentID();
        StudentStatus studentScore;
        for (int i = 0; i < studentScores.size(); i++) {
            studentScore = studentScores.get(i);
            if (studentScore.getStudentID().equals(targetStudentID)) {
                studentScores.remove(i);
                return;
            }
        }
    }

    public StudentStatus getStudentsStatus(Student targetStudent) {
        String studentID;
        Double targetStudentScore;
        for (StudentStatus studentStatus : studentScores) {
            studentID = studentStatus.getStudentID();
            if (studentID.equals(targetStudent.getStudentID())) {
                return studentStatus;
            }
        }
        return null;
    }

    public LinkedList<StudentStatus> getStudentsStatusesList() {
        return studentScores;
    }

    public String getStudentsScoreString(Student targetStudent) {
        StudentStatus studentStatus = getStudentsStatus(targetStudent);
        if (studentStatus.scoreIsFinalized()) {
            double score = studentStatus.getScore();
            return String.valueOf(score);
        }
        return "N/A";
    }

    public void finalizeScore(Student targetStudent) {
        String studentID;
        for (StudentStatus studentStatus : studentScores) {
            studentID = studentStatus.getStudentID();
            if (studentID.equals(targetStudent.getStudentID())) {
                studentStatus.setScoreIsFinalized(true);
                updateInDatabase();
                return;
            }
        }
    }

    public void addStudent(Student student) {
        listOfStudents.add(student);
        addStudentToStudentScoresList(student);
    }

    public void removeStudent(Student student) {
        for (int i = 0; i < listOfStudents.size(); i++) {
            if (student == listOfStudents.get(i)) {
                listOfStudents.remove(i);
                return;
            }
        }
    }

    public String getCourseName() {
        return courseName;
    }

    public String getExamTimeString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd - hh:mm");
        return formatter.format(examTime);
    }

    public LocalDateTime getExamTime() {
        return examTime;
    }

    public Professor getTeachingProfessor() {
        return teachingProfessor;
    }

    public int getNumberOfCredits() {
        return numberOfCredits;
    }

    public String getCourseLevelString() {
        switch (courseLevel) {
            case BACHELORS:
                return "Bachelors";
            case GRADUATE:
                return "Graduate";
            case PHD:
                return "PhD";
        }
        return "";
    }

    public CourseLevel getCourseLevel() {
        return courseLevel;
    }

    public String getCourseID() {
        return courseID;
    }

    public String getDepartmentName() {
        Department department = DepartmentsDB.getCoursesDepartment(this);
        return department.getDepartmentName();
    }

    public LinkedList<Student> getListOfStudents() {
        return listOfStudents;
    }

    public LinkedList<WeeklyDate> getClassDatesInWeek() {
        return classDatesInWeek;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setTeachingProfessor(Professor teachingProfessor) {
        this.teachingProfessor = teachingProfessor;
    }

    public void setNumberOfCredits(int numberOfCredits) {
        this.numberOfCredits = numberOfCredits;
    }

    public void setCourseLevel(CourseLevel courseLevel) {
        this.courseLevel = courseLevel;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public void updateInDatabase() {
        CoursesDB.removeFromDatabase(this);
        CoursesDB.addToDatabase(this);
    }
}
