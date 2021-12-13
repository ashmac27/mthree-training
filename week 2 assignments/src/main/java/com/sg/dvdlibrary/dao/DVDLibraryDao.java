package com.sg.dvdlibrary.dao;

import com.sg.dvdlibrary.dto.DVD;
import java.util.List;

/*
Interface that defines methods that must be implemented by any class that wants to play the role of DAO
 */
public interface DVDLibraryDao {
    DVD addDvd(DVD d) throws DVDLibraryDaoException;

    List<DVD> getAllDvds() throws DVDLibraryDaoException;

    DVD getDvd(String title) throws DVDLibraryDaoException;

    DVD removeDvd(String title) throws DVDLibraryDaoException;

    void modifyDvd(String oldTitle, DVD d) throws DVDLibraryDaoException;

    void loadDvds() throws DVDLibraryDaoException;

    void writeDvds() throws DVDLibraryDaoException;
}
