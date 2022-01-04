package com.sg.vendingmachine.service;

import java.math.BigDecimal;
import com.sg.vendingmachine.dto.Item;
import com.sg.vendingmachine.dto.Change;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import java.util.List;

public interface VendingMachineServiceLayer {

    List<Item> getAllItems() throws VendingMachinePersistenceException;

    Item getItem(String name) throws VendingMachinePersistenceException;

    Change purchaseItem(String code, BigDecimal money) throws VendingMachinePersistenceException, InsufficientFundsException, NoItemInventoryException;

    void writeMachine() throws VendingMachinePersistenceException;

    void loadMachine() throws VendingMachinePersistenceException;
}

