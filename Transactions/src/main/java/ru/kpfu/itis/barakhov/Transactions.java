package ru.kpfu.itis.barakhov;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Transactions {
    private String prodCode;
    private String basketID;

    public static void main(String[] args) throws IOException {
        CsvToBean csv = new CsvToBean();
        String csvFilename = "src/transactions.csv";
        CSVReader csvReader = new CSVReader(new FileReader(csvFilename), ';', '"', 1);
        List list = csv.parse(setColumnMapping(), csvReader);
        Map<String, Map<Integer, List<String>>> products = new Transactions().addAllProducts(list);     // [ PRDxxxxxxx [ number_of_orders list_of_baskets ] ]
        writeToCSV(products);
    }

    private static void writeToCSV(Map<String, Map<Integer, List<String>>> products) throws IOException {
        String csv = "target/data.csv";
        CSVWriter writer = new CSVWriter(new FileWriter(csv));
        String[] record = new String[2];
        for (Map.Entry<String, Map<Integer, List<String>>> entry: products.entrySet()) {
            record[0] = entry.getKey();
            for (Map.Entry<Integer, List<String>> innerEntry : entry.getValue().entrySet()) {
                record[1] = String.valueOf(innerEntry.getKey());
            }
            writer.writeNext(record);
        }
        writer.close();
    }

    private static ColumnPositionMappingStrategy<Transactions> setColumnMapping() {
        ColumnPositionMappingStrategy<Transactions> strategy = new ColumnPositionMappingStrategy<>();
        strategy.setType(Transactions.class);
        String[] columns = new String[] {"prodCode", "basketID"};
        strategy.setColumnMapping(columns);
        return strategy;
    }

    private Map<String, Map<Integer, List<String>>> addAllProducts(List list) {
        Map<String, Map<Integer, List<String>>> products = new HashMap<>();
        for (Object object : list) {                                            // Adding all products to map
            Transactions transactions = (Transactions) object;
            prodCode = transactions.getProdCode();
            basketID = transactions.getBasketID();
            if (products.containsKey(prodCode)) {
                Map<Integer, List<String>> innerMap = products.get(prodCode);
                int periodicity = 1;
                List<String> oldBaskets = null;
                for (Map.Entry<Integer, List<String>> entry : innerMap.entrySet()) {
                    periodicity = entry.getKey() + 1;
                    oldBaskets = entry.getValue();
                    oldBaskets.add(basketID);
                }
                innerMap.clear();
                innerMap.put(periodicity, oldBaskets);
                products.put(prodCode, innerMap);
            } else {
                List<String> baskets = new ArrayList<>();
                baskets.add(basketID);
                Map<Integer, List<String>> innerMap = new HashMap<>();
                innerMap.put(1, baskets);
                products.put(prodCode, innerMap);
            }
        }
        return products;
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public String getBasketID() {
        return basketID;
    }

    public void setBasketID(String basketID) {
        this.basketID = basketID;
    }
}
