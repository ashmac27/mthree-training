package com.sg.flooringmastery;

import com.sg.flooringmastery.controller.FlooringMasteryController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;


public class App {
    public static void main(String[] args) throws FlooringMasteryPersistenceException{

        // using Spring DI for instead of creating new objects for the class here
        ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

        FlooringMasteryController controller = appContext.getBean("controller", FlooringMasteryController.class);
        controller.run();

    }
}
