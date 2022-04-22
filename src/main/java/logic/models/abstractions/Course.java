package logic.models.abstractions;

import logic.models.roles.Professor;
import logic.models.roles.Student;
import utils.database.data.CoursesDB;
import utils.database.data.DepartmentsDB;

import java.time.LocalDate;
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

    public Course(String courseName, LocalDateTime examTime, int numberOfCredits, CourseLevel courseLevel,
                  String courseID, Professor teachingProfessor) {
        this.courseName = courseName;
        this.examTime = examTime;
        this.numberOfCredits = numberOfCredits;
        this.courseLevel = courseLevel;
        this.courseID = courseID;
        this.teachingProfessor = teachingProfessor;
        listOfStudents = new LinkedList<>();
        CoursesDB.addToDatabase(this);
    }

    public void addStudent(Student student) {
        listOfStudents.add(student);
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
}
