package utils.database.testdata;

import logic.models.abstractions.Department;
import logic.models.abstractions.University;
import logic.models.roles.Dean;

public abstract class DepartmentBuilder {
    protected Department department;
    protected Dean dean;

    protected DepartmentBuilder(University university) {
        buildDepartmentDatabase();
    }

    protected abstract void buildDepartment();

    protected abstract void buildProfessors();

    protected abstract void buildCourses();

    protected abstract void buildStudents();

    protected abstract void setDean();

    protected abstract void setEducationDeputy();

    protected abstract void mapStudentsToScores();

    protected abstract void addProfessorsToDepartment();

    protected abstract void addStudentsToCourses();

    protected abstract void addStudentsToDepartment();

    protected abstract void addCoursesToDepartment();

    protected void buildDepartmentDatabase() {
        setDean();
        buildDepartment();
        buildProfessors();
        addProfessorsToDepartment();
        setEducationDeputy();
        buildStudents();
        addStudentsToDepartment();
        buildCourses();
        addStudentsToCourses();
        addCoursesToDepartment();
        mapStudentsToScores();
    }
}
