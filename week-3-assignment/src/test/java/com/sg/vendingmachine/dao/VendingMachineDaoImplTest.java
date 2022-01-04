package com.sg.vendingmachine.dao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.sg.vendingmachine.dto.Item;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import com.sg.vendingmachine.dao.VendingMachineDaoImpl;


public class VendingMachineDaoImplTest  {

    VendingMachineDao testDao;
    public VendingMachineDaoImplTest(){
    }

    @Test
    public void testWriteMachine() throws Exception  {
        String testFile = "vendingMachineTest.txt";
        new FileWriter(testFile, true);
        testDao = new VendingMachineDaoImpl(testFile);
        testDao.loadMachine();
        testDao.writeMachine();

        assertEquals(testDao.getAllItems().size(), 2, "There should be 2 in this file");
    }


    @Test
    public void testLoadMachine() throws Exception{

        String testFile = "vendingMachineTest.txt";
        new FileWriter(testFile, true);
        testDao = new VendingMachineDaoImpl(testFile);


        Item testItem1 = new Item();
        testItem1.setName("Sour Patch");
        testItem1.setPrice(2.00);
        testItem1.setStock(2);
        Item testItem2 = new Item();
        testItem2.setName("Skittles");
        testItem2.setPrice(2.50);
        testItem2.setStock(2);

        // Getting Result
        testDao.loadMachine();
        List<Item> resultList = testDao.getAllItems();


        // Getting expected results
        List<Item> expectedResultList = new ArrayList<Item>();
        expectedResultList.add(testItem1);
        expectedResultList.add(testItem2);

        // Assert
        assertEquals(expectedResultList.size(), resultList.size() ,"Should be loaded from the file" );

    }


    @Test
    public void testGetAllItems() throws Exception{

        String testFile = "vendingMachineTest.txt";
        new FileWriter(testFile, true);
        testDao = new VendingMachineDaoImpl(testFile);
        testDao.loadMachine();
        testDao.writeMachine();

        //Assign
        Item testItem1 = new Item();
        testItem1.setName("Sour Patch");
        testItem1.setPrice(2.00);
        testItem1.setStock(1);

        Item testItem2 = new Item();
        testItem2.setName("Skittles");
        testItem2.setPrice(2.50);
        testItem2.setStock(2);

        //Assert
        assertEquals(2, testDao.getAllItems().size(), "We should have 2 here");
        assertTrue(testDao.getAllItems().get(0).getName().equals(testItem1.getName()), "Should say Sour Patch ");

    }


    @Test
    public void testGetItem() throws Exception {
        String testFile = "vendingMachineTest.txt";
        new FileWriter(testFile, true);
        testDao = new VendingMachineDaoImpl(testFile);
        testDao.loadMachine();
        //testDao.writeMachine();

        Item testItem1 = new Item();
        testItem1.setName("Sour Patch");
        testItem1.setPrice(2.00);
        testItem1.setStock(2);

        Item shouldBeSourPatch = testDao.getItem(testItem1.getName());

        String testDaoName = testDao.getAllItems().get(0).getName();

        assertNotNull(shouldBeSourPatch, "Getting index 0 shouldnt be null");
        assertEquals(shouldBeSourPatch.getName(), testDaoName, "Should be truueee" );
        //assertEquals(shouldBeSourPatch.getName(), getItem(testDaoName), "Should be truueee" );
    }


    @Test
    public void testDecreaseStock() throws Exception{

        String testFile = "vendingMachineTest.txt";
        new FileWriter(testFile, true);
        testDao = new VendingMachineDaoImpl(testFile);
        testDao.loadMachine();

        Item testItem1 = new Item();
        testItem1.setName("Sour Patch");
        testItem1.setPrice(2.00);
        testItem1.setStock(2);

        //System.out.println(testItem1);

        testDao.decreaseStock(testItem1);

        assertNotEquals(5, testDao.getItem(testItem1.getName()).getStock(), "Stock shouldn't be 5");
    }



}
