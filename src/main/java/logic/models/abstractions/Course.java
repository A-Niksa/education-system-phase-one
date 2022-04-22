package logic.models.abstractions;

import logic.models.roles.Professor;
import logic.models.roles.Student;
import utils.database.data.CoursesDB;

import java.time.LocalDate;
import java.util.LinkedList;

public class Course {
    private String courseName;
    private LocalDate examTime;
    private Professor teachingProfessor;
    private LinkedList<Student> listOfStudents;

    public Course(String courseName, LocalDate examTime, Professor teachingProfessor) {
        this.courseName = courseName;
        this.examTime = examTime;
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
}
