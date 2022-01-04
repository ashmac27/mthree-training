package com.sg.vendingmachine.ui;

import com.sg.vendingmachine.dto.Change;
import com.sg.vendingmachine.dto.Item;
import java.util.List;
import java.math.BigDecimal;
import static java.math.RoundingMode.HALF_UP;

public class VendingMachineView {
    private UserIO io;
    private static int index;

    public VendingMachineView(UserIO io){
        this.io = io;
    }

    public int displayItemsGetSelection(List<Item> items) {
        //This will display the written Vending Machine menu
        io.print("Vending Machine:");
        displayItems(items);
        return io.readInt("Please select: 1 = Enter Money. 2. Exit", 1, 2);
    }
    public double getMoneyEntered() {
        //Getting user's money
        double credit = io.readDouble("Please enter your money:");
        return credit;
    }
    private void displayItems(List<Item> items) {

        // Lambda expression .forEach
        index = 1;
        items.forEach((item) -> {
            BigDecimal price = new BigDecimal(item.getPrice()).setScale(2, HALF_UP);
            io.print(index++ + ". " + item.getName() + ", $" + price + " , Remaining: " + item.getStock());

        });
    }


    public void displayErrorMessage(String message) {
        io.print("=== Error ===");
        io.print(message);
    }


    public void displayExitBanner(){
        io.print("Goodbye.");
    }

    public void displayUnknownCommandBanner(){
        io.print("Unknown command.");
    }

    public String getItemChoice(List <Item> items){
        displayItems(items);
        int numItems = items.size();
        int choice = io.readInt("Please select an item from 1-" + numItems, 1, numItems);
        String item = new String();
        item = items.get(choice-1).getName();
        return item;

    }

    public void displayPurchaseSuccess(Change change) {
        int numQuarters = change.getNumQuarters();
        int numDimes = change.getNumDimes();
        int numNickels = change.getNumNickels();
        int numPennies = change.getNumPennies();
        io.print("Your change is:");
        io.print("Quarters: " + numQuarters);
        io.print("Dimes: " + numDimes);
        io.print("Nickels: " + numNickels);
        io.print("Pennies: " + numPennies + "\n");
    }

}
