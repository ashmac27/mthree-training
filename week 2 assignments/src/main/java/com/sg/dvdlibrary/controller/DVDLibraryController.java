package com.sg.dvdlibrary.controller;

import com.sg.dvdlibrary.dao.DVDLibraryDao;
import com.sg.dvdlibrary.dao.DVDLibraryDaoException;
import com.sg.dvdlibrary.dao.DVDLibraryImpl;
import com.sg.dvdlibrary.dto.DVD;

import com.sg.dvdlibrary.ui.DVDLibraryView;
import com.sg.dvdlibrary.ui.UserIO;
import com.sg.dvdlibrary.ui.UserIOConsoleImpl;
import java.util.List;

/*
The Orchestrator of the application.
Knows what to be done and when to be done
 */
public class DVDLibraryController {
    private DVDLibraryDao dao;
    private DVDLibraryView view;

    public DVDLibraryController(DVDLibraryDao dao, DVDLibraryView view) {
        this.dao = dao;
        this.view = view;
    }

    public void run() {
        boolean keepGoing = true;
        int menuSelection;

        try{
        while (keepGoing) {
            menuSelection = view.printMenuAndGetSelection();


            //swtich-case menu to decide what is to be done with
            // users selections
            switch (menuSelection) {
                case 1:
                    addDvd();
                    break;
                case 2:
                    deleteDvd();
                    break;
                case 3:
                    editDvd();
                    break;
                case 4:
                    listDvds();
                    break;
                case 5:
                    findDvd();
                    break;
                case 6:
                    dao.writeDvds();
                    System.exit(0);
                    break;
                default:
                    unknownCommand();
            }
        }
        exitMessage();

        /*try-catch: catch DVDLibraryDaoException
            and display error message when caught
            */
    } catch(DVDLibraryDaoException e){
        view.displayErrorMessage(e.getMessage());
    }
    }

    /*
    Appropriate methods to add, delete, list, etc DVDs


     */

    private void addDvd() throws DVDLibraryDaoException {
        view.displayCreateDvdBanner();
        DVD dvd = view.getNewDvdInfo();

        dao.addDvd(dvd);

        view.displayCreateSuccessBanner();
    }

    private void deleteDvd() throws DVDLibraryDaoException {
        view.displayRemoveDvdBanner();
        String title = view.displayGetDvdTitleChoice();

        DVD dvd = dao.removeDvd(title);

        view.displayRemoveResult(dvd);
    }

    private void editDvd() throws DVDLibraryDaoException {
        view.displayEditDvdBanner();
        String title = view.displayGetDvdTitleChoice();

        DVD oldDvd = dao.getDvd(title);
        DVD newDvd = (view.editDvd(oldDvd));
        if(newDvd!=null){
            dao.modifyDvd(title, newDvd);
        }

    }

    private void listDvds() throws DVDLibraryDaoException {
        view.displayListAllBanner();
        view.displayDvdList(dao.getAllDvds());
    }

    private void findDvd() throws DVDLibraryDaoException {
        view.displayFindDvdBanner();
        String title = view.displayGetDvdTitleChoice();

        DVD dvd = dao.getDvd(title);

        view.displayDvd(dvd);
    }

    private void unknownCommand(){
        view.displayUnknownCommandBanner();
    }

    private void exitMessage(){
        view.displayExitBanner();
    }

}
