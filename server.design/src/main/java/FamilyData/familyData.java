package FamilyData;

import Handler.Deserializer;
import com.google.gson.Gson;

import java.io.*;

public class familyData {
    public FemaleNames fNames;
    public MaleNames mNames;
    public Locations locations;
    public Surnames sNames;

    public void createData(){
        File sFile = new File("json/snames.json");
        File fFile = new File("json/fnames.json");
        File mFile = new File("json/mnames.json");
        File locationsFile = new File("json/locations.json");

        try {
            fNames = parse(fFile, FemaleNames.class);
            mNames = parse(mFile, MaleNames.class);
            locations = parse(locationsFile, Locations.class);
            sNames = parse(sFile,Surnames.class);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private <T> T parse(File file, Class<T> c) throws Exception{
        String response = "";
        try{
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            Gson gson = new Gson();
            for (String line; (line = bufferedReader.readLine()) != null; response += line);

        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
        return Deserializer.deserialize(response, c);
    }
}
