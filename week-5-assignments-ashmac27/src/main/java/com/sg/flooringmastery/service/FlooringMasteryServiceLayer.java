package com.sg.flooringmastery.service;

import java.util.Map;
import java.util.List;
import java.time.LocalDate;
import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.model.Order;
import com.sg.flooringmastery.model.Product;

public interface FlooringMasteryServiceLayer {
    void getLoadAllFiles(String orderFile, String productsFile, String taxesFile) throws FlooringMasteryPersistenceException;

    void writeOrdersToFile() throws FlooringMasteryPersistenceException;

    void writeOrdersToFile(String fileName) throws FlooringMasteryPersistenceException, InvalidOrderEntryException;

    LocalDate validateDate(String dateAsString) throws InvalidOrderEntryException;

    boolean isFutureDate(LocalDate date) throws InvalidOrderEntryException;

    String validateCustomerName(String name) throws InvalidOrderEntryException;

    Order getOrder(int orderNumber, LocalDate date) throws InvalidOrderEntryException;

    Order getOrder(int orderNumber) throws InvalidOrderEntryException;

    Map<Integer, Order> getAllOrdersDate(String dateAsString) throws InvalidOrderEntryException, FlooringMasteryPersistenceException;

    List<Order> getAllOrders();

    List<Product> getAllProducts();

    void addOrder(Order order) throws InvalidOrderEntryException, FlooringMasteryPersistenceException;

    void editOrder(Order order) throws InvalidOrderEntryException, FlooringMasteryPersistenceException;

    void removeOrder(int orderNumber) throws InvalidOrderEntryException;

    void validateOrder(Order order) throws InvalidOrderEntryException;
}
