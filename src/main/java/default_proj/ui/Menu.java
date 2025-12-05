package default_proj.ui;

import java.util.*;

public class Menu {

    private static final Scanner sc = new Scanner(System.in);
    private static final String MENU_OPTIONS = """
        1 : Total Population
        2 : Parking Fines per Capita
        3 : Average Residence Market Value for Zip Code
        4 : Average Residence Livable Area for Zip Code
        5 : Residential Market Value per Capita for Zip Code
        6 : Total Livable Area per Capita for Zip Code
        7 : Average Value per Square Foot for Zip Code
        0 : Exit""";

    public static int promptSelection() {
        int selection;
        while (true) {
            System.out.println(MENU_OPTIONS);
            System.out.print("Enter menu selection: ");
            String ln = sc.nextLine();
            try {
                selection = Integer.parseInt(ln);
                if (selection < 0 || selection > 7) {
                    throw new NumberFormatException();
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid menu selection\n");
            }
        }
        return selection;
    }

    public static int promptZipCode() {
        int zip_code;
        System.out.print("Enter zip code: ");
        String ln = sc.nextLine();
        try {
            zip_code = Integer.parseInt(ln);
            return zip_code;
        } catch (NumberFormatException e) {
            System.out.println("Invalid zip code\n");
        }
        return 0;
    }


}
