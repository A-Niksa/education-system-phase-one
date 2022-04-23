package logic.models.abstractions;

import logic.models.roles.Dean;
import logic.models.roles.EducationDeputy;
import logic.models.roles.Professor;
import logic.models.roles.Student;
import utils.database.data.DepartmentsDB;

import java.util.LinkedList;

public class Department {
    private String departmentName;
    private Dean dean;
    private EducationDeputy educationDeputy;
    private LinkedList<Professor> listOfProfessors;
    private LinkedList<Course> listOfCourses;
    private LinkedList<Student> listOfStudents;

    public Department(String departmentName, Dean dean) {
        this.departmentName = departmentName;
        this.dean = dean;
        listOfProfessors = new LinkedList<>();
        listOfStudents = new LinkedList<>();
        listOfCourses = new LinkedList<>();
        DepartmentsDB.addToDatabase(this); // TODO: updating DB
    }

    public void addProfessor(Professor professor) {
        listOfProfessors.add(professor);
    }

    public void removeStudent(Professor professor) {
        for (int i = 0; i < listOfProfessors.size(); i++) {
            if (professor == listOfProfessors.get(i)) {
                listOfProfessors.remove(i);
                return;
            }
        }
    }

    public void addCourse(Course course) {
        listOfCourses.add(course);
    }

    public void removeCourse(Course course) {
        for (int i = 0; i < listOfCourses.size(); i++) {
            if (course == listOfCourses.get(i)) {
                listOfCourses.remove(i);
                return;
            }
        }
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

    public Dean getDean() {
        return dean;
    }

    public EducationDeputy getEducationDeputy() {
        return educationDeputy;
    }

    public void setEducationDeputy(EducationDeputy educationDeputy) {
        this.educationDeputy = educationDeputy;
    }

    public LinkedList<Student> getListOfStudents() {
        return listOfStudents;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public LinkedList<Course> getListOfCourses() {
        return listOfCourses;
    }
}
