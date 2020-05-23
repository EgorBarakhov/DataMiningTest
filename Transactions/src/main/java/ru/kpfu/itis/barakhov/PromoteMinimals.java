package ru.kpfu.itis.barakhov;
/*
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class PromoteMinimals {
    private String productID;
    private int periodicity;

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public int getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(int periodicity) {
        this.periodicity = periodicity;
    }

    public static void main(String[] args) throws FileNotFoundException {
        CsvToBean csv = new CsvToBean();
        String csvFilename = "target/data.csv";
        CSVReader csvReader = new CSVReader(new FileReader(csvFilename), ',', '"', 1);
        List list = csv.parse(setColumnMapping(), csvReader);
        for (Object object : list) {
            PromoteMinimals promoteMinimals = (PromoteMinimals) object;
            if (promoteMinimals.productID)
        }

    }

    private static ColumnPositionMappingStrategy<PromoteMinimals> setColumnMapping() {
        ColumnPositionMappingStrategy<PromoteMinimals> strategy = new ColumnPositionMappingStrategy<>();
        strategy.setType(PromoteMinimals.class);
        String[] columns = new String[] {"productID", "periodicity"};
        strategy.setColumnMapping(columns);
        return strategy;
    }

    private List<String> promote(String idToPromote) {

        return null;
    }

}
 */
public class PromoteMinimals {

}