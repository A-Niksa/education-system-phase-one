package logic.models.abstractions;

import logic.models.roles.Dean;
import logic.models.roles.Professor;
import logic.models.roles.Student;
import utils.database.data.DepartmentsDB;

import java.util.LinkedList;

public class Department {
    private String departmentName;
    private Professor dean;
    private Professor educationDeputy;
    private LinkedList<Professor> listOfProfessors;
    private LinkedList<Course> listOfCourses;
    private LinkedList<Student> listOfStudents;

    public Department(String departmentName, Dean dean) {
        this.departmentName = departmentName;
        this.dean = dean;
        listOfProfessors = new LinkedList<>();
        listOfStudents = new LinkedList<>();
        listOfCourses = new LinkedList<>();
        addProfessor(dean);
        DepartmentsDB.addToDatabase(this);
    }

    public void addProfessor(Professor professor) {
        listOfProfessors.add(professor);
    }

    public void removeProfessor(Professor professor) {
        String professorID = professor.getTeachingID();
        String eachProfessorsID;
        for (int i = 0; i < listOfProfessors.size(); i++) {
            eachProfessorsID = listOfProfessors.get(i).getTeachingID();
            if (professorID.equals(eachProfessorsID)) {
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

    public Professor getDean() {
        return dean;
    }

    public Professor getEducationDeputy() {
        return educationDeputy;
    }

    public void setEducationDeputyForFirstTime(Professor educationDeputy) {
        this.educationDeputy = educationDeputy;
        addProfessor(educationDeputy);
    }

    public void setEducationDeputy(Professor educationDeputy) {
        if (educationDeputy != null) {
            educationDeputy.setAdministrativeRole(Professor.AdministrativeRole.EDUCATION_DEPUTY);
            educationDeputy.updateInDatabase();
        }
        if (this.educationDeputy != null) {
            this.educationDeputy.setAdministrativeRole(Professor.AdministrativeRole.NORMAL);
            this.educationDeputy.updateInDatabase();
        }
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

    public LinkedList<Professor> getListOfProfessors() {
        return listOfProfessors;
    }
}
