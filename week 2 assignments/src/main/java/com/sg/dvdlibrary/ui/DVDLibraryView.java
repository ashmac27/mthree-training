package com.sg.dvdlibrary.ui;

import java.util.List;
import com.sg.dvdlibrary.dto.DVD;

// This will handle all the UI logic
// Therefore responsible for interacting with user completely
public class DVDLibraryView {
    private UserIO io;

    public DVDLibraryView(UserIO io){
        this.io = io;
    }

    public int printMenuAndGetSelection(){

        //Display Menu to user
        io.print("Main Menu");
        io.print("1. Add DVD");
        io.print("2. Remove DVD");
        io.print("3. Edit DVD");
        io.print("4. List DVDs");
        io.print("5. Find DVD");
        io.print("6. Exit");

        return io.readInt("Please select from the above choices.", 1, 6);
    }

    //Getting the information for a new DVD.
    public DVD getNewDvdInfo() {
        String title = io.readString("Please enter the name of the DVD");
        String releaseDate = io.readString("Please enter the release date.");
        String mpaa = io.readString("Please enter the MPAA Rating.");
        String director = io.readString("Please enter the Director's name.");
        String studio = io.readString("Please enter the studio name.");
        String rating = io.readString("Please enter your rating or additional note.");

        DVD currentDvd = new DVD(title, releaseDate, mpaa, director, studio, rating);
        return currentDvd;
    }

    //Banners that will displayed throughout the program
    public void displayCreateDvdBanner() {
        io.print("=== Add Dvd ===");
    }

    public void displayCreateSuccessBanner() {
        io.readString("Dvd added. Please hit enter to continue.");
    }

    public void displayRemoveDvdBanner(){
        io.print("=== Remove DVD ===");
    }

    public void displayEditDvdBanner(){
        io.print("=== Edit Dvd ===");
    }

    public void displayListAllBanner(){
        io.print("=== List All DVDs ===");
    }

    public void displayFindDvdBanner(){
        io.print("=== Find DVD ===");
    }

    public void displayExitBanner() {
        io.print("Goodbye!");
    }


    public void displayDvdList(List<DVD> dvdList){

        for(DVD d : dvdList){
            String dvdInfo = String.format("%s %n Release Date: %s %n MPAA Rating: %s %n Director: %s %n Studio: %s %n User Rating/Note: %s",
                    d.getTitle(), d.getReleaseDate(), d.getMpaaRating(), d.getDirector(), d.getStudio(), d.getRating());
            io.print(dvdInfo);

        }
        io.readString("Please hit enter to continue.");
    }

    public String displayGetDvdTitleChoice(){
        return io.readString("Please enter the DVD Title.");
    }


    //Edit function works similar to add function.
    // Applying info to an already existing object
    public DVD editDvd(DVD d){

        if(d!=null){
            d.setTitle(io.readString("Please enter the name of the DVD"));
            d.setReleaseDate(io.readString("Please enter the release date."));
            d.setMpaaRating(io.readString("Please enter the MPAA Rating."));
            d.setDirector(io.readString("Please enter the Director's name."));
            d.setStudio(io.readString("Please enter the studio name."));
            d.setRating(io.readString("Please enter your rating or additional note."));
            return d;
        }
        else{
            io.print("No such DVD found.");
            return null;
        }
    }

    //Method to display the DVD information
    public void displayDvd(DVD d){
        if(d!=null){
            io.print("DVD: "+d.getTitle());
            io.print("Released: "+d.getReleaseDate());
            io.print("MPAA Rating: "+d.getMpaaRating());
            io.print("Director: "+d.getDirector());
            io.print("Studio: "+d.getStudio());
            io.print("User Rating or Note: "+d.getRating());
            } else{
            io.print("No such DVD.");
        }
        io.readString("Press hit enter to continue.");
    }

    //Method if the user removes a dvd, nonexistent dvd
    public void displayRemoveResult(DVD d){
        if(d != null){
            io.print("DVD removed.");
        }
        else{
            io.print("No such DVD found.");
        }
        io.readString("Press enter to continue.");
    }

    // Methods for just incase there is a user error
    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!");
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }

}