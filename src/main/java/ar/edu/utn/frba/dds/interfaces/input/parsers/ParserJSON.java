package ar.edu.utn.frba.dds.interfaces.input.parsers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class ParserJSON<T> {
    private final Class<T> type;

    public ParserJSON(Class<T> type) {
        this.type = type;
    }

    public T parseElement(String json) {
        return new Gson().fromJson(json, type);
    }

    public List<T> parseCollection(String json) {
        Type listType = TypeToken.getParameterized(List.class, type).getType();
        return new Gson().fromJson(json, listType);
    }

    public T parseFileToElement(String archivo) throws IOException {
        return new Gson().fromJson(new FileReader(archivo), type);
    }

    public List<T> parseFileToCollection(String archivo) throws IOException {
        Type listType = TypeToken.getParameterized(List.class, type).getType();
        return new Gson().fromJson(new FileReader(archivo), listType);
    }
}
