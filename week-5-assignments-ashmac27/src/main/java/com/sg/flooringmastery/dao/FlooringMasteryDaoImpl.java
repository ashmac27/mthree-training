package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Order;
import com.sg.flooringmastery.model.Product;
import com.sg.flooringmastery.model.Tax;
import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import static java.lang.Integer.parseInt;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FlooringMasteryDaoImpl implements FlooringMasteryDao {

    //declaring private values
    public static final String DELIMITER = ",";
    private final Map<Integer, Order> orders = new HashMap<>();
    private String ORDER_FILE;
    private final Map<String, Product> products = new HashMap<>();
    private String PRODUCT_FILE;
    private final Map<String, Tax> taxes = new HashMap<>();
    private String TAX_FILE;

    /*
    All methods below are to either get data, load that data from the file
    , write the users input to file
     */

    @Override
    public Map<Integer, Order> getAllOrdersThroughDate(String dateString) throws FlooringMasteryPersistenceException{
        this.loadOrder("./SampleFileData/Orders/Orders_" + dateString.substring(0, 2) + dateString.substring(3, 5) + dateString.substring(6,10) + ".txt");
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("MM-dd-yyyy"));

        Map<Integer, Order> map = new HashMap<>();
        orders.entrySet().stream().filter(entry -> (entry.getValue().getOrderDate().isEqual(date))).forEachOrdered(entry -> {
            map.put(entry.getKey(), entry.getValue());
        });

        return map;
    }

    @Override
    public void addOrder(Order order) throws FlooringMasteryPersistenceException{
        orders.put(order.getOrderNumber(),order );
        this.writeOrder("./SampleFileData/Orders/Orders_" + order.getOrderDate().toString().substring(5,7) + order.getOrderDate().toString().substring(8,10) + order.getOrderDate().toString().substring(0, 4) + ".txt");
    }

    @Override
    public void removeOrder(int orderNumber) {
        orders.remove(orderNumber);
    }

    @Override
    public Order getOrder(int orderNumber) {
        return orders.get(orderNumber);
    }

    @Override
    public int getNewOrderNumber() {
        Map.Entry<Integer, Order> entryWithMaxKey = null;
        for (Map.Entry<Integer, Order> currentEntry : orders.entrySet()) {

            if (entryWithMaxKey == null || currentEntry.getKey().compareTo(entryWithMaxKey.getKey())> 0) {
                entryWithMaxKey = currentEntry;
            }
        }

        // Returns the entry with the highest key
        return entryWithMaxKey.getKey() + 1;
    }

    @Override
    public Product getProduct(String productName) {
        return products.get(productName);
    }

    @Override
    public Tax getTax(String state) {
        return taxes.get(state);
    }

    @Override
    public List<Order> getAllOrders() {

        //System.out.println("Initial Mappings are: " + orders);
        return new ArrayList(orders.values());
    }

    @Override
    public List<Product> getAllProducts() {

        //System.out.println("Initial Mappings are: " + orders);
        return new ArrayList(products.values());
    }

    @Override
    public List<Tax> getAllTaxes() {
        return new ArrayList(taxes.values());
    }

    @Override
    public void writeOrder(String fileName) throws FlooringMasteryPersistenceException {

        try {
            File file = new File(fileName);
            file.createNewFile();

        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException("error creating file, IOException");
        }

        try (PrintWriter out = WriteFile(fileName)) {

            String orderAsText;

            out.println(ORDER_FILE);

            out.flush();
            List<Order> orderList = this.getAllOrders();
            for (Order currentOrder : orderList) {

                orderAsText = currentOrder.getOrderNumber() + DELIMITER;

                orderAsText += currentOrder.getCustomerName() + DELIMITER;
                orderAsText += currentOrder.getState() + DELIMITER;
                orderAsText += currentOrder.getTaxRate().toString() + DELIMITER;
                orderAsText += currentOrder.getProductType() + DELIMITER;
                orderAsText += currentOrder.getArea().toString() + DELIMITER;
                orderAsText += currentOrder.getCostPerSquareFoot().toString() + DELIMITER;
                orderAsText += currentOrder.getLaborCostPerSquareFoot().toString() + DELIMITER;
                orderAsText += currentOrder.getMaterialCost().toString() + DELIMITER;
                orderAsText += currentOrder.getLaborCost().toString() + DELIMITER;
                orderAsText += currentOrder.getTax().toString() + DELIMITER;
                orderAsText += currentOrder.getTotal().toString();

                out.println(orderAsText);
                out.flush();
                orderAsText = "";
            }
        }
    }

    @Override
    public void loadOrder(String file) throws FlooringMasteryPersistenceException {
        try (
              Scanner scanner = loadFile(file)) {
            String currentLine;
            String[] orderTokens;
            Order currentOrder;
            ORDER_FILE = scanner.nextLine();
            while (scanner.hasNextLine()) {
                currentOrder = new Order();
                currentLine = scanner.nextLine();

                orderTokens = currentLine.split(DELIMITER);

                currentOrder.setOrderNumber(parseInt(orderTokens[0]));
                currentOrder.setCustomerName(orderTokens[1]);
                currentOrder.setState(orderTokens[2]);
                currentOrder.setTaxRate(new BigDecimal(orderTokens[3]));
                currentOrder.setProductType(orderTokens[4]);
                currentOrder.setArea(new BigDecimal(orderTokens[5]));
                currentOrder.setCostPerSquareFoot(new BigDecimal(orderTokens[6]));
                currentOrder.setLaborCostPerSquareFoot(new BigDecimal(orderTokens[7]));
                currentOrder.setMaterialCost(new BigDecimal(orderTokens[8]));
                currentOrder.setLaborCost(new BigDecimal(orderTokens[9]));
                currentOrder.setTax(new BigDecimal(orderTokens[10]));
                currentOrder.setTotal(new BigDecimal(orderTokens[11]));

                int stringIndex = 31;
                currentOrder.setOrderDate(LocalDate.parse(file.substring(stringIndex, stringIndex + 2) + "-"
                        + file.substring(stringIndex + 2, stringIndex + 4) + "-"
                        + file.substring(stringIndex + 4, stringIndex + 8), DateTimeFormatter.ofPattern("MM-dd-yyyy")));

                orders.put(currentOrder.getOrderNumber(), currentOrder);
            }
        }
    }

    @Override
    public void writeProducts(String fileName) throws FlooringMasteryPersistenceException {
        try (PrintWriter out = WriteFile(fileName)) {

            String productAsText;
            out.println(PRODUCT_FILE);
            out.flush();
            List<Product> productList = this.getAllProducts();
            for (Product currentProduct : productList) {
                productAsText = currentProduct.getProductType() + DELIMITER;

                productAsText += currentProduct.getCostPerSquareFoot() + DELIMITER;
                productAsText += currentProduct.getLaborCostPerSquareFoot() + DELIMITER;

                out.println(productAsText);
                out.flush();
                productAsText = "";
            }
        }
    }

    @Override
    public void loadProducts(String fileName) throws FlooringMasteryPersistenceException {
        try (Scanner scanner = loadFile(fileName)) {
            String currentLine;
            String[] productTokens;
            Product currentProduct;
            PRODUCT_FILE = scanner.nextLine();
            while (scanner.hasNextLine()) {
                // get the next line in the file
                currentProduct = new Product();
                currentLine = scanner.nextLine();

                productTokens = currentLine.split(DELIMITER);

                currentProduct.setProductType(productTokens[0]);
                currentProduct.setCostPerSquareFoot(new BigDecimal(productTokens[1]));
                currentProduct.setLaborCostPerSquareFoot(new BigDecimal(productTokens[2]));

                products.put(currentProduct.getProductType(), currentProduct);
            }
        }
    }

    @Override
    public void writeTaxes(String fileName) throws FlooringMasteryPersistenceException {
        try (PrintWriter out = WriteFile(fileName)) {
            String taxAsText;
            out.println(TAX_FILE);
            out.flush();
            List<Tax> taxList = this.getAllTaxes();
            for (Tax currentTax : taxList) {
                taxAsText = currentTax.getStateAbbrev() + DELIMITER;

                taxAsText += currentTax.getStateName() + DELIMITER;
                taxAsText += currentTax.getTaxRate() + DELIMITER;

                out.println(taxAsText);
                out.flush();
                taxAsText = "";
            }
        }
    }

    public void loadTaxes(String fileName) throws FlooringMasteryPersistenceException {
        try (Scanner scanner = loadFile(fileName)) {
            String currentLine;
            String[] taxTokens;
            Tax currentTax;
            TAX_FILE = scanner.nextLine();
            while (scanner.hasNextLine()) {
                currentTax = new Tax();
                currentLine = scanner.nextLine();

                taxTokens = currentLine.split(DELIMITER);

                currentTax.setStateAbbreviation(taxTokens[0]);
                currentTax.setStateName(taxTokens[1]);
                currentTax.setTaxRate(new BigDecimal(taxTokens[2]));

                taxes.put(currentTax.getStateAbbrev(), currentTax);
            }
        }
    }

    @Override
    public void loadProductsAndTaxes(String productsFile, String taxesFile) throws FlooringMasteryPersistenceException {
        loadProducts(productsFile);
        loadTaxes(taxesFile);
    }

    private Scanner loadFile(String fileName) throws FlooringMasteryPersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(fileName)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException(
                    "-_- Could not load data into memory, from: " + fileName, e);
        }
        return scanner;
    }

    private PrintWriter WriteFile(String fileName) throws FlooringMasteryPersistenceException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(fileName));
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException("Not able to save data to file:  " + fileName, e);
        }
        return out;
    }






}
