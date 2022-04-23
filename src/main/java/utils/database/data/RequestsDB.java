package utils.database.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import logic.menus.services.requests.Request;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedList;

public class RequestsDB extends ModelDB {
    private static RequestsDB database;

    private LinkedList<Request> requestsList;

    private RequestsDB() {
        requestsList = new LinkedList<>();
        listType = new TypeToken<LinkedList<Request>>(){}.getType();
    }

    private static RequestsDB getInstance() {
        if (database == null) {
            database = new RequestsDB();
        }
        return database;
    }

    public static void addToDatabase(Request request) {
        getInstance().addToDatabaseByInstance(request);
    }

    private void addToDatabaseByInstance(Request request) {
        requestsList.add(request);
    }

    public static void removeFromDatabase(Request request) {
        getInstance().removeFromDatabaseByInstance(request);
    }

    private void removeFromDatabaseByInstance(Request request) {
        for (int i = 0; i < requestsList.size(); i++) {
            if (request == requestsList.get(i)) {
                requestsList.remove(i);
                return;
            }
        }
    }

    public static void setDatabase(LinkedList<Request> requestsList) {
        if (requestsList != null) {
            getInstance().setDatabaseByInstance(requestsList);
        }
    }

    private void setDatabaseByInstance(LinkedList<Request> requestsList) {
        this.requestsList = requestsList;
    }

    public static Type getListType() {
        return getInstance().getListTypeByInstance();
    }

    public static LinkedList<Request> getList() {
        return getInstance().getListByInstance();
    }

    private LinkedList<Request> getListByInstance() {
        return requestsList;
    }

    public static void addListToJson(FileWriter writer, Gson gson) {
        getInstance().addListToJsonByInstance(writer, gson);
    }

    @Override
    protected void addListToJsonByInstance(FileWriter writer, Gson gson) {
        try {
            writer.write(gson.toJson(requestsList));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}