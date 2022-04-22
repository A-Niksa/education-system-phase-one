package utils.database.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import logic.models.abstractions.Department;
import logic.models.roles.Student;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedList;

public class DepartmentsDB extends ModelDB {
    private static DepartmentsDB database;

    private LinkedList<Department> departmentsList;

    private DepartmentsDB() {
        departmentsList = new LinkedList<Department>();
        listType = new TypeToken<LinkedList<Department>>(){}.getType();
    }

    private static DepartmentsDB getInstance() {
        if (database == null) {
            database = new DepartmentsDB();
        }
        return database;
    }

    public static void addToDatabase(Department department) {
        getInstance().addToDatabaseByInstance(department);
    }

    private void addToDatabaseByInstance(Department department) {
        departmentsList.add(department);
    }

    public static void removeFromDatabase(Department department) {
        getInstance().removeFromDatabaseByInstance(department);
    }

    private void removeFromDatabaseByInstance(Department department) {
        for (int i = 0; i < departmentsList.size(); i++) {
            if (department == departmentsList.get(i)) {
                departmentsList.remove(i);
                return;
            }
        }
    }

    public static void setDatabase(LinkedList<Department> departmentsList) {
        getInstance().setDatabaseByInstance(departmentsList);
    }

    private void setDatabaseByInstance(LinkedList<Department> departmentsList) {
        this.departmentsList = departmentsList;
    }

    public static Type getListType() {
        return getInstance().getListTypeByInstance();
    }

    public static LinkedList<Department> getList() {
        return getInstance().getListByInstance();
    }

    private LinkedList<Department> getListByInstance() {
        return departmentsList;
    }

    public static void addListToJson(FileWriter writer, Gson gson) {
        getInstance().addListToJsonByInstance(writer, gson);
    }

    @Override
    protected void addListToJsonByInstance(FileWriter writer, Gson gson) {
        try {
            writer.write(gson.toJson(departmentsList));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Department getStudentsDepartment(Student targetStudent) {
        return getInstance().getStudentsDepartmentByInstance(targetStudent);
    }

    private Department getStudentsDepartmentByInstance(Student targetStudent) {
        LinkedList<Student> studentsPerDepartment;
        for (Department department : departmentsList) {
            studentsPerDepartment = department.getListOfStudents();
            for (Student student : studentsPerDepartment) {
                if (student.getStudentID().equals(targetStudent.getStudentID())) {
                    return department;
                }
            }
        }
        return null;
    }
}
