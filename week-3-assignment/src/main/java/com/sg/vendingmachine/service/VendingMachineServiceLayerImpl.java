package com.sg.vendingmachine.service;


import com.sg.vendingmachine.dao.VendingMachineAuditDao;
import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Item;
import com.sg.vendingmachine.dto.Change;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer{

    private VendingMachineDao dao;
    private VendingMachineAuditDao auditDao;

    public VendingMachineServiceLayerImpl (VendingMachineDao dao, VendingMachineAuditDao auditDao){
        this.dao = dao;
        this.auditDao = auditDao;
    }

    @Override
    public Item getItem(String name) throws VendingMachinePersistenceException{

        //First we write to file and then get item
        auditDao.writeAuditEntry("Called getItem()");
        return dao.getItem(name);
    }

    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {

        //First we write to audit file and then get the list of Items
        auditDao.writeAuditEntry("Called getAllItems()");
        return dao.getAllItems();
    }

    @Override
    public Change purchaseItem(String code, BigDecimal money) throws VendingMachinePersistenceException, InsufficientFundsException, NoItemInventoryException {
        auditDao.writeAuditEntry("Called purchaseItem()");
        Item item = dao.getItem(code);

        //price will be like "2.00" instead of 2.0 to make it appear as money
        // This is done by setScale
        BigDecimal price = new BigDecimal(item.getPrice()).setScale(2, RoundingMode.HALF_UP);

        // Checking to see if user put in the right amount of money
        // or if there is any of the item left in stock
        if (money.compareTo(price) < 0) {
            throw new InsufficientFundsException("You didn't put in enough money!");
        }
        if (item.getStock() <= 0) {
            throw new NoItemInventoryException("We ran out of this item");
        }

        // Decreasing stock of a certain item
        // writing to audit file
        // getting total pennies as change
        dao.decreaseStock(item);

        auditDao.writeAuditEntry(item.getName() + " was purchased.");

        int totalPennies = new BigDecimal(100).multiply(money.subtract(price)).intValue();

        return new Change(totalPennies);

    }

    @Override
    public void writeMachine() throws VendingMachinePersistenceException {
        auditDao.writeAuditEntry("Attempt to write item data to file.");
        dao.writeMachine();
        auditDao.writeAuditEntry("Item data was successfully written to file.");
    }

    @Override
    public void loadMachine() throws VendingMachinePersistenceException {
        auditDao.writeAuditEntry("Attempt to load item data from file.");
        dao.loadMachine();
        auditDao.writeAuditEntry("Item data was successfuly loaded from file.");
    }

}
