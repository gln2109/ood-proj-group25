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
    private final ArrayList<String[]> parking_data, property_data;
    private final Map<String, Integer> population_map;

    private DataReader(String parking_format, String parking_file,
                       String property_file, String population_file) throws IOException, ParseException {
        if (parking_format.equals("json")) {
            parking_data = parseParkingJSON(new FileReader(parking_file));
        } else {
            parking_data = parseParkingCSV(new FileReader(parking_file));
        }
        property_data = parsePropertiesCSV(new FileReader(property_file));
        population_map = parsePopulationTXT(new FileReader(population_file));
    }

    public static void init(String parking_format, String parking_file,
                            String property_file, String population_file) {
        if (instance == null) {
            try {
                instance = new DataReader(parking_format, parking_file, property_file, population_file);
            } catch (IOException | ParseException e) {
                System.err.println("Error reading file: " + e.getMessage());
                instance = null;
            }
        }
    }

    public static DataReader getInstance() { return instance; }

    public ArrayList<String[]> getParkingData() { return parking_data; }

    public ArrayList<String[]> getPropertyData() { return property_data; }

    public Map<String, Integer> getPopulationMap() { return population_map; }


    private static ArrayList<String[]> parseParkingJSON(FileReader fp) throws IOException, ParseException {

        // The order of this array matches the order of fields in the csv file
        String[] fields = {"date", "fine", "violation", "plate_id", "state", "ticket_number", "zip_code"};

        ArrayList<String[]> data = new ArrayList<>();
        JSONArray json_arr = (JSONArray) new JSONParser().parse(fp);
        for (Object obj : json_arr) {
            JSONObject json_obj = (JSONObject) obj;
            String[] entry = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                entry[i] = json_obj.get(fields[i]).toString();
            }
            data.add(entry);
        }
        return data;
    }


    private static ArrayList<String[]> parseParkingCSV(FileReader fp) throws IOException {
        ArrayList<String[]> data = new ArrayList<>();
        BufferedReader br = new BufferedReader(fp);
            String ln;
            while ((ln = br.readLine()) != null) {
                data.add(ln.split(","));
            }
        return data;
    }


    private static ArrayList<String[]> parsePropertiesCSV(FileReader fp) throws IOException {

        // This array sets the output fields
        String[] fields = {"market_value", "total_livable_area", "zip_code"};

        ArrayList<String[]> data = new ArrayList<>();
        BufferedReader br = new BufferedReader(fp);
        String ln = br.readLine();
        List<String> first_line = Arrays.asList(ln.split(","));
        int[] index_list = new int[fields.length];
        for (int i = 0; i < fields.length; i++) {
            index_list[i] = first_line.indexOf(fields[i]); // Finding field name in first line
        }

        while ((ln = br.readLine()) != null) {
            String[] ln_arr = ln.split(",");
            String[] entry = new String[index_list.length];
            for (int i = 0; i < index_list.length; i++) {
                entry[i] = ln_arr[index_list[i]];
            }
            data.add(entry);
        }
        return data;
    }


    private static Map<String, Integer> parsePopulationTXT(FileReader fp) throws IOException {
        Map<String, Integer> pop_map = new HashMap<>();
        BufferedReader br = new BufferedReader(fp);
            String ln;
            while ((ln = br.readLine()) != null) {
                String[] ln_arr = ln.split(" ");
                pop_map.put(ln_arr[0], Integer.parseInt(ln_arr[1]));
            }
        return pop_map;
    }

}
