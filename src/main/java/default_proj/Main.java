package default_proj;

import default_proj.data.DataReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        if (args.length != 4) {
            System.err.println("Invalid number of arguments");
            return;
        }

        if (!args[0].equals("csv") && !args[0].equals("json")) {
            System.err.println("Parking format must be 'csv' or 'json'");
            return;
        }

        DataReader.init(args[0], args[1], args[2], args[3]);

        DataReader dr = DataReader.getInstance();
        ArrayList<String[]> parking_data, property_data;
        Map<String, Integer> population_map;

        parking_data = dr.getParkingData();
        property_data = dr.getPropertyData();
        population_map = dr.getPopulationMap();
        if (parking_data == null || property_data == null || population_map == null) {
            return;
        }
        printArrayList(parking_data, 5);
        printArrayList(property_data, 5);

    }


    public static void printArrayList(ArrayList<String[]> arr_list, int num) {
        for (int i = 0; i < num; i++) {
            System.out.println(Arrays.toString(arr_list.get(i)));
        }
    }
}