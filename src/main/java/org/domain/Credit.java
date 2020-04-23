package org.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


 //A class used to store information on the people (actors, directors, and producers)
public class Credit implements Comparable<Credit> {

    //The full name of the person.
    private String fullName;

    //The disambiguation number (multiple people of same number) of a person.
    private String disambigNumber;

    //The Media objects associated with the person with ACTING credits
    private ArrayList<Media> actingCredits;

    //The Media objects associated with the person with PRODUCING credits
    private ArrayList<Media> producingCredits;

    //The Media objects associated with the person with DIRECTING credits
    private ArrayList<Media> directingCredits;

    //A default constructor for MediaMaker that sets all the fields to empty
    public Credit() {
        fullName = "";
        disambigNumber = "";
        actingCredits = new ArrayList<Media>();
        producingCredits = new ArrayList<Media>();
        directingCredits = new ArrayList<Media>();
    }

    //Credit constructor to be used when user adds/edits a Credit through GUI interaction
    public Credit(String firstName, String lastName, String disambigNum, ArrayList<Media> acting
            , ArrayList<Media> directing, ArrayList<Media> producing) {

        // Checks if media maker has a disambiguation number for formatting full name
        if (disambigNum.equals("")) {
            fullName = firstName + " " + lastName;
        } else {
            fullName = firstName + " " + lastName + "(" + disambigNum + ")";
        }

        disambigNumber = disambigNum;
        actingCredits = acting;
        producingCredits = producing;
        directingCredits = directing;
    }

    //A constructor for Credit, filling its fields with information from a parsed read line.
    public Credit(String readLine, int type, BufferedReader br) throws IOException {

        this.fullName = "";
        this.disambigNumber = "";
        this.actingCredits = new ArrayList<Media>();
        this.producingCredits = new ArrayList<Media>();
        this.directingCredits = new ArrayList<Media>();

        //Separate the components of the read-in line into elements of a String array.
        String[] componentsArray = readLine.split("\\t| ");

        //Cut white space left over from the previous split method.
        for (int i = 0; i <= componentsArray.length - 1; i++) {
            componentsArray[i].trim();
        }

        //Delete empty strings by resorting the string array into a string ArrayList.
        ArrayList<String> components = new ArrayList<String>();
        for (int i = 0; i <= componentsArray.length - 1; i++) {
            if (!(componentsArray[i].isEmpty()))
                components.add(componentsArray[i]);
        }

        if (Pattern.compile("([A-Za-z]+),\\s+([A-Za-z]+)").matcher(readLine).find()) { //if there's a name, fill in name data
            //Store name data
            this.fullName = components.get(1) + " " + components.get(0).replaceAll(",", "");

            //Determine if there is a disambiguation number
            String disambigNumber = "";
            for (int i = 0; i < 3; i++) {
                if (components.get(i).matches("\\(\\D{1,4}\\)")) {
                    disambigNumber = components.get(i);
                    fullName += " " + disambigNumber;
                }
            }
        }

        Media media = parseLine(readLine, components);
        if (type == 3) {
            actingCredits.add(media);
        } else if (type == 4) {
            directingCredits.add(media);
        } else if (type == 5) {
            producingCredits.add(media);
        }

        String nextLine;
        while ((nextLine = br.readLine()) != null) { //File is read line by line until the end of the file

            if (!nextLine.isEmpty()) //if the nextLine isn't a new person (no new line)
            {//Use modified parse methods to store data into an AL.

                //Separate the components of the NEW line into elements of a String array.
                componentsArray = nextLine.split("\\t| ");

                //Cut white space left over from the previous split method.
                for (int i = 0; i <= componentsArray.length - 1; i++) {
                    componentsArray[i].trim();
                }

                //Delete empty strings by resorting the string array into a string ArrayList.
                components = new ArrayList<String>();
                for (int i = 0; i <= componentsArray.length - 1; i++) {
                    if (!(componentsArray[i].isEmpty()))
                        components.add(componentsArray[i]);
                }

                media = parseLine(nextLine, components); //change this to a parseMovie object

                if (type == 3) {
                    actingCredits.add(media);
                } else if (type == 4) {
                    directingCredits.add(media);
                } else if (type == 5) {
                    producingCredits.add(media);
                }
            } else {//next line had a name, construct a new MediaMaker object
                break;
            }
        }
    }

    //Determines the type of a line, parses it, and sorts the data into a Media object
    public static Media parseLine(String readLine, ArrayList<String> components) {

        if (!readLine.contains("\"") && !(Pattern.compile("\\(#\\d+\\.\\d+\\)").matcher(readLine).find()
                || Pattern.compile("\\d{4}-\\d{2}-\\d{2}").matcher(readLine).find())) //if the line is a movie type
        {
            Movie movie = new Movie();
            for (String s : components) {
                if (s.matches("\\([\\w]{1,2}\\)")) {
                    StringBuilder sb = new StringBuilder(s);
                    sb.deleteCharAt(0);
                    sb.deleteCharAt(sb.length() - 1);
                    s = sb.toString();
                    movie.setVenue(s);
                    break;
                }

            }
            for (String s : components) {
                if (s.matches("\\(.{4,}\\)")) {
                    movie.setYearAndNumeral(s);

                    //trim unnecessary characters
                    Pattern pattern = Pattern.compile("//d{4}");
                    Matcher matcher = pattern.matcher(s);
                    if (matcher.find()) s = matcher.group(0);
                    movie.setReleaseYear(s);
                    break;
                }
            }

            // Parse for MovieTitle in the line
            String title = "";

            // If the line has a name
            if (Pattern.compile("([A-Za-z]+),\\s*([A-Za-z]+)\\s").matcher(readLine).find()) {
                boolean containsDisambig = false;
                for (int i = 0; i < 3; i++) {
                    // If line has a disambiguation number
                    if (components.get(i).matches("\\([\\w]{1,2}\\)")) {
                        containsDisambig = true;
                    }
                }
                if (containsDisambig) //if line has a disambiguation number
                {
                    int yearPos = 0;
                    for (int a = 0; a < components.size(); a++) {
                        if (components.get(a).matches("\\(.{4,7}\\)"))
                            yearPos = a;
                    }
                    for (int i = 3; i < yearPos; i++) {
                        title += components.get(i) + " ";
                    }

                    movie.setTitle(title);
                } else {//line doesn't contain disambig number
                    int yearPos = 0;
                    for (int a = 0; a < components.size(); a++) {
                        if (components.get(a).matches("\\(.{4,}\\)"))
                            yearPos = a;
                    }
                    for (int i = 2; i < yearPos; i++) {
                        title += components.get(i) + " ";
                    }

                    movie.setTitle(title);
                }

            } else { // The line does not have a name

                int yearPos = 0;
                for (int a = 0; a < components.size(); a++) {
                    if (components.get(a).matches("\\(.{4,7}\\)"))
                        yearPos = a;
                }
                for (int i = 0; i < yearPos; i++) { // One word titles

                    title += components.get(i) + " ";
                }

                movie.setTitle(title);
            }

            return movie;
        } else  //the line contains data for an Episode
        {
            Episode episode = new Episode();

            String epNumber = "";
            //Parse for episode number
            for (String s : components) {
                if (Pattern.compile("\\(#\\d+\\.\\d+\\)").matcher(s).find() ||
                        Pattern.compile("\\d{4}-\\d{2}-\\d{2}").matcher(s).find()) {
                    //trim unnecessary characters
                    Pattern pattern = Pattern.compile("\\(#\\d+\\.\\d+\\)");
                    Matcher matcher = pattern.matcher(s);
                    if (matcher.find()) s = matcher.group(0);
                    else {
                        pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
                        matcher = pattern.matcher(s);
                        if (matcher.find()) s = matcher.group(0);
                    }

                    epNumber = s;
                    episode.setNumber(s);
                    break;
                }
            }

            //Parse for episode title
            int titleStart = readLine.indexOf("{");
            int titleEnd = readLine.indexOf("}");
            StringBuilder titleBuilder = new StringBuilder();
            for (int i = titleStart + 1; i < titleEnd; i++) {
                if (readLine.charAt(i) != '(') {
                    titleBuilder.append(readLine.charAt(i));
                } else {
                    break;
                }
            }
            String epTitle = "no title given";
            if (!titleBuilder.toString().isEmpty()) {
                epTitle = titleBuilder.toString();
                episode.setTitle(epTitle);
            } else {
                epTitle = epNumber;
                episode.setTitle(epNumber);
            }

            //set Year of the series
            String releaseYear = "error";
            for (String s : components) {
                if (s.matches("\\(.{4,}\\)")) {
                    releaseYear = s;
                    episode.setYear(s);
                    break;
                }
            }

            //Parse for SeriesTitle in the line
            String title = "no series title";
            Pattern typeTitle = Pattern.compile("\"(.*?)\"");
            Matcher titleMatch = typeTitle.matcher(readLine);
            if (titleMatch.find()) title = titleMatch.group(0);
            episode.setSeriesTitle(title);

            return episode;
        }
    }

    //A method that returns acting credits attributed to a person (Credit).
    public ArrayList<Media> getActingCredits() {
        return this.actingCredits;
    }

    //A method that returns producing credits attributed to a person (Credit).
    public ArrayList<Media> getProducingCredits() {
        return this.producingCredits;
    }

    //A method that returns directing credits attributed to a person (Credit).
    public ArrayList<Media> getDirectingCredits() {
        return this.directingCredits;
    }

    //A method that returns the name of the person (Credit).
    public String getName() {
        return fullName;
    } // end getName

    //A method that returns the first name of the person
    public String getFirstName() {
        String[] parts = fullName.split("[^\\s]+");
        return parts[0];
    }

    //A method that returns the last name of the person
    public String getLastName() {
        String[] parts = fullName.split("[^\\s]+");
        return parts[1];
    }

    public String getDisambigNum() {
        return disambigNumber;
    }


    //Returns a string representing Credits and all of its data.
    @Override
    public String toString() {
        String maker = ""; // String to store the media maker

        // Checks if maker's dismabig number (if it has one) is included within the name or not
        if (this.disambigNumber.equals("")) {
            maker = fullName;
        } else {
            maker = fullName + "(" + disambigNumber + ")";
        }

        return maker;
    }

    //Compares the name of Credit to another to determine order.
    @Override
    public int compareTo(Credit o) {
        int personNameDiff = this.fullName.compareTo(o.getName());

        return personNameDiff;
    }
}
