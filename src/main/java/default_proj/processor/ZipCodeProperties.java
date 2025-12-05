package default_proj.processor;

import default_proj.common.Property;
import default_proj.data.DataReader;
import java.util.ArrayList;
import java.util.Iterator;

public class ZipCodeProperties implements Iterable<Property> {
    private ArrayList<Property> filteredProperties;

    public ZipCodeProperties(int zipCode) {
        filteredProperties = new ArrayList<>();
        for (Property p : DataReader.getInstance().getPropertyData()) {
            if (p.zip_code() == zipCode) {
                filteredProperties.add(p);
            }
        }
    }

    @Override
    public Iterator<Property> iterator() {
        return filteredProperties.iterator();
    }
}

