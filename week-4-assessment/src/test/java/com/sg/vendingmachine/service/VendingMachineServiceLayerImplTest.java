package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineAuditDao;
import com.sg.vendingmachine.dao.VendingMachineAuditDaoImpl;
import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachineDaoImpl;
import com.sg.vendingmachine.dto.Item;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class VendingMachineServiceLayerImplTest {

    private VendingMachineServiceLayer service;
    VendingMachineDao dao;
    VendingMachineAuditDao auditDao;

    public VendingMachineServiceLayerImplTest(){
        VendingMachineDao dao = new VendingMachineDaoStubImpl();
        VendingMachineAuditDao auditDao = new VendingMachineAuditDaoImpl();

        service = new VendingMachineServiceLayerImpl(dao,auditDao );
    }


    @Test
    public void testGetItem() throws Exception {

        //Arrange
        Item testItem1 = new Item();
        testItem1.setName("Inka");
        testItem1.setPrice(2.00);
        testItem1.setStock(3);

        //Act and Assert
        Item shouldBeInka = service.getItem(testItem1.getName());
        assertNotNull(shouldBeInka, "Getting inka shouldnt be null" );

        assertEquals(testItem1, shouldBeInka, "Should be equal to eachother");

        Item shouldBeNull = service.getItem("blah");
        assertNull(shouldBeNull,"Should be null" );
    }

    @Test
    public void getAllItems() throws Exception{
        //Arrange
        Item testItem1 = new Item();
        testItem1.setName("Inka");
        testItem1.setPrice(2.00);
        testItem1.setStock(3);

        //Act and Assert
        assertEquals(1,service.getAllItems().size() ,"Should only have 1 item" );
        assertTrue(service.getAllItems().contains(testItem1),"The one item is inka" );
    }

    @Test
    public void getPurchaseItem() throws Exception{
        String testFile = "vendingMachineTest.txt";
        new FileWriter(testFile, true);

        dao = new VendingMachineDaoImpl(testFile);
        dao.loadMachine();

        VendingMachineAuditDao auditDao2 = new VendingMachineAuditDaoImpl();

        service = new VendingMachineServiceLayerImpl(dao,auditDao2 );

        // Arrange
        Item testItem1 = new Item();
        testItem1.setName("Sour Patch");
        testItem1.setPrice(2.00);
        testItem1.setStock(2);

        //String name = testItem1.getName();

        String code = "Sour Patch";
        BigDecimal money = new BigDecimal(1.00);

        //Act and Assert
        service.getAllItems();

        try {
            service.purchaseItem(code, money);
        } catch(NoItemInventoryException e){
            fail("Wrong exception thrown");
        } catch(InsufficientFundsException e){
            //This should print because it's not enough money
            //System.out.println("not enough money");
        }


    }
}