package org.data;

import java.util.ArrayList;

public class Production {
    public int id;
    public String name;
    public int productionID;
    public int releaseYear;
    public String productionCountry;
    public int episodeCount;
    public String genre;

    public Production(int id, String name, String genre, int releaseYear) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.releaseYear = releaseYear;
    }

    public Production(int id, String name, int productionID, int releaseYear, String productionCountry, int episodeCount, String genre) {
        this.id = id;
        this.name = name;
        this.productionID = productionID;
        this.releaseYear = releaseYear;
        this.productionCountry = productionCountry;
        this.episodeCount = episodeCount;
        this.genre = genre;
    }

    public Production( String name, int productionID, int releaseYear, String productionCountry, int episodeCount, String genre) {
        this.name = name;
        this.productionID = productionID;
        this.releaseYear = releaseYear;
        this.productionCountry = productionCountry;
        this.episodeCount = episodeCount;
        this.genre = genre;
    }


    public String toString(){
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProductionID() {
        return productionID;
    }

    public void setProductionID(int productionID) {
        this.productionID = productionID;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getProductionCountry() {
        return productionCountry;
    }

    public void setProductionCountry(String productionCountry) {
        this.productionCountry = productionCountry;
    }

    public int getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(int episodeCount) {
        this.episodeCount = episodeCount;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

}

