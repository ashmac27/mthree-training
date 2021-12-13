package com.sg.dvdlibrary;



import com.sg.dvdlibrary.controller.DVDLibraryController;
import com.sg.dvdlibrary.dao.DVDLibraryDao;
import com.sg.dvdlibrary.dao.DVDLibraryDaoException;
import com.sg.dvdlibrary.dao.DVDLibraryImpl;
import com.sg.dvdlibrary.ui.DVDLibraryView;
import com.sg.dvdlibrary.ui.UserIO;
import com.sg.dvdlibrary.ui.UserIOConsoleImpl;

public class App {

    //Main program that runs the run function in the controller

    public static void main(String[] args) throws DVDLibraryDaoException{
        UserIO userIo = new UserIOConsoleImpl();

        DVDLibraryView myView = new DVDLibraryView(userIo);
        DVDLibraryDao myDao = new DVDLibraryImpl();

        DVDLibraryController controller = new DVDLibraryController(myDao,myView);
        controller.run();

    }
}
