package utils.database.tools;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.*;

public class DatabaseManager {
    protected GsonBuilder builder;
    protected Gson gson;
    protected JsonReader reader;
    protected FileWriter writer;
    protected DatabasePathFinder pathFinder;

    protected DatabaseManager() {
        builder = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY);
        gson = builder.create();
        pathFinder = new DatabasePathFinder();
    }

    protected void initializeReadingTools(DatabaseIdentifier databaseIdentifier) {
        String pathToFile = getFullPath(databaseIdentifier);
        try {
            reader = new JsonReader(new FileReader(pathToFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void initializeWritingTools(DatabaseIdentifier databaseIdentifier) {
        String pathToFile = getFullPath(databaseIdentifier);
        try {
            writer = new FileWriter(pathToFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String getFullPath(DatabaseIdentifier databaseIdentifier) {
        return pathFinder.getPath(databaseIdentifier);
    }
}