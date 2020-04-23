package org.domain;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Episode extends Media implements Comparable<Media> {


    //Stores the title of an episode as a string
    private String title;

    //Stores the title of an episode's series as a string
    private String seriesTitle;

    // Stores the release year of an episode as a string
    private String releaseYear;

    //Stores the season and episode number of an episode as a string
    private String seasonAndEpisodeNum;


    //default constructor for an Episode object that sets all fields to empty
    public Episode() {
        title = "";
        seriesTitle = "";
        releaseYear = "";
        seasonAndEpisodeNum = "";
    }


    //Episode constructor to be used when user adds/edits an episode through GUI interaction.
    public Episode(String title, String seasonNum, String episodeNum, String year) {
        this.title = title;
        releaseYear = year;
        seasonAndEpisodeNum = "(#" + seasonNum + "." + episodeNum + ")";
    }


     //A constructor to create a Episode object, filling its fields from a parsed String line read from a file.
    public Episode (String inputLine) {

        //Create string array to hold the split-by-whitespace inputLine
        String[] parsedLine = inputLine.split("\t\t\t|\t\t|\t| ");

        //Trim the whitespace from each of the index components, deals with the tab characters
        for (int i = parsedLine.length -1; i >= 0; i--) {
            parsedLine[i].trim();
        }

        //Converts String[] to AL<String> to remove the empty strings from trimming
        ArrayList<String> cleanParsedLine = new ArrayList<String>();
        for(int j = 0; j <= parsedLine.length-1; j++){
            if(parsedLine[j].isEmpty() != true) {
                cleanParsedLine.add(parsedLine[j]);
            }
        }


        //Finds the end of the series information using regular expressions
        Pattern serStYr = Pattern.compile("\\([0-9]{4}\\)");
        int seriesTitleLength = 0;
        for (int i = 0; i < cleanParsedLine.size(); i++)
        {
            String current = cleanParsedLine.get(i);
            Matcher mSSY = serStYr.matcher(current);
            if (mSSY.find() == true)
            {
                seriesTitleLength = i;
            }

        }

        //Set last component to the airYear, Remove last comp
        this.releaseYear = cleanParsedLine.get((cleanParsedLine.size()-1));
        cleanParsedLine.remove(cleanParsedLine.size()-1);

        //Comp currently being checked
        String currentComp = cleanParsedLine.get(cleanParsedLine.size() -1);

        //Regex to find Season & Num
        Pattern seaNumParB = Pattern.compile("(\\#[0-9]+[.][0-9]+\\)\\})");

        //String for Title
        String titleBuilder = "";

        //See if next comp is Season & Num
        Matcher sNPB = seaNumParB.matcher(currentComp);
        boolean foundSeaNum = sNPB.find();

        if(foundSeaNum == true) {
            this.seasonAndEpisodeNum = currentComp;
            seasonAndEpisodeNum = seasonAndEpisodeNum.substring(0, (seasonAndEpisodeNum.length()-1));
        }
        else if (currentComp == "{{SUSPENDED}}") {
            this.seasonAndEpisodeNum = currentComp.toString();
        }
        else {
            this.seasonAndEpisodeNum = "No Season or Episode Number Information Available";


            //Sets Title here because it wouldn't work later
            for (int start = seriesTitleLength+1; start <= cleanParsedLine.size()-1; start++)
            {
                titleBuilder = titleBuilder.concat(cleanParsedLine.get(start)).concat(" ");
            }
            title = titleBuilder;
        }

        //Checks if there is an episode title by checking first index of {, Sets title field when ready
        if (cleanParsedLine.get(seriesTitleLength +1) == seasonAndEpisodeNum)
        {
            this.title = "No Episode Title Available";
        }

        else if(this.title == null){

            for (int start = seriesTitleLength+1; start <= cleanParsedLine.size()-2; start++)
            {
                titleBuilder = titleBuilder.concat(cleanParsedLine.get(start)).concat(" ");
            }
        }
        this.title = titleBuilder;
        title = title.replaceAll("\\{|\\}", "");

        //Sets the seriesTitle field
        String builder = "";
        for (int p = 0; p < seriesTitleLength; p++)
        {
            builder = builder.concat(cleanParsedLine.get(p)).concat(" ");
        }

        this.seriesTitle = builder;
    }


    //Accessor for the variable title of an episode.
    @Override
    public String getTitle() {
        return title;
    }


     //A mutator for the episode title
    public void setTitle(String s) {
        this.title = s;
    }


     //An accessor for the variable seriesTitle of an episode.
    public String getSeriesTitle() {
        return seriesTitle;
    }


     //A mutator for the variable seriesTitle of an episode.
    public void setSeriesTitle(String title2) {
        this.seriesTitle = title2;
    }

    //An accessor for the variable releaseYear.
    @Override
    public String getReleaseYear() {
        return releaseYear;
    }

    //A mutator for the year of the episode
    public void setYear(String s) {
        this.releaseYear = s;
    }

    //An accessor that returns the season and episode numbers of an episode.
    public String getSeasonAndEpisodeNum() {
        return seasonAndEpisodeNum;
    }

     //An accessor that returns just the season number of the episode
    public String getSeasonNumberOnly() {
        String SeasAndNum = this.seasonAndEpisodeNum;
        String SeasNum = SeasAndNum.substring(2 ,SeasAndNum.indexOf('.'));
        return SeasNum;
    }


     //An accessor that returns just the episode number of the episode
    public String getEpisodeNumberOnly() {
        String SeasAndNum = this.seasonAndEpisodeNum;
        String EpNum = SeasAndNum.substring(SeasAndNum.indexOf(".")+1, SeasAndNum.length()-1);
        return EpNum;
    }


     //A mutator for the episode number
    public void setNumber(String s) {
        this.seasonAndEpisodeNum = s;
    }


     //Returns a string representing an episode and all of its data.
    public String toString(String title){
        String episode = ""; // episode as a string representation

        // If episode has a title, episode and season number are omitted
        if (!title.equals("No Episode Title Available")) {
            episode = "EPISODE: " + seriesTitle + ": " + title + " (" + releaseYear + ")";
        }

        // If episode has no title, episode and season numbers are printed
        else {
            episode = "EPISODE: " + seriesTitle + ": " + seasonAndEpisodeNum + " (" + releaseYear + ")";
        }
        return episode;
    }


     //Compares an Episode to another to determine order.
    @Override
    public int compareTo(Media otherEpisode) {

        //Compare based on title
        if(otherEpisode.getClass().getName().toString() == "Series" | otherEpisode.getClass().getName().toString() == "Movie"){
            // Comparing episodes' series titles
            int seriesTitleDiff = this.seriesTitle.compareTo(otherEpisode.getTitle());
            if (seriesTitleDiff != 0) {
                return seriesTitleDiff;
            }
        }

        if(otherEpisode.getClass().getName().toString() == "Episode"){
            Episode other = (Episode) otherEpisode;
            // Comparing episodes' series titles
            int seriesTitleDiff = this.seriesTitle.compareTo(other.getSeriesTitle());
            if (seriesTitleDiff != 0) {
                return seriesTitleDiff;
            }
        }

        // Comparing episode titles
        int titleDiff = this.title.compareTo(otherEpisode.getTitle());

        if (titleDiff != 0) {
            return titleDiff;
        }

        // Comparing release years
        int episodeYearDiff = this.releaseYear.compareTo(otherEpisode.getReleaseYear());

        if (episodeYearDiff != 0) {
            return episodeYearDiff;
        }

        // Comparing season and episode numbers
        if(otherEpisode.getClass().getName().toString() == "Episode"){
            Episode other = (Episode) otherEpisode;
            int seasonAndEpDiff = this.seasonAndEpisodeNum.compareTo(other.getSeasonAndEpisodeNum());

            if (seasonAndEpDiff != 0) {
                return seasonAndEpDiff;
            }
        }
        return 0; // Returns zero if all fields are the same because the episodes are the same
    }
}
