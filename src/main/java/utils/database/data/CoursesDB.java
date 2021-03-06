package utils.database.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import gui.enrolment.FilterKey;
import logic.models.abstractions.Course;
import utils.logging.MasterLogger;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedList;

public class CoursesDB extends ModelDB {
    private static CoursesDB database;

    private LinkedList<Course> coursesList;

    private CoursesDB() {
        coursesList = new LinkedList<Course>();
        listType = new TypeToken<LinkedList<Course>>() {
        }.getType();
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

    public static void removeFromTranscripts(Course course) {

    }

    public static void removeFromDatabase(Course course) {
        getInstance().removeFromDatabaseByInstance(course);
    }

    private void removeFromDatabaseByInstance(Course course) {
        String targetID = course.getCourseID();
        String potentialCourseID;
        for (int i = 0; i < coursesList.size(); i++) {
            potentialCourseID = coursesList.get(i).getCourseID();
            if (targetID.equals(potentialCourseID)) {
                coursesList.remove(i);
                return;
            }
        }
    }

    public static void setDatabase(LinkedList<Course> coursesList) {
        if (coursesList != null) {
            getInstance().setDatabaseByInstance(coursesList);
        }
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

    public static LinkedList<Course> getFilteredList(FilterKey key, String filterTarget) {
        return getInstance().getFilteredListByInstance(key, filterTarget);
    }

    private LinkedList<Course> getFilteredListByInstance(FilterKey key, String filterTarget) {
        LinkedList<Course> filteredList = new LinkedList<>();
        switch (key) {
            case COURSE_ID:
                for (Course course : coursesList) {
                    if (course.getCourseID().equals(filterTarget)) {
                        filteredList.add(course);
                    }
                }
                break;
            case NUMBER_OF_CREDITS:
                for (Course course : coursesList) {
                    try {
                        if (course.getNumberOfCredits() == Integer.parseInt(filterTarget)) {
                            filteredList.add(course);
                        }
                    } catch (NumberFormatException e) {
                        MasterLogger.fatal(filterTarget + " is not castable to int", getClass());
                    }
                }
                break;
            case COURSE_LEVEL:
                for (Course course : coursesList) {
                    String courseLevelString = course.getCourseLevelString();
                    if (courseLevelString.equals(filterTarget)) {
                        filteredList.add(course);
                    }
                }
                break;
        }
        return filteredList;
    }

    public static Course getCourseWithID(String courseID) {
        return getInstance().getCourseWithIDByInstance(courseID);
    }

    private Course getCourseWithIDByInstance(String courseID) {
        for (Course course : coursesList) {
            if (course.getCourseID().equals(courseID)) {
                return course;
            }
        }
        return null;
    }

    public static Course getCourseWithName(String courseName, String departmentName) {
        return getInstance().getCourseWithNameByInstance(courseName, departmentName);
    }

    private Course getCourseWithNameByInstance(String courseName, String departmentName) {
        String potentialCoursesDepartmentName;
        String potentialCourseName;
        for (Course course : coursesList) {
            potentialCoursesDepartmentName = course.getDepartmentName();
            potentialCourseName = course.getCourseName();
            if (potentialCoursesDepartmentName.equals(departmentName) &&
                    potentialCourseName.equals(courseName)) {
                return course;
            }
        }
        return null;
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