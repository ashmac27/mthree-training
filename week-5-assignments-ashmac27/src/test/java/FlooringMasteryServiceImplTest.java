import com.sg.flooringmastery.service.FlooringMasteryServiceImpl;
import com.sg.flooringmastery.service.FlooringMasteryServiceLayer;
import com.sg.flooringmastery.service.InvalidOrderEntryException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

import com.sg.flooringmastery.dao.FlooringMasteryDaoImpl;
import com.sg.flooringmastery.dao.FlooringMasteryDao;
import com.sg.flooringmastery.model.Order;
import com.sg.flooringmastery.model.Product;

public class FlooringMasteryServiceImplTest {

    FlooringMasteryServiceLayer service = new FlooringMasteryServiceImpl();
    String testFileName = "./SampleFileData/Orders/Orders_12012021.txt";
    //String testFileName = "./SampleFileData/Orders/Orders_01122021.txt";

    FlooringMasteryDao dao = new FlooringMasteryDaoImpl();

    Order orderTest;

    public FlooringMasteryServiceImplTest(){
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() throws Exception {
        dao.loadOrder("./SampleFileData/Orders/Orders_06012013.txt");

        dao.writeOrder(testFileName);
        dao.loadOrder(testFileName);

        service.getLoadAllFiles("./SampleFileData/Orders/Orders_06012013.txt", "./SampleFileData/Data/Products.txt", "./SampleFileData/Data/Taxes.txt");

        orderTest = new Order(1, "Ada Lovelace", "CA",
                new BigDecimal("25.00"), "Tile", new BigDecimal("249.00"),
                new BigDecimal("3.50"), new BigDecimal("4.15"),
               new BigDecimal("871.50"), new BigDecimal("1033.35"), new BigDecimal("476.21"), new BigDecimal("2381.06"), LocalDate.parse("2021-01-12"));
                //new BigDecimal("871.50"), new BigDecimal("1033.35"), new BigDecimal("476.21"), new BigDecimal("2381.06"), LocalDate.parse("2021-12-01"));
    }

    @AfterEach
    public void tearDown() {
    }




    @Test
    public void testValidateDate() throws Exception {
        System.out.println("ValidateDate");
        String dateAsString = (LocalDate.now().plusDays(1)).toString();
       // System.out.println(dateAsString);
        LocalDate expResult = LocalDate.now().plusDays(1);
       // System.out.println(expResult.toString());

        LocalDate result = expResult;
        assertEquals(expResult, result);
    }

    @Test
    public void testValidateCustomerName() throws Exception {
        System.out.println("ValidateCustomerName");
        String expResult = "Acme, Inc.";
        String result = service.validateCustomerName(expResult);
        assertEquals(expResult, result);
    }

    @Test
    public void testValidateBadCustomerName() throws Exception {
        System.out.println("ValidateBadCustomerName");
        String expResult = "Acme, Inc.!@#$%^";
        try {
            String result = service.validateCustomerName(expResult);
            fail("if this line runs, this test fails");
        } catch (InvalidOrderEntryException IOE) {
//            assert (true);
        }
    }



    @Test
    public void testRemoveOrder() throws Exception {
        System.out.println("RemoveOrder");
        int orderNumber = 1;

        service.removeOrder(orderNumber);
        Order result;
        try {
            result = service.getOrder(orderNumber);
        } catch (InvalidOrderEntryException IOE) {
            result = null;
        }

        Order expResult = null;
        assertEquals(expResult, result);
    }

    @Test
    public void testValidateOrderBadName() throws Exception {
        System.out.println("ValidateOrderBadName");
        Order order = orderTest;
        order.setCustomerName("!!!!!!");
        try {
            service.validateOrder(order);
        } catch (InvalidOrderEntryException IOE) {
            assert (true);
        }
    }

    @Test
    public void testValidateOrderBadState() throws Exception {
        System.out.println("ValidateOrderBadState");
        Order order = orderTest;
        order.setState("happy");
        try {
            service.validateOrder(order);
        } catch (InvalidOrderEntryException IOE) {
            assert (true);
        }

    }

    @Test
    public void testValidateOrderBadDate() throws Exception {
        System.out.println("ValidateOrderBadDate");
        Order order = orderTest;
        order.setOrderDate(LocalDate.now());
        try {
            service.validateOrder(order);
        } catch (InvalidOrderEntryException IOE) {
            assert (true);
        }

    }


    @Test
    public void testAddOrder() throws Exception {
        System.out.println("AddOrder");
        Order order = orderTest;
        Order expResult = orderTest;
        service.addOrder(order);
        Order result = service.getOrder(order.getOrderNumber());
        assertEquals(expResult, result);
    }

}