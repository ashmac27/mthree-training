import com.sg.flooringmastery.dao.FlooringMasteryDao;
import com.sg.flooringmastery.dao.FlooringMasteryDaoImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.sg.flooringmastery.model.Order;


import static org.junit.jupiter.api.Assertions.*;

public class FlooringMasteryDaoImplTest {

    FlooringMasteryDaoImpl daoTest = new FlooringMasteryDaoImpl();
    String testFile = "./SampleFileData/Orders/Orders_12012021.txt";

    public FlooringMasteryDaoImplTest(){
    }


    @BeforeEach
    public void setUp() throws Exception {

        FlooringMasteryDao dao = new FlooringMasteryDaoImpl();
        Map<Integer, Order> orders = new HashMap<>();


        dao.loadOrder("./SampleFileData/Orders/Orders_06012013.txt");

        dao.writeOrder(testFile);
        dao.loadOrder(testFile);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetAllOrdersFromDate() throws Exception {
        System.out.println("GetAllOrdersFromDate");
        String dateAsString = "12-01-2021";

        daoTest.loadOrder("./SampleFileData/Orders/Orders_12012021.txt");
        Map<Integer, Order> expResult = new HashMap<>();
        expResult.put(1, daoTest.getOrder(1));

        Map<Integer, Order> result = daoTest.getAllOrdersThroughDate(dateAsString);
        assertEquals(expResult, result);
    }


    @Test
    public void testAddOrder() throws Exception {
        System.out.println("AddOrder");
        Order order = new Order(2, "Doctor Who2", "WA",
                new BigDecimal("9.25"), "Wood", new BigDecimal("243.00"),
                new BigDecimal("5.15"), new BigDecimal("4.75"),
                new BigDecimal("1251.45"), new BigDecimal("1154.25"), new BigDecimal("216.51"), new BigDecimal("2622.21"), LocalDate.parse("2021-12-30"));
        daoTest.addOrder(order);
        assertEquals(order, daoTest.getOrder(order.getOrderNumber()));
    }

    @Test
    public void testRemoveOrder() {
        System.out.println("RemoveOrder");
        int orderNumber = 1;
        daoTest.removeOrder(orderNumber);
        assertEquals(null, daoTest.getOrder(orderNumber));
    }


    @Test
    public void testGetOrder() throws Exception {
        System.out.println("getOrder");

//        Order expResult = null;
        Order expResult = new Order(1, "Ada Lovelace", "CA",
                new BigDecimal("25.00"), "Tile", new BigDecimal("249.00"),
                new BigDecimal("3.50"), new BigDecimal("4.15"),
                new BigDecimal("871.50"), new BigDecimal("1033.35"), new BigDecimal("476.21"), new BigDecimal("2381.06"), LocalDate.parse("2022-01-08"));
        daoTest.addOrder(expResult);

        Order result = daoTest.getOrder(expResult.getOrderNumber());
        assertEquals(expResult, result);
    }


}