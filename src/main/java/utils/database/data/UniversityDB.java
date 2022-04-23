package utils.database.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import logic.models.abstractions.University;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedList;

public class UniversityDB extends ModelDB {
    private static UniversityDB database;

    private LinkedList<University> universityList;

    private UniversityDB() {
        universityList = new LinkedList<>();
        listType = new TypeToken<LinkedList<University>>(){}.getType();
    }

    private static UniversityDB getInstance() {
        if (database == null) {
            database = new UniversityDB();
        }
        return database;
    }

    public static void addToDatabase(University university) {
        getInstance().addToDatabaseByInstance(university);
    }

    private void addToDatabaseByInstance(University university) {
        universityList.add(university);
    }

    public static void removeFromDatabase(University university) {
        getInstance().removeFromDatabaseByInstance(university);
    }

    private void removeFromDatabaseByInstance(University university) {
        for (int i = 0; i < universityList.size(); i++) {
            if (university == universityList.get(i)) {
                universityList.remove(i);
                return;
            }
        }
    }

    public static void setDatabase(LinkedList<University> universityList) {
        if (universityList != null) {
            getInstance().setDatabaseByInstance(universityList);
        }
    }

    private void setDatabaseByInstance(LinkedList<University> universityList) {
        this.universityList = universityList;
    }

    public static Type getListType() {
        return getInstance().getListTypeByInstance();
    }

    public static LinkedList<University> getList() {
        return getInstance().getListByInstance();
    }

    private LinkedList<University> getListByInstance() {
        return universityList;
    }

    public static void addListToJson(FileWriter writer, Gson gson) {
        getInstance().addListToJsonByInstance(writer, gson);
    }

    @Override
    protected void addListToJsonByInstance(FileWriter writer, Gson gson) {
        try {
            writer.write(gson.toJson(universityList));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
