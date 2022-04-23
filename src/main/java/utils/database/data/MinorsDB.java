package utils.database.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import logic.menus.services.requests.MinorRequest;
import logic.menus.services.requests.Request;
import logic.models.roles.Student;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedList;

public class MinorsDB extends ModelDB {
    private static MinorsDB database;

    private LinkedList<MinorRequest> minorsList;

    private MinorsDB() {
        minorsList = new LinkedList<>();
        listType = new TypeToken<LinkedList<MinorRequest>>(){}.getType();
    }

    private static MinorsDB getInstance() {
        if (database == null) {
            database = new MinorsDB();
        }
        return database;
    }

    public static void addToDatabase(MinorRequest minor) {
        getInstance().addToDatabaseByInstance(minor);
    }

    private void addToDatabaseByInstance(MinorRequest minor) {
        minorsList.add(minor);
    }

    public static void removeFromDatabase(MinorRequest minor) {
        getInstance().removeFromDatabaseByInstance(minor);
    }

    private void removeFromDatabaseByInstance(MinorRequest minor) {
        for (int i = 0; i < minorsList.size(); i++) {
            if (minor == minorsList.get(i)) {
                minorsList.remove(i);
                return;
            }
        }
    }

    public static void setDatabase(LinkedList<MinorRequest> minorsList) {
        if (minorsList != null) {
            getInstance().setDatabaseByInstance(minorsList);
        }
    }

    private void setDatabaseByInstance(LinkedList<MinorRequest> minorsList) {
        this.minorsList = minorsList;
    }

    public static Type getListType() {
        return getInstance().getListTypeByInstance();
    }

    public static LinkedList<MinorRequest> getList() {
        return getInstance().getListByInstance();
    }

    private LinkedList<MinorRequest> getListByInstance() {
        return minorsList;
    }

    public static void addListToJson(FileWriter writer, Gson gson) {
        getInstance().addListToJsonByInstance(writer, gson);
    }

    @Override
    protected void addListToJsonByInstance(FileWriter writer, Gson gson) {
        try {
            writer.write(gson.toJson(minorsList));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LinkedList<MinorRequest> getStudentsMinorRequests(Student targetStudent) {
        return getInstance().getStudentsMinorRequestsByInstance(targetStudent);
    }

    private LinkedList<MinorRequest> getStudentsMinorRequestsByInstance(Student targetStudent) {
        LinkedList<MinorRequest> minorRequests = new LinkedList<>();
        Student potentialStudent;
        String targetStudentID = targetStudent.getStudentID();
        for (MinorRequest minorRequest : minorsList) {
            potentialStudent = minorRequest.getRequestingStudent();
            if (potentialStudent.getStudentID().equals(targetStudentID)) {
                minorRequests.add(minorRequest);
            }
        }
        return minorRequests;
    }
}
