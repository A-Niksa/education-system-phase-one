package utils.database.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import logic.models.roles.Professor;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedList;

public class ProfessorsDB extends ModelDB {
    private static ProfessorsDB database;

    private LinkedList<Professor> professorsList;

    private ProfessorsDB() {
        professorsList = new LinkedList<>();
        listType = new TypeToken<LinkedList<Professor>>() {
        }.getType();
    }

    private static ProfessorsDB getInstance() {
        if (database == null) {
            database = new ProfessorsDB();
        }
        return database;
    }

    public static void addToDatabase(Professor professor) {
        getInstance().addToDatabaseByInstance(professor);
    }

    private void addToDatabaseByInstance(Professor professor) {
        professorsList.add(professor);
    }

    public static void removeFromDatabase(Professor professor) {
        getInstance().removeFromDatabaseByInstance(professor);
    }

    private void removeFromDatabaseByInstance(Professor professor) {
        String professorID = professor.getTeachingID();
        String eachProfessorsID;
        for (int i = 0; i < professorsList.size(); i++) {
            eachProfessorsID = professorsList.get(i).getTeachingID();
            if (professorID.equals(eachProfessorsID)) {
                professorsList.remove(i);
                return;
            }
        }
    }

    public static void setDatabase(LinkedList<Professor> professorsList) {
        if (professorsList != null) {
            getInstance().setDatabaseByInstance(professorsList);
        }
    }

    private void setDatabaseByInstance(LinkedList<Professor> professorsList) {
        this.professorsList = professorsList;
    }

    public static Type getListType() {
        return getInstance().getListTypeByInstance();
    }

    public static LinkedList<Professor> getList() {
        return getInstance().getListByInstance();
    }

    private LinkedList<Professor> getListByInstance() {
        return professorsList;
    }

    public static void addListToJson(FileWriter writer, Gson gson) {
        getInstance().addListToJsonByInstance(writer, gson);
    }

    @Override
    protected void addListToJsonByInstance(FileWriter writer, Gson gson) {
        try {
            writer.write(gson.toJson(professorsList));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Professor getProfessorWithID(String teachingID) {
        return getInstance().getProfessorWithIDByInstance(teachingID);
    }

    private Professor getProfessorWithIDByInstance(String teachingID) {
        for (Professor professor : professorsList) {
            if (professor.getTeachingID().equals(teachingID)) {
                return professor;
            }
        }
        return null;
    }

    public static Professor getProfessorWithName(String name) {
        return getInstance().getProfessorWithNameByInstance(name);
    }

    private Professor getProfessorWithNameByInstance(String name) {
        String potentialProfessorName;
        for (Professor professor : professorsList) {
            potentialProfessorName = professor.getFirstName() + " " + professor.getLastName();
            if (potentialProfessorName.equals(name)) {
                return professor;
            }
        }
        return null;
    }
}
