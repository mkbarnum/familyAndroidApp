package FamilyData;

import java.util.List;
import java.util.Random;

public class FemaleNames {
    List<String> data;

    public List<String> getFnames() {
        return data;
    }

    public void setFnames(List<String> fnames) {
        this.data = fnames;
    }

    @Override
    public String toString() {
        return "FemaleNames{" +
                "fnames=" + data +
                '}';
    }

    public String getRandom(){
        Random rand = new Random();
        return data.get(rand.nextInt(data.size()));
    }
}
