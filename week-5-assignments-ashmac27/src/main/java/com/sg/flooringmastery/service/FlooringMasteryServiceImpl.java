package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringMasteryDao;
import com.sg.flooringmastery.dao.FlooringMasteryDaoImpl;
import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.service.InvalidOrderEntryException;
import com.sg.flooringmastery.model.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlooringMasteryServiceImpl implements FlooringMasteryServiceLayer {
    private FlooringMasteryDao dao;
    public FlooringMasteryServiceImpl(FlooringMasteryDao dao) {
        this.dao = dao;
    }

    public FlooringMasteryServiceImpl() {
        this.dao = new FlooringMasteryDaoImpl();
    }

    //getting and loading all the files used for the app.
    // Order fils, tax files, etc
    @Override
    public void getLoadAllFiles(String orderFile, String productsFile, String taxesFile) throws FlooringMasteryPersistenceException {
        dao.loadOrder(orderFile);
        dao.loadProductsAndTaxes(productsFile,taxesFile );
    }

    @Override
    public void writeOrdersToFile() throws FlooringMasteryPersistenceException {

        dao.writeOrder("./SampleFileData/Orders/Orders_" + String.format("%02d%02d%d", LocalDate.now().getMonthValue(),LocalDate.now().getDayOfMonth(), LocalDate.now().getYear()) + ".txt");
    }

    @Override
    public void writeOrdersToFile(String fileName) throws FlooringMasteryPersistenceException, InvalidOrderEntryException {

        dao.writeOrder("./SampleFileData/Orders/Orders_" + DateToFileDate(fileName) + ".txt");
    }

    private String DateToFileDate(String dateAsText) throws InvalidOrderEntryException {

       // System.out.println("Hellloooo");
        this.validateDate(dateAsText);
        return dateAsText.substring(0, 2) + dateAsText.substring(3, 5) + dateAsText.substring(6);
    }

    @Override
    public LocalDate validateDate(String dateAsString) throws InvalidOrderEntryException {
        LocalDate date;
        try {
            //System.out.println(dateAsString);
            date = LocalDate.parse(dateAsString, DateTimeFormatter.ofPattern("MM-dd-yyyy"));
            //System.out.println(date);
        } catch (DateTimeParseException e) {

            try {
               // System.out.println(date.toString());
                date = LocalDate.parse(dateAsString, DateTimeFormatter.ofPattern("yyyy-dd-MM"));
                //date = LocalDate.parse(dateAsString, DateTimeFormatter.ofPattern("MM-dd-yyyy"));
            } catch (DateTimeParseException DTPE) {
                throw new InvalidOrderEntryException("Invalid date");
            }
        }

        return date;
    }

    @Override
    public boolean isFutureDate(LocalDate date) throws InvalidOrderEntryException {
        if (!date.isAfter(LocalDate.now())) {
            throw new InvalidOrderEntryException("Invalid Date, must be after today");
        }
        return true;
    }

    @Override
    public String validateCustomerName(String name) throws InvalidOrderEntryException {
        String regex = "[#$%^&*()_+{}\\[\\]:;<>/?-]";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(name);
        if (m.find() || name.isEmpty()) {
            throw new InvalidOrderEntryException("Invalid Name");
        }
        return name;
    }

    @Override
    public Map<Integer, Order> getAllOrdersDate(String dateAsString) throws InvalidOrderEntryException, FlooringMasteryPersistenceException {
        Map<Integer, Order> map = dao.getAllOrdersThroughDate(dateAsString);
        if (map.isEmpty()) {
            throw new InvalidOrderEntryException("no orders with that date");
        }
        return map;
    }

    @Override
    public List<Order> getAllOrders() {
        return dao.getAllOrders();
    }

    @Override
    public List<Product> getAllProducts() {
        return dao.getAllProducts();
    }

    @Override
    public void addOrder(Order order) throws InvalidOrderEntryException, FlooringMasteryPersistenceException {

        order.setOrderNumber(dao.getNewOrderNumber());
        order.setTaxRate(dao.getTax(order.getState()).getTaxRate());
        order.setCostPerSquareFoot(dao.getProduct(order.getProductType()).getCostPerSquareFoot());
        order.setLaborCostPerSquareFoot(dao.getProduct(order.getProductType()).getLaborCostPerSquareFoot());

        order.setMaterialCost(order.getArea().multiply(order.getCostPerSquareFoot()).setScale(2, RoundingMode.HALF_EVEN));
        order.setLaborCost(order.getArea().multiply(order.getLaborCostPerSquareFoot()).setScale(2, RoundingMode.HALF_EVEN));

        BigDecimal subtotal = order.getMaterialCost().add(order.getLaborCost());
        BigDecimal taxRate = dao.getTax(order.getState()).getTaxRate().divide(new BigDecimal("100").setScale(2, RoundingMode.HALF_EVEN));
        order.setTax(subtotal.multiply(taxRate));

        order.setTotal(subtotal.add(order.getTax()).setScale(2, RoundingMode.HALF_EVEN));

        dao.addOrder(order);
    }

    @Override
    public Order getOrder(int orderNumber, LocalDate date) throws InvalidOrderEntryException {
        Order order = dao.getOrder(orderNumber);
        if (order == null || !order.getOrderDate().equals(date)) {

            throw new InvalidOrderEntryException("no orders with that date and order number");
        }
        return order;
    }

    @Override
    public Order getOrder(int orderNumber) throws InvalidOrderEntryException {
        Order order = dao.getOrder(orderNumber);
        if (order == null) {
            throw new InvalidOrderEntryException("no orders with that order number");
        }
        return order;
    }

    @Override
    public void editOrder(Order order) throws InvalidOrderEntryException, FlooringMasteryPersistenceException {
        Order orderWithMods = dao.getOrder(order.getOrderNumber());
        if (order.getCustomerName() != "" && order.getCustomerName() != "\n" && order.getCustomerName() != null) {
            orderWithMods.setCustomerName(order.getCustomerName());
        }
        if (order.getState() != "" && order.getState() != "\n" && order.getState() != null) {
            orderWithMods.setState(order.getState());
        }
        if (order.getProductType() != "" && order.getProductType() != "\n" && order.getProductType() != null) {
            orderWithMods.setProductType(order.getProductType());
        }
        if (order.getArea() != null && order.getArea().signum() != 0) {
            orderWithMods.setArea(order.getArea());
        }
        this.addOrder(orderWithMods);
    }

    @Override
    public void removeOrder(int orderNumber) throws InvalidOrderEntryException {
        try {
            dao.removeOrder(orderNumber);
        } catch (FlooringMasteryPersistenceException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void validateOrder(Order order) throws InvalidOrderEntryException {
        if (!order.getOrderDate().isAfter(LocalDate.now())) {
            throw new InvalidOrderEntryException("Invalid Date, must be after today");
        }
        validateCustomerName(order.getCustomerName());
        if (dao.getTax(order.getState()) == null) {
            throw new InvalidOrderEntryException("Invalid State");
        }
        if (dao.getProduct(order.getProductType()) == null) {
            throw new InvalidOrderEntryException("Invalid Product");
        }
        if (order.getArea().compareTo(new BigDecimal("100")) == -1) {
            throw new InvalidOrderEntryException("Invalid Area, must be greater than 100");
        }
    }
}
