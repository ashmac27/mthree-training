package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.service.NoItemInventoryException;
import com.sg.vendingmachine.dto.Item;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendingMachineDaoStubImpl implements VendingMachineDao {
    public Item onlyItem;
    private Map<String, Item> items = new HashMap<>();

    public VendingMachineDaoStubImpl(){
        onlyItem = new Item();
        onlyItem.setName("Inka");
        onlyItem.setPrice(2.00);
        onlyItem.setStock(3);
    }

    public VendingMachineDaoStubImpl(Item testItem){
        this.onlyItem = testItem;
    }

    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException{
        List<Item> itemList = new ArrayList<>();
        itemList.add(onlyItem);
        return itemList;
    }

    @Override
    public Item getItem(String name) throws VendingMachinePersistenceException{
        if(name.equals(onlyItem.getName())){
            return onlyItem;
        } else {
            return null;
        }
    }

    @Override
    public void decreaseStock(Item item) throws  NoItemInventoryException{
        NoItemInventoryException inventoryException = new NoItemInventoryException("No items exception");

        if(item.getStock()!= 0){
            item.adjustStock(-1);
            items.put(item.getName(), item);
        }else{
            throw inventoryException;
        }
    }

    @Override
    public void writeMachine() throws VendingMachinePersistenceException {

    }

    @Override
    public void loadMachine() throws VendingMachinePersistenceException {

    }

}