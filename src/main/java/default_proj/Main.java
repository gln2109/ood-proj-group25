package default_proj;

import default_proj.data.DataReader;
import default_proj.ui.Menu;

import java.util.*;

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
        if (dr == null) { return; }


        int selection = -1;
        while (selection != 0) { // Main Loop

            selection = Menu.promptSelection();
            System.out.println("Selected option: " + selection + "\n");


        }


    }

}
