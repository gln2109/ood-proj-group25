package default_proj.data;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;

public class ParkingReaderJSON {

    JSONArray json_arr;

    public ParkingReaderJSON(String json_file) {
        try (FileReader fp = new FileReader(json_file)) {
            try {
                json_arr = (JSONArray) new JSONParser().parse(fp);
            } catch (ParseException | IOException e) {
                System.err.println("Cannot parse " + json_file);
            }
        } catch (IOException e) {
            System.err.println(json_file + " not found");
        }
    }

    public JSONArray getJSONArray() {
        return json_arr;
    }

    /*
    public static void main(String[] args) {

        ParkingReaderJSON parkingReaderJSON = new ParkingReaderJSON("PhillyData-files/parking.json");
        JSONArray parkingArr = parkingReaderJSON.getJSONArray();
        for (Object obj : parkingArr) {
            JSONObject parking = (JSONObject) obj;
            System.out.println(parking.get("ticket_number"));
        }
    }
    */

}
