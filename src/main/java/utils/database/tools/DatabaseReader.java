package utils.database.tools;

import utils.database.data.CoursesDB;
import utils.database.data.DepartmentsDB;
import utils.database.data.ProfessorsDB;
import utils.database.data.StudentsDB;

import java.io.IOException;

public class DatabaseReader extends DatabaseManager {
    private static DatabaseReader databaseReader;

    private DatabaseReader() {
        super();
    }

    private static DatabaseReader getInstance() {
        if (databaseReader == null) {
            databaseReader = new DatabaseReader();
        }
        return databaseReader;
    }

    public static void constructFromDatabase(DatabaseIdentifier databaseIdentifier) {
        getInstance().constructFromDatabaseByInstance(databaseIdentifier);
    }

    private void constructFromDatabaseByInstance(DatabaseIdentifier databaseIdentifier) {
        initializeReadingTools(databaseIdentifier);
        switch (databaseIdentifier) {
            case STUDENTS:
                StudentsDB.setDatabase(gson.fromJson(reader, StudentsDB.getListType()));
                break;
            case PROFESSORS:
                ProfessorsDB.setDatabase(gson.fromJson(reader, ProfessorsDB.getListType()));
                break;
            case DEPARTMENTS:
                DepartmentsDB.setDatabase(gson.fromJson(reader, DepartmentsDB.getListType()));
                break;
            case COURSES:
                CoursesDB.setDatabase(gson.fromJson(reader, CoursesDB.getListType()));
                break;
            default:
                return;
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}