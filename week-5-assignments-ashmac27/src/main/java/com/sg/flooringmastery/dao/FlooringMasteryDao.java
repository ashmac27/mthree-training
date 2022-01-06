package com.sg.flooringmastery.dao;

import java.util.List;
import java.util.Map;
import com.sg.flooringmastery.model.*;
import com.sg.flooringmastery.service.InvalidOrderEntryException;

public interface FlooringMasteryDao {

    Map<Integer, Order> getAllOrdersThroughDate(String date) throws FlooringMasteryPersistenceException;

    void addOrder(Order order) throws FlooringMasteryPersistenceException;

    void removeOrder(int orderNumber) throws FlooringMasteryPersistenceException;

    Order getOrder(int orderNumber);

    int getNewOrderNumber();

    Product getProduct(String productName);

    Tax getTax(String state);

    List<Order> getAllOrders();

    List<Product> getAllProducts();

    List<Tax> getAllTaxes();

    void writeOrder(String file) throws FlooringMasteryPersistenceException;

    void loadOrder(String file) throws FlooringMasteryPersistenceException;

    void writeProducts(String file) throws FlooringMasteryPersistenceException;

    void loadProducts(String file) throws FlooringMasteryPersistenceException;

    void writeTaxes(String file) throws FlooringMasteryPersistenceException;

    void loadTaxes(String file) throws FlooringMasteryPersistenceException;

    void loadProductsAndTaxes(String productsFile, String taxesFile) throws FlooringMasteryPersistenceException;

}
