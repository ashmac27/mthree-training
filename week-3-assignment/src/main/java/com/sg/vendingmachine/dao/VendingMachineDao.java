package com.sg.vendingmachine.dao;

import java.util.List;
import com.sg.vendingmachine.dto.Item;
import com.sg.vendingmachine.service.NoItemInventoryException;

public interface VendingMachineDao {
    List<Item> getAllItems() throws VendingMachinePersistenceException;

    Item getItem(String name) throws VendingMachinePersistenceException;

    void decreaseStock(Item item) throws VendingMachinePersistenceException, NoItemInventoryException;

    void  writeMachine() throws VendingMachinePersistenceException;

    void loadMachine() throws VendingMachinePersistenceException;
}
