package com.sg.dvdlibrary.dao;

public class DVDLibraryDaoException extends Exception{

    // Exceptions to be used throughout the program
    //The error class for DVD application. Extends Exception
    public DVDLibraryDaoException(String message){
        super(message);
    }

    public DVDLibraryDaoException(String message, Throwable cause){
        super(message, cause);
    }
}
