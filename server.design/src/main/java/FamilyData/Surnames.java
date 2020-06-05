package FamilyData;

import java.util.List;
import java.util.Random;

public class Surnames {
    List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Surnames{" +
                "data=" + data +
                '}';
    }

    public String getRandom(){
        Random rand = new Random();
        return data.get(rand.nextInt(data.size()));
    }
}
