package utils.database.tools;

import logic.menus.services.requests.Request;
import logic.models.abstractions.Course;
import logic.models.abstractions.Department;
import logic.models.roles.Professor;
import logic.models.roles.Student;
import utils.database.data.*;
import utils.logging.LogIdentifier;
import utils.logging.MasterLogger;

public class DatabaseWriter extends DatabaseManager {
    private static DatabaseWriter databaseWriter;

    private DatabaseWriter() {
        super();
    }

    private static DatabaseWriter getInstance() {
        if (databaseWriter == null) {
            databaseWriter = new DatabaseWriter();
        }
        return databaseWriter;
    }

    public static void addToDatabase(Object objectToAdd, DatabaseIdentifier databaseIdentifier) {
        getInstance().addToDatabaseByInstance(objectToAdd, databaseIdentifier);
    }

    private void addToDatabaseByInstance(Object objectToAdd, DatabaseIdentifier databaseIdentifier) {
        initializeWritingTools(databaseIdentifier);
        switch (databaseIdentifier) {
            case STUDENTS:
                StudentsDB.addToDatabase((Student) objectToAdd);
                return;
            case PROFESSORS:
                ProfessorsDB.addToDatabase((Professor) objectToAdd);
                return;
            case DEPARTMENTS:
                DepartmentsDB.addToDatabase((Department) objectToAdd);
                return;
            case COURSES:
                CoursesDB.addToDatabase((Course) objectToAdd);
                return;
            case REQUESTS:
                RequestsDB.addToDatabase((Request) objectToAdd);
        }
    }

    public static void removeFromDatabase(Object objectToRemove, DatabaseIdentifier databaseIdentifier) {
        getInstance().removeFromDatabaseByInstance(objectToRemove, databaseIdentifier);
    }

    private void removeFromDatabaseByInstance(Object objectToRemove, DatabaseIdentifier databaseIdentifier) {
        initializeWritingTools(databaseIdentifier);
        switch (databaseIdentifier) {
            case STUDENTS:
                StudentsDB.removeFromDatabase((Student) objectToRemove);
                return;
            case PROFESSORS:
                ProfessorsDB.removeFromDatabase((Professor) objectToRemove);
                return;
            case DEPARTMENTS:
                DepartmentsDB.removeFromDatabase((Department) objectToRemove);
                return;
            case COURSES:
                CoursesDB.removeFromDatabase((Course) objectToRemove);
                return;
            case REQUESTS:
                RequestsDB.removeFromDatabase((Request) objectToRemove);
        }
    }

    public static void updateDatabase() {
        MasterLogger.log("updated json databases", LogIdentifier.INFO, "updateDatabase",
                "utils.database.tools.DatabaseWriter");
        getInstance().updateDatabaseByInstance();
    }

    private void updateDatabaseByInstance() {
        initializeWritingTools(DatabaseIdentifier.STUDENTS);
        StudentsDB.addListToJson(writer, gson);
        initializeWritingTools(DatabaseIdentifier.PROFESSORS);
        ProfessorsDB.addListToJson(writer, gson);
        initializeWritingTools(DatabaseIdentifier.COURSES);
        CoursesDB.addListToJson(writer, gson);
        initializeWritingTools(DatabaseIdentifier.DEPARTMENTS);
        DepartmentsDB.addListToJson(writer, gson);
        initializeWritingTools(DatabaseIdentifier.REQUESTS);
        RequestsDB.addListToJson(writer, gson);
    }
}
