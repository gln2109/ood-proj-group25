package default_proj;

import default_proj.data.DataReader;
import default_proj.ui.Menu;

import java.util.*;

public class MainNoArgs {

    // Parent directory holds the folder with the input files
    private static final String parent_dir = "datafiles/";

    // Set parameters for DataReader instead of reading them in from arguments
    private static final String parking_format = "json";
    private static final String parking_file = parent_dir + "parking.json";
    private static final String property_file = parent_dir + "properties.csv";
    private static final String population_file = parent_dir + "population.txt";

    public static void main(String[] args) {

        DataReader.init(parking_format, parking_file, property_file, population_file);
        DataReader dr = DataReader.getInstance();
        if (dr == null) { return; }


        int selection = -1;
        while (selection != 0) { // Main Loop

            selection = Menu.promptSelection();
            System.out.println("Selected option: " + selection + "\n");

            if (selection == 6) {
                Menu.printParkingEntries(dr.getParkingData());
            }

            if (selection == 7) {
                Menu.printPropertyEntries(dr.getPropertyData());
            }


        }


    }

}