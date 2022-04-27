package utils.database.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import logic.models.abstractions.Course;
import logic.models.abstractions.Transcript;
import logic.models.roles.Student;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedList;

public class StudentsDB extends ModelDB {
    private static StudentsDB database;

    private LinkedList<Student> studentsList;

    private StudentsDB() {
        studentsList = new LinkedList<>();
        listType = new TypeToken<LinkedList<Student>>(){}.getType();
    }

    private static StudentsDB getInstance() {
        if (database == null) {
            database = new StudentsDB();
        }
        return database;
    }

    public static void addToDatabase(Student student) {
        getInstance().addToDatabaseByInstance(student);
    }

    private void addToDatabaseByInstance(Student student) {
        studentsList.add(student);
    }

    public static void removeFromDatabase(Student student) {
        getInstance().removeFromDatabaseByInstance(student);
    }

    private void removeFromDatabaseByInstance(Student student) {
        for (int i = 0; i < studentsList.size(); i++) {
            if (student == studentsList.get(i)) {
                studentsList.remove(i);
                return;
            }
        }
    }

    public static void setDatabase(LinkedList<Student> studentsList) {
        if (studentsList != null) {
            getInstance().setDatabaseByInstance(studentsList);
        }
    }

    private void setDatabaseByInstance(LinkedList<Student> studentsList) {
        this.studentsList = studentsList;
    }

    public static Type getListType() {
        return getInstance().getListTypeByInstance();
    }

    public static LinkedList<Student> getList() {
        return getInstance().getListByInstance();
    }

    private LinkedList<Student> getListByInstance() {
        return studentsList;
    }

    public static String getStudentsNameWithID(String targetStudentID) {
        return getInstance().getStudentsNameWithIDByInstance(targetStudentID);
    }

    private String getStudentsNameWithIDByInstance(String targetStudentID) {
        String studentID;
        String targetStudentName;
        for (Student student : studentsList) {
            studentID = student.getStudentID();
            if (studentID.equals(targetStudentID)) {
                targetStudentName = student.getFirstName() + " " + student.getLastName();
                return targetStudentName;
            }
        }
        return "";
    }

    public static Student getStudentWithID(String targetStudentID) {
        return getInstance().getStudentWithIDByInstance(targetStudentID);
    }

    private Student getStudentWithIDByInstance(String targetStudentID) {
        String potentialStudentID;
        for (Student student : studentsList) {
            potentialStudentID = student.getStudentID();
            if (potentialStudentID.equals(targetStudentID)) {
                return student;
            }
        }
        return null;
    }

//    public static Student getStudentWithName(String targetStudentName) {
//        return getInstance().getStudentWithNameByInstance(targetStudentName);
//    }
//
//    private Student getStudentWithNameByInstance(String targetStudentName) {
//        String potentialStudentName;
//        for (Student student : studentsList) {
//            potentialStudentName = student.getFirstName() + " " + student.getLastName();
//            if (potentialStudentName.equals(targetStudentName)) {
//                return student;
//            }
//        }
//        return null;
//    }

    public static void removeCourseFromTranscripts(Course targetCourse) {
        getInstance().removeCourseFromTranscriptsByInstance(targetCourse);
    }

    private void removeCourseFromTranscriptsByInstance(Course targetCourse) {
        Transcript transcript;
        for (Student student : studentsList) {
            transcript = student.getTranscript();
            transcript.removeCourse(targetCourse);
        }
    }

    public static void addListToJson(FileWriter writer, Gson gson) {
        getInstance().addListToJsonByInstance(writer, gson);
    }

    @Override
    protected void addListToJsonByInstance(FileWriter writer, Gson gson) {
        try {
            writer.write(gson.toJson(studentsList));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}