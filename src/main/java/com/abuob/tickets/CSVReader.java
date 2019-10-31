package com.abuob.tickets;

import com.abuob.tickets.entity.Customer;
import com.abuob.tickets.entity.Inventory;
import com.abuob.tickets.repository.InventoryRepository;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "ticket")
public final class CSVReader {

    private static final Logger logger = LoggerFactory.getLogger(CSVReader.class);

    private static final String COMMA_DELIMITER = ",";

    private InventoryRepository inventoryRepository;

    private String csvFilePath;

    private static final Customer customer0 = new Customer(1L);
    private static final Customer customer1 = new Customer(2L);
    private static final Customer customer2 = new Customer(3L);
    private static final Customer customer3 = new Customer(4L);

    @Autowired
    public CSVReader(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @PostConstruct
    public void loadCSVFile() {
        if (csvFilePath == null) {
            logger.error("CSV File Path missing");
            throw new IllegalArgumentException("Failed to initialize CSV File Path");
        }
        parseCSV(csvFilePath);
    }

    /**
     * Private utility method to load CSV file contents into memory
     *
     * @param csvFilePath The path to the csv file
     **/
    private void parseCSV(String csvFilePath) {

        logger.info("parseCSV - processing file:" + csvFilePath);
        BufferedReader fileReader = null;

        List<Inventory> inventoryList = Lists.newArrayList();

        String eventIdStr;
        String sectionStr;
        String quantityStr;
        String priceStr;
        String rowStr;
        Inventory inventory = null;

        Integer section;

        try {
            String line = "";
            fileReader = new BufferedReader(new FileReader(csvFilePath));

            //Read the file line by line
            while ((line = fileReader.readLine()) != null) {
                //Get all tokens available in line
                String[] tokens = line.split(COMMA_DELIMITER);

                eventIdStr = tokens[0];
                sectionStr = tokens[1];
                quantityStr = tokens[2];
                priceStr = tokens[3];
                rowStr = tokens[4];

                //Parse out the bad row data
                if (rowStr.length() > 2 || !StringUtils.isAlphanumeric(rowStr))
                    continue;

                //Input validation
                if (!isLong(eventIdStr) || !isInteger(sectionStr) || !isInteger(quantityStr) || !isDouble(priceStr))
                    continue;

                section = Integer.parseInt(sectionStr);

                logger.info("parseCSV - seeding customer test data");
                //Seed test data
                if (section % 4 == 0)
                    inventory = new Inventory(Long.parseLong(eventIdStr), Integer.parseInt(sectionStr),
                            Integer.parseInt(quantityStr), new BigDecimal(priceStr), rowStr, customer0);

                if (section % 4 == 1)
                    inventory = new Inventory(Long.parseLong(eventIdStr), Integer.parseInt(sectionStr),
                            Integer.parseInt(quantityStr), new BigDecimal(priceStr), rowStr, customer1);

                if (section % 4 == 2)
                    inventory = new Inventory(Long.parseLong(eventIdStr), Integer.parseInt(sectionStr),
                            Integer.parseInt(quantityStr), new BigDecimal(priceStr), rowStr, customer2);

                if (section % 4 == 3)
                    inventory = new Inventory(Long.parseLong(eventIdStr), Integer.parseInt(sectionStr),
                            Integer.parseInt(quantityStr), new BigDecimal(priceStr), rowStr, customer3);

                inventoryList.add(inventory);
            }

            logger.info("parseCSV - saving test data in repository");
            inventoryRepository.saveAll(inventoryList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isInteger(String strNum) {
        try {
            int i = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isLong(String strNum) {
        try {
            long l = Long.parseLong(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isDouble(String strNum) {
        try {
            Double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void setCsvFilePath(String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }
}



