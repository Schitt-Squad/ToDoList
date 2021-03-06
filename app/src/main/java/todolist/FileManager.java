package todolist;

/**
 * @author Christian Liechty
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileManager {

    //Empty constructor
    public FileManager() {}

    public void writeUser(String file, ArrayList<User> users) throws IOException {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        //Instantiate new FileWriter
        FileWriter writer = new FileWriter(file);

        //Create new Json file
        gson.toJson(users, writer);

        //Close the writer
        writer.close();
    }

    public ArrayList<User> readUser(String file) throws IOException {
        Gson gson = new Gson();
        ArrayList<User> other;

        //Instantiate Reader
        Reader reader = Files.newBufferedReader(Paths.get(file));

        //Create type for ArrayList
        Type arrayList = new TypeToken<ArrayList<User>>(){}.getType();

        //Bring in file
        other = gson.fromJson(reader, arrayList);

        //Close reader
        reader.close();

        return other;
    }
}
