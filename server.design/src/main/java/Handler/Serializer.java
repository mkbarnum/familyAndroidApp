package Handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Serializer {

    public static <T> String serialize(Object o){

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(o);

        return jsonString;

    }
}
