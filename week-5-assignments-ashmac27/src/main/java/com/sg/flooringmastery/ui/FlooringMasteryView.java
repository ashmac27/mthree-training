package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.model.*;
import java.util.Map;

public class FlooringMasteryView {
    private UserIO io;
    public FlooringMasteryView(UserIO io){
        this.io = io;
    }

    //Displaying Menu to user
    public int displayMenuGetSelection(){
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.print("* <<Flooring Program>>                                               ");
        io.print("* 1. Display Orders                                                  ");
        io.print("* 2. Add an Order                                                    ");
        io.print("* 3. Edit an Order                                                   ");
        io.print("* 4. Remove an Order                                                 ");
        io.print("* 5. Export All Data                                                 ");
        io.print("* 6. Quit                                                            ");
        io.print("*                                                                    ");
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        return io.readInt("");
    }

    public void displayOrders(Map<Integer, Order> orders){
        io.print("Orders: ");
        for (Order CO : orders.values()) {
            io.print("Order Number: " + CO.getOrderNumber());
            io.print("Name: " + CO.getCustomerName());
            io.print("Product Type: " + CO.getProductType());
            io.print("Area: " + CO.getArea());
            io.print("Total: " + CO.getTotal());
            io.print("================================================");
        }
    }

    public void displayOrder(Order order){
        io.print("Order: ");
        io.print("Order Number: " + order.getOrderNumber());
        io.print("Name: " + order.getCustomerName());
        io.print("Product Type: " + order.getProductType());
        io.print("Area: " + order.getArea());
        io.print("Total: " + order.getTotal());

    }

    // Text to show user the directions of what they should input

    public String getCustomerName() {
        return io.readString("What is the customer name?");
    }

    public String getState() {
        return io.readString("What is the state? (Abreviation)");
    }

    public String getProductType() {
        return io.readString("What is the product type?");
    }

    public String getArea() {
        return io.readString("What is the area?");
    }

    public String askForDate() {
        return io.readString("What is the date of the order? (MM-DD-YYYY)");
    }

    public String askForFileDate() {
        return io.readString("What is the date of the file to save as? (MM-DD-YYYY)");
    }

    public int askForOrderNumber() {
        return io.readInt("What is the order number?");
    }

    public void printProducts(Map<String, Product> products) {

        io.print("Products: ");
        products.values().forEach(currentProduct -> {
            io.print(currentProduct.getProductType() + "   Material Cost: " + currentProduct.getMaterialCostAsString() + "   Labor Cost: " + currentProduct.getLaborCostAsString());
        });

    }

    public void exportDataSuccessBanner() {
        io.print("Order Data Exported Successfully.");
    }

    public void displayExitBanner() {
        io.print("Goodbye");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }
}
