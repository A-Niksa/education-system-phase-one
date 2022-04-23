package utils.database.tools;

public class DatabasePathFinder {
    private final String databasesPath;

    public DatabasePathFinder() {
        databasesPath = "src/main/resources/database/";
    }

    public String getPath(DatabaseIdentifier databaseIdentifier) {
        switch (databaseIdentifier) {
            case STUDENTS:
                return databasesPath + "students.json";
            case PROFESSORS:
                return databasesPath + "professors.json";
            case DEPARTMENTS:
                return databasesPath + "departments.json";
            case COURSES:
                return databasesPath + "courses.json";
            case UNIVERSITY:
                return databasesPath + "university.json";
            case REQUESTS:
                return databasesPath + "requests.json";
            case MINORS:
                return databasesPath + "minors.json";
            case RECOMMENDATIONS:
                return databasesPath + "recommendations.json";
            default:
                return null;
        }
    }
}
