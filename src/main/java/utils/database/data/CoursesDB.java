package utils.database.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import logic.models.abstractions.Course;
import logic.models.roles.Student;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedList;

public class CoursesDB extends ModelDB {
    private static CoursesDB database;

    private LinkedList<Course> coursesList;
    private Type listType;

    private CoursesDB() {
        coursesList = new LinkedList<Course>();
        listType = new TypeToken<LinkedList<Course>>(){}.getType();
    }

    private static CoursesDB getInstance() {
        if (database == null) {
            database = new CoursesDB();
        }
        return database;
    }

    public static void addToDatabase(Course course) {
        getInstance().addToDatabaseByInstance(course);
    }

    private void addToDatabaseByInstance(Course course) {
        coursesList.add(course);
    }

    public static void removeFromDatabase(Course course) {
        getInstance().removeFromDatabaseByInstance(course);
    }

    private void removeFromDatabaseByInstance(Course course) {
        for (int i = 0; i < coursesList.size(); i++) {
            if (course == coursesList.get(i)) {
                coursesList.remove(i);
                return;
            }
        }
    }

    public static void setDatabase(LinkedList<Course> coursesList) {
        getInstance().setDatabaseByInstance(coursesList);
    }

    private void setDatabaseByInstance(LinkedList<Course> coursesList) {
        this.coursesList = coursesList;
    }

    public static Type getListType() {
        return getInstance().getListTypeByInstance();
    }

    public static LinkedList<Course> getList() {
        return getInstance().getListByInstance();
    }

    private LinkedList<Course> getListByInstance() {
        return coursesList;
    }

    public static void addListToJson(FileWriter writer, Gson gson) {
        getInstance().addListToJsonByInstance(writer, gson);
    }

    @Override
    protected void addListToJsonByInstance(FileWriter writer, Gson gson) {
        try {
            writer.write(gson.toJson(coursesList));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}