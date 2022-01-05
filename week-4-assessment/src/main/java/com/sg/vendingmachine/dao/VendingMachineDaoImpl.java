package com.sg.vendingmachine.dao;
import com.sg.vendingmachine.dto.Item;
import com.sg.vendingmachine.service.NoItemInventoryException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class VendingMachineDaoImpl implements VendingMachineDao {

    //declaring private values
    public static final String DELIMITER = "::";
    private final String MACHINE_FILE;
    private Map<String, Item> items = new HashMap<>();


    public VendingMachineDaoImpl(){
        MACHINE_FILE = "vendingMachine.txt";
    }

    // For the purpose of test file
    public VendingMachineDaoImpl(String vendingMachineTextFile){
        MACHINE_FILE = vendingMachineTextFile;
    }

    //How it will be written in the file
    public String marshallItem(Item i) {
        return i.getName() + DELIMITER + i.getPrice() + DELIMITER + i.getStock();
    }

    // How it will be broken apart from the file
    public Item unMarshallItem(String itemInf){

        String[] itemVariables = itemInf.split(DELIMITER);

        Item newItem = new Item();
        newItem.setName(itemVariables[0]);
        newItem.setPrice(Double.parseDouble(itemVariables[1]));
        newItem.setStock(Integer.parseInt(itemVariables[2]));


        /*
        Item newItem = new Item(itemVariables[0], Double.parseDouble(itemVariables[1]), Integer.parseInt(itemVariables[2]));
        */
        return newItem;
    }

    // Method to write to vendinmachine.txt
    public void writeMachine() throws VendingMachinePersistenceException {

        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(MACHINE_FILE));
        } catch (IOException e) {
            throw new VendingMachinePersistenceException(
                    "Failed to write item data.", e);
        }

        String itemAsText;
        List<Item> itemList = this.getAllItems();
        for (Item i : itemList) {

            itemAsText = marshallItem(i);
            out.println(itemAsText);
            out.flush();
        }
        out.close();
    }

    public void loadMachine() throws VendingMachinePersistenceException {
        Scanner scanner;

        //Reads the vending machine file into memory
        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(MACHINE_FILE)));
        } catch (FileNotFoundException e) {
            throw new VendingMachinePersistenceException(
                    "Could not load items into memory.", e);
        }

        // Reading from file and changing marshalledItem into regular text and putting that inside Map
        String currentLine;
        Item currentItem;
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentItem = unMarshallItem(currentLine);

            items.put(currentItem.getName(), currentItem);
        }
        scanner.close();
    }

    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        VendingMachinePersistenceException persistenceException = new VendingMachinePersistenceException("Persistence exception: No items");

        if(items.values()!=null)
            return new ArrayList(items.values());
        else
            throw persistenceException;

    }

    @Override
    public Item getItem(String name) throws VendingMachinePersistenceException {

        VendingMachinePersistenceException pointer = new VendingMachinePersistenceException("Persistence Exception: Null entry");
        if(items.get(name)!=null)
            return items.get(name);
        else
            throw pointer;
    }

    @Override
    public void decreaseStock(Item item) throws VendingMachinePersistenceException, NoItemInventoryException {

        NoItemInventoryException inventoryException = new NoItemInventoryException("No items exception");

        if(item.getStock()!=0){
            item.adjustStock(-1);
            items.put(item.getName(), item);
        }
        else
            throw inventoryException;
    }


}
