package org.domain;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Production {
    String name;
    int productionID;
    int releaseYear;
    String productionCountry;
    int episodeCount;
    String genre;



    public Production() {
    }

    public Production(String name, int productionID, int releaseYear,
                      String productionCountry, int episodeCount, String genre) {
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
    public void SetValues(String name, int productionID, int releaseYear, String productionCountry, int episodeCount, String genre) {
        this.name = name;
        this.productionID = productionID;
        this.releaseYear = releaseYear;
        this.productionCountry = productionCountry;
        this.episodeCount = episodeCount;
        this.genre = genre;
    }

       // Production obj = new Production();
}

