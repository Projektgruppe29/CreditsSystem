package org.domain;

//An abstract class that is the parent class for movies, series

public abstract class Media implements Comparable<Media> {

    //Stores the title of a Media object as a string
    protected String title;

    //Stores release year of a Media object as a string
    protected String releaseYear;

    public abstract String getTitle(); // Requires subclasses to contain an accessor for title
    public abstract String getReleaseYear(); // Requires subclasses to contain an accessor for year
}
