package default_proj.processor;

import default_proj.common.*;
import default_proj.data.DataReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Processor {

    public static int getTotalPopulation() {
        ArrayList<Population> populationData = DataReader.getInstance().getPopulationData();
        int total = 0;
        for (Population p : populationData) {
            total += p.value();
        }
        return total;
    }

    public static String getFinesPerCapita() {
        ArrayList<Parking> parkingData = DataReader.getInstance().getParkingData();
        ArrayList<Population> populationData = DataReader.getInstance().getPopulationData();

        Map<Integer, Integer> populationMap = new TreeMap<>();
        for (Population p : populationData) {
            populationMap.put(p.zip_code(), p.value());
        }

        Map<Integer, Integer> finesMap = new TreeMap<>();
        for (Parking p : parkingData) {
            if (!p.state().equals("PA")) {
                continue;
            }
            int zip = p.zip_code();
            finesMap.put(zip, finesMap.getOrDefault(zip, 0) + p.fine());
        }

        StringBuilder result = new StringBuilder();
        for (Map.Entry<Integer, Integer> entry : finesMap.entrySet()) {
            int zip = entry.getKey();
            int totalFines = entry.getValue();
            if (totalFines == 0) {
                continue;
            }
            Integer population = populationMap.get(zip);
            if (population == null || population == 0) {
                continue;
            }
            double perCapita = (double) totalFines / population;
            result.append(String.format("%d %.4f%n", zip, perCapita));
        }
        return result.toString();
    }

    public static int getAverageMarketValue(int zipCode) {
        ArrayList<Property> propertyData = DataReader.getInstance().getPropertyData();
        double totalValue = 0;
        int count = 0;
        for (Property p : propertyData) {
            if (p.zip_code() != zipCode) {
                continue;
            }
            if (p.market_value() <= 0) {
                continue;
            }
            totalValue += p.market_value();
            count++;
        }
        if (count == 0) {
            return 0;
        }
        return (int) Math.round(totalValue / count);
    }

    public static int getAverageLivableArea(int zipCode) {
        ArrayList<Property> propertyData = DataReader.getInstance().getPropertyData();
        double totalArea = 0;
        int count = 0;
        for (Property p : propertyData) {
            if (p.zip_code() != zipCode) {
                continue;
            }
            if (p.livable_area() <= 0) {
                continue;
            }
            totalArea += p.livable_area();
            count++;
        }
        if (count == 0) {
            return 0;
        }
        return (int) Math.round(totalArea / count);
    }

    public static int getMarketValuePerCapita(int zipCode) {
        ArrayList<Property> propertyData = DataReader.getInstance().getPropertyData();
        ArrayList<Population> populationData = DataReader.getInstance().getPopulationData();

        double totalValue = 0;
        for (Property p : propertyData) {
            if (p.zip_code() != zipCode) {
                continue;
            }
            if (p.market_value() <= 0) {
                continue;
            }
            totalValue += p.market_value();
        }

        if (totalValue == 0) {
            return 0;
        }

        int population = 0;
        for (Population p : populationData) {
            if (p.zip_code() == zipCode) {
                population = p.value();
                break;
            }
        }

        if (population == 0) {
            return 0;
        }

        return (int) Math.round(totalValue / population);
    }

    public static int getLivableAreaPerCapita(int zipCode) {
        ArrayList<Property> propertyData = DataReader.getInstance().getPropertyData();
        ArrayList<Population> populationData = DataReader.getInstance().getPopulationData();

        double totalArea = 0;
        for (Property p : propertyData) {
            if (p.zip_code() != zipCode) {
                continue;
            }
            if (p.livable_area() <= 0) {
                continue;
            }
            totalArea += p.livable_area();
        }

        if (totalArea == 0) {
            return 0;
        }

        int population = 0;
        for (Population p : populationData) {
            if (p.zip_code() == zipCode) {
                population = p.value();
                break;
            }
        }

        if (population == 0) {
            return 0;
        }

        return (int) Math.round(totalArea / population);
    }

    public static int getAverageValuePerSqFt(int zipCode) {
        ArrayList<Property> propertyData = DataReader.getInstance().getPropertyData();
        double totalValuePerSqFt = 0;
        int count = 0;
        for (Property p : propertyData) {
            if (p.zip_code() != zipCode) {
                continue;
            }
            if (p.market_value() <= 0 || p.livable_area() <= 0) {
                continue;
            }
            totalValuePerSqFt += p.market_value() / p.livable_area();
            count++;
        }
        if (count == 0) {
            return 0;
        }
        return (int) Math.round(totalValuePerSqFt / count);
    }

}

