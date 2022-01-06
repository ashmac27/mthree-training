package com.sg.flooringmastery.controller;

import com.sg.flooringmastery.dao.FlooringMasteryDao;
import com.sg.flooringmastery.service.FlooringMasteryServiceImpl;
import com.sg.flooringmastery.service.InvalidOrderEntryException;
import com.sg.flooringmastery.ui.FlooringMasteryView;
import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.model.Order;
import com.sg.flooringmastery.model.Product;
import com.sg.flooringmastery.model.Tax;
import java.math.BigDecimal;

/*
The orchestrator of the application.
Knows what to be done and when to be done
*/

public class FlooringMasteryController {
    private FlooringMasteryView view;
    private FlooringMasteryServiceImpl service;
    private String orderFile = "./SampleFileData/Orders/Orders_11302021.txt";
    private String productsFile = "./SampleFileData/Data/Products.txt";
    private String taxesFile = "./SampleFileData/Data/Taxes.txt";


    public FlooringMasteryController(){

    }

    public FlooringMasteryController(FlooringMasteryView view, FlooringMasteryServiceImpl service) {
        this.view = view;
        this.service = service;
    }

    public void run() {
        try {
            service.getLoadAllFiles(orderFile, productsFile, taxesFile);
        } catch (FlooringMasteryPersistenceException FMPE) {
            view.displayErrorMessage(FMPE.getMessage());
        }

        boolean keepGoing = true;
        int menuSelection;


         while(keepGoing) {
            menuSelection = view.displayMenuGetSelection();
            switch (menuSelection) {
                case 1: //Display Orders
                    DisplayOrders();
                    break;
                case 2:
                    AddOrder();
                    break;
                case 3:
                    EditOrder();
                    break;
                case 4:
                    RemoveOrder();
                    break;
                case 5:
                    ExportAllData();
                    break;
                case 6:
                    keepGoing = false;
                    break;
                default:
                    unknownCommand();
                    break;
            }
        }

        exitMessage();

    }

    private void DisplayOrders() {
        String dateAsString = view.askForDate();
        try {
            service.validateDate(dateAsString);
            try {
                view.displayOrders(service.getAllOrdersDate(dateAsString));
            } catch (InvalidOrderEntryException IOE) {
                view.displayErrorMessage(IOE.getMessage());
            } catch (FlooringMasteryPersistenceException e) {
                view.displayErrorMessage(e.getMessage());
            }
        } catch (InvalidOrderEntryException IOE) {
            view.displayErrorMessage(IOE.getMessage());
        }

    }

    private void AddOrder() {
        Order order = new Order();
        boolean isValid = true;
        do {
            try {
                order.setOrderDate(service.validateDate(view.askForDate()));
                service.isFutureDate(order.getOrderDate());
                order.setCustomerName(service.validateCustomerName(view.getCustomerName()));
                order.setState(view.getState());
                order.setProductType(view.getProductType());
                order.setArea(new BigDecimal(view.getArea()));
                service.validateOrder(order);
                service.addOrder(order);
                isValid = true;

            } catch (InvalidOrderEntryException IOE) {
                view.displayErrorMessage(IOE.getMessage());
                isValid = false;
            } catch (FlooringMasteryPersistenceException e) {

                view.displayErrorMessage(e.getMessage());
            }
        } while (!isValid);
    }

    private void EditOrder() {
        Order order;
        try {
            order = service.getOrder(view.askForOrderNumber(), service.validateDate(view.askForDate()));
            try {
                //System.out.println("Hello");
                order.setCustomerName(view.getCustomerName());
                order.setState(view.getState());
                order.setProductType(view.getProductType());
                order.setArea(new BigDecimal(view.getArea()));
                service.editOrder(order);
            } catch (InvalidOrderEntryException | FlooringMasteryPersistenceException IOE) {
                view.displayErrorMessage(IOE.getMessage());
            }
        } catch (InvalidOrderEntryException IOE) {
            view.displayErrorMessage(IOE.getMessage());
        }

    }

    private void RemoveOrder() {
        Order order;
        try {
            order = service.getOrder(view.askForOrderNumber(), service.validateDate(view.askForDate()));

            service.removeOrder(order.getOrderNumber());

        } catch (InvalidOrderEntryException IOE) {
            view.displayErrorMessage(IOE.getMessage());
        }
    }

    private void ExportAllData() {
        try {
            service.writeOrdersToFile(view.askForFileDate());
        } catch (FlooringMasteryPersistenceException | InvalidOrderEntryException e) {
            view.displayErrorMessage(e.getMessage());
        }
        view.exportDataSuccessBanner();
    }

    private void exportAllDataDefault() {
        try {
            service.writeOrdersToFile();
        } catch (FlooringMasteryPersistenceException FMPE) {
            view.displayErrorMessage(FMPE.getMessage());
        }
        view.exportDataSuccessBanner();
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }
}
