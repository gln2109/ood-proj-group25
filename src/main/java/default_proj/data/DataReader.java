package default_proj.data;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DataReader {

    private static DataReader instance;
    private ArrayList<String[]> parking_data, property_data;
    private Map<String, Integer> population_map;

    private DataReader(String parking_format, String parking_file,
                       String property_file, String population_file) {
        parking_data = null;
        property_data = null;
        population_map = null;

        FileReader parking_reader, property_reader, population_reader;
        try {
            parking_reader = new FileReader(parking_file);
            property_reader = new FileReader(property_file);
            population_reader = new FileReader(population_file);
        } catch (IOException e) {
            System.err.println("Unable to open file: " + e.getMessage());
            return;
        }

        if (parking_format.equals("json")) {
            parking_data = parseParkingJSON(parking_reader);
        } else {
            parking_data = parseParkingCSV(parking_reader);
        }
        property_data = parsePropertiesCSV(property_reader);
        population_map = parsePopulationTXT(population_reader);
    }


    public static void init(String parking_format, String parking_file,
                            String property_file, String population_file) {
        if (instance == null) {
            instance = new DataReader(parking_format, parking_file, property_file, population_file);
        }
    }

    public static DataReader getInstance() { return instance; }


    public ArrayList<String[]> getParkingData() { return parking_data; }

    public ArrayList<String[]> getPropertyData() { return property_data; }

    public Map<String, Integer> getPopulationMap() { return population_map; }


    private static ArrayList<String[]> parseParkingJSON(FileReader fp) {
        ArrayList<String[]> data = new ArrayList<>();
        try {
            JSONArray json_arr = (JSONArray) new JSONParser().parse(fp);

            String[] fields = {"date", "fine", "violation", "plate_id", "state", "ticket_number", "zip_code"};

            for (Object obj : json_arr) {
                JSONObject json_obj = (JSONObject) obj;
                String[] arr = new String[fields.length];

                for (int i = 0; i < fields.length; i++) {
                    arr[i] = json_obj.get(fields[i]).toString();
                }
                data.add(arr);
            }

        } catch (ParseException | IOException e) {
            System.err.println("Cannot parse JSON: " + e.getMessage());
            return null;
        }
        return data;
    }


    private static ArrayList<String[]> parseParkingCSV(FileReader fp) {
        ArrayList<String[]> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(fp)) {

            String ln;
            while ((ln = br.readLine()) != null) {
                String[] ln_arr = ln.split(",");
                data.add(ln_arr);
            }

        } catch (IOException e) {
            System.err.println("Cannot read CSV: " + e.getMessage());
            return null;
        }
        return data;
    }


    private static ArrayList<String[]> parsePropertiesCSV(FileReader fp) {
        ArrayList<String[]> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(fp)) {

            String ln = br.readLine();
            String[] fields = {"market_value", "total_livable_area", "zip_code"};
            int[] index_list = new int[fields.length];

            List<String> first_line = Arrays.asList(ln.split(","));
            for (int i = 0; i < fields.length; i++) {
                index_list[i] = first_line.indexOf(fields[i]);
            }

            while ((ln = br.readLine()) != null) {
                String[] ln_arr = ln.split(",");
                String[] entry = new String[index_list.length];
                for (int i = 0; i < index_list.length; i++) {
                    entry[i] = ln_arr[index_list[i]];
                }
                data.add(entry);
            }

        } catch (IOException e) {
            System.err.println("Cannot read CSV: " + e.getMessage());
            return null;
        }
        return data;
    }


    private static Map<String, Integer> parsePopulationTXT(FileReader fp) {

        Map<String, Integer> pop_map = new HashMap<>();
        try (BufferedReader br = new BufferedReader(fp)) {

            String ln;
            while ((ln = br.readLine()) != null) {
                String[] ln_arr = ln.split(" ");
                pop_map.put(ln_arr[0], Integer.parseInt(ln_arr[1]));
            }

        } catch (IOException e) {
            System.err.println("Cannot read TXT: " + e.getMessage());
            return null;
        }
        return pop_map;
    }


}
