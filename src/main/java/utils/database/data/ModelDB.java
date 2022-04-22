package utils.database.data;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.lang.reflect.Type;

public abstract class ModelDB {
    protected Type listType;

    protected Type getListTypeByInstance() {
        return listType;
    }

    protected abstract void addListToJsonByInstance(FileWriter writer, Gson gson);
}
