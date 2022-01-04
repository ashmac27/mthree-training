package com.sg.vendingmachine.controller;

import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Change;
import com.sg.vendingmachine.dto.Item;
import com.sg.vendingmachine.service.InsufficientFundsException;
import com.sg.vendingmachine.service.NoItemInventoryException;
import com.sg.vendingmachine.service.VendingMachineServiceLayer;
import com.sg.vendingmachine.service.VendingMachineServiceLayerImpl;
import com.sg.vendingmachine.ui.UserIO;
import com.sg.vendingmachine.ui.UserIOConsoleImpl;
import com.sg.vendingmachine.ui.VendingMachineView;
import java.math.BigDecimal;
import static java.math.RoundingMode.HALF_UP;

import java.util.List;

/*
The orchestrator of the application.
Knows what to be done and when to be done
*/

public class VendingMachineController {

    private VendingMachineView view;
    private VendingMachineServiceLayer service;

    //Mmmm Let's revisit this soon.

    public VendingMachineController(VendingMachineServiceLayer service, VendingMachineView view) {
        this.service = service;
        this.view = view;
    }


    public void run() throws VendingMachinePersistenceException {
        boolean keepGoing = true;
        service.loadMachine();

        int menuSelection = -1;

        //Switch case menu to decide what is to be done with
        try {
            while (keepGoing) {
                menuSelection = displayItemGetSelection();

                switch (menuSelection){
                    case 1:
                        enterMoneyAndSelection();
                        service.writeMachine();
                        break;
                    case 2:
                        exitMessage();
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }
            }
            ///*try-catch: catch DVDLibraryDaoException
            //            and display error message when caught
            //            */
        } catch (VendingMachinePersistenceException | InsufficientFundsException | NoItemInventoryException e) {
            keepGoing = false;
            view.displayErrorMessage(e.getMessage());
        }
    }

    /*
    Appropriate methods to displayItem and to get selection, to enter money and select choice,
    unknown method, exit message
     */
    private int displayItemGetSelection() throws VendingMachinePersistenceException {
        List<Item> items = service.getAllItems();
        return view.displayItemsGetSelection(items);
    }

    private void enterMoneyAndSelection() throws VendingMachinePersistenceException,InsufficientFundsException, NoItemInventoryException{
        BigDecimal credit = new BigDecimal(view.getMoneyEntered()).setScale(2, HALF_UP);

        List<Item> items = service.getAllItems();
        String itemChoice = view.getItemChoice(items);
        try {

            Change change = service.purchaseItem(itemChoice, credit);
            view.displayPurchaseSuccess(change);
        } catch (VendingMachinePersistenceException | InsufficientFundsException | NoItemInventoryException e) {

            view.displayErrorMessage(e.getMessage());
        }
    }

    private void unknownCommand(){
        view.displayUnknownCommandBanner();
    }

    private void exitMessage(){
        view.displayExitBanner();
    }

}
