package com.sg.vendingmachine;

import com.sg.vendingmachine.controller.VendingMachineController;
import com.sg.vendingmachine.dao.VendingMachineAuditDao;
import com.sg.vendingmachine.dao.VendingMachineAuditDaoImpl;
import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachineDaoImpl;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.service.VendingMachineServiceLayer;
import com.sg.vendingmachine.service.VendingMachineServiceLayerImpl;
import com.sg.vendingmachine.ui.UserIO;
import com.sg.vendingmachine.ui.UserIOConsoleImpl;
import com.sg.vendingmachine.ui.VendingMachineView;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) throws VendingMachinePersistenceException{

        ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

        VendingMachineController controller = appContext.getBean("controller", VendingMachineController.class);
        controller.run();

        /*UserIO myIo = new UserIOConsoleImpl();

        VendingMachineView myView = new VendingMachineView(myIo);

        VendingMachineDao myDao = new VendingMachineDaoImpl();

        VendingMachineAuditDao myAuditDao = new VendingMachineAuditDaoImpl();

        VendingMachineServiceLayer myService = new VendingMachineServiceLayerImpl(myDao,myAuditDao);

        VendingMachineController controller = new VendingMachineController(myService,myView);

        controller.run();
*/
    }
}
