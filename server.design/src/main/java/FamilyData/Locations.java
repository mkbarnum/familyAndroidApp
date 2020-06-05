package FamilyData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Locations {
    List<Location> data;

    public List<Location> getLocations() {
        return data;
    }

    public void setLocations(List<Location> locations) {
        this.data = locations;
    }

    @Override
    public String toString() {
        return "Locations{" +
                "locations=" + data +
                '}';
    }

    public Location getRandom(){
        Random rand = new Random();
        return data.get(rand.nextInt(data.size()));
    }
}
