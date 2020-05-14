package org.data;

import java.util.ArrayList;

public class Production {
    String name;
    int productionID;
    int releaseYear;
    String productionCountry;
    int episodeCount;
    String genre;
    private static ArrayList<Production> list = new ArrayList<>();

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

    public static ArrayList<Production> getList() {
        return list;
    }


       // Production obj = new Production();
}

