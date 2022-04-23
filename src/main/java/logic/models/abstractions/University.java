package logic.models.abstractions;

import utils.database.data.UniversityDB;

import java.util.LinkedList;

public class University {
    private LinkedList<Department> listOfDepartments;

    public University() {
        listOfDepartments = new LinkedList<>();
        UniversityDB.addToDatabase(this);
    }

    public void addDepartment(Department department) {
        listOfDepartments.add(department);
    }

    public LinkedList<Department> getListOfDepartments() {
        return listOfDepartments;
    }
}
