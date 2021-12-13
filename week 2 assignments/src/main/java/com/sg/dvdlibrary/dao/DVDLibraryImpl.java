package com.sg.dvdlibrary.dao;

import com.sg.dvdlibrary.dto.DVD;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

//Text-file specific implementation of the DVDLibraryDao Interface

public class DVDLibraryImpl implements DVDLibraryDao{

    //Declaring and initializing variables used throughout
    private Map<String, DVD> library = new HashMap<>();
    public static final String DVD_FILE = "dvds.txt";
    public static final String DELIMITER = "::";
    public static final String DELIMITER_CAST = "<<";

    //insert mapping into hashmap
    @Override
    public DVD addDvd(DVD d) throws DVDLibraryDaoException {
        library.put(d.getTitle(), d);
        return d;
    }

    // returning all mappings in hashmap
    @Override
    public List<DVD> getAllDvds() throws DVDLibraryDaoException {
        return new ArrayList(library.values());
    }

    //get specific DVD from hashmap
    @Override
    public DVD getDvd(String title) throws DVDLibraryDaoException {
        return library.get(title);
    }

    // Remove DVD mapping from hashmap
    @Override
    public DVD removeDvd(String title) throws DVDLibraryDaoException {
        loadDvds();
        DVD removedDvd = library.remove(title);
        writeDvds();
        return removedDvd;
    }

    // writing DVD info into the text file
    @Override
    public void writeDvds() throws DVDLibraryDaoException{
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(DVD_FILE));
        } catch (IOException e) {
            throw new DVDLibraryDaoException(
                    "Could not save dvd data.", e);
        }
        List<DVD> dvdList = getAllDvds();
        for (DVD dvd : dvdList) {
            out.println(marshallDvd(dvd));
            out.flush();
        }
    }

    // Reading DVD from memory
    @Override
    public void loadDvds() throws DVDLibraryDaoException{

        Scanner scanner;
        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(DVD_FILE)));
        } catch (FileNotFoundException e) {
            throw new DVDLibraryDaoException(
                    "Could not load dvd data into memory.", e);
        }
        while(scanner.hasNextLine()){
            String current = scanner.nextLine();
            DVD currentDvd = unMarshallDvd(current);
            library.put(currentDvd.getTitle(),currentDvd);
        }
        scanner.close();

    }

    // Translating data from object in memory into textfile
    public String marshallDvd(DVD d) {
        return d.getTitle() + DELIMITER + d.getReleaseDate() + DELIMITER + d.getMpaaRating() + DELIMITER + d.getDirector() + DELIMITER + d.getStudio() + DELIMITER + d.getRating();
    }

    public void unMarhsallDvds() throws DVDLibraryDaoException {
        Scanner scanner;
        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(DVD_FILE)));
        } catch (FileNotFoundException e) {
            throw new DVDLibraryDaoException(
                    "Could not load dvd data into memory.", e);
        }
        String currentLine;
        DVD currentDvd;

        while (scanner.hasNextLine()) {

            currentLine = scanner.nextLine();

            currentDvd = unMarshallDvd(currentLine);

            library.put(currentDvd.getTitle(),currentDvd);
        }
        // close scanner
        scanner.close();
    }

    private DVD unMarshallDvd(String dvdAsText) {
        String[] values = dvdAsText.split(DELIMITER);
        List<String> cast = new ArrayList<String>();

        DVD newDvd = new DVD(values[0], values[1], values[2], values[3], values[4], values[5]);
        return newDvd;

    }

    @Override
    public void modifyDvd(String oldTitle, DVD d) throws DVDLibraryDaoException {

        if (oldTitle != null) {
            library.remove(oldTitle);
            library.put(d.getTitle(), d);
        }
    }


}
