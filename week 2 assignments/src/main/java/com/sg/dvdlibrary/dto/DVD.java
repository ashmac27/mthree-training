package com.sg.dvdlibrary.dto;

// DTO to hold all DVD information
// This file simply has getters and setters
//Ferries information between layers

public class DVD {
    
    private String title;
    private String releaseDate;
    private String mpaaRating;
    private String director;
    private String studio;
    private String rating;

    public DVD(String title, String releaseDate, String mpaaRating, String director, String studio, String rating){
        this.title = title;
        this.releaseDate = releaseDate;
        this.mpaaRating = mpaaRating;
        this.director = director;
        this.studio = studio;
        this.rating = rating;
    }

    /*
    Getter and setter methods for
    all the variables
     */

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getReleaseDate(){
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate){
        this.releaseDate = releaseDate;
    }

    public String getMpaaRating() {
        return mpaaRating;
    }

    public void setMpaaRating(String mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
