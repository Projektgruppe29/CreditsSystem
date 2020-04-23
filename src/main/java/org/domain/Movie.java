package org.domain;

public class Movie extends Media implements Comparable<Media>{

    // Stores the title of a single movie as a string
    private String title;

    //Stores the release year and/or a Roman numeral that indicates a multiple release of a movie with the same name.

    private String yearAndNumeral;

    //Stores the type of movie if it went straight to video, TV, or theater as a string
    private String venue;

    // Stores the year of release for a movie as a string
    private String releaseYear;

    //A default constructor for a Movie object that sets all fields to
    public Movie() {
        title = "";
        yearAndNumeral = "";
        venue = "";
        releaseYear = "";
    }


    //Movie constructor to be used when user adds/edits a movie through GUI interaction.
    public Movie(String title, String year, String disambigNum, String venue) {
        this.title = title;
        releaseYear = year;
        yearAndNumeral = disambigNum;
        this.venue = venue;
    } //


    //A constructor for a Movie object that sets the title, release year, year (with potential Roman Numeral), and venue.
    public Movie(String line) throws StringIndexOutOfBoundsException{
        String year = line.substring(line.length() - 4); // Takes last 4 chars to assign to year
        // Checks if year is unknown, suspended, or neither to set release year field
        if (year.equals("????")) {
            releaseYear = "UNSPECIFIED";
        }
        else if (year.equals("ED}}")) {
            StringBuilder ifSuspended = new StringBuilder(line);
            int checkWhiteSpace = ifSuspended.lastIndexOf("{") - 2; // Index of char 2 indexes before the last {

            // Checks for and deletes any white space (except for one) between the year and the {{SUSPENDED}}
            if (ifSuspended.charAt(checkWhiteSpace) == (' ')) {
                boolean isWhiteSpace = true;
                while (isWhiteSpace) {
                    if (ifSuspended.charAt(checkWhiteSpace - 1) == (' ') ) {
                        ifSuspended.deleteCharAt(checkWhiteSpace - 1);
                    }
                    else {
                        isWhiteSpace = false;
                    }
                }
            }

            // Sets release year to the year including the {{SUSPENDED}} after it
            releaseYear = ifSuspended.substring(ifSuspended.lastIndexOf("{") - 6, ifSuspended.length());
        }
        else {
            releaseYear = year;
        }

        // Checks if line contains a venue
        String contained = line.substring(line.lastIndexOf('('), line.lastIndexOf(')') + 1);
        StringBuilder venue = new StringBuilder(contained); // Converts contained to a SB for modifications

        // If contained is a video venue (V)
        if (venue.length() == 3) {
            this.venue = "Video";
        }
        // If contained is a TV venue (TV)
        else if (venue.length() == 4) {
            this.venue = "TV";
        }
        // If contained is a year (with or without Numeral)
        else {
            // Checks if year (with or without Numeral) is unknown
            if (venue.toString().equals("(????)")) {
                yearAndNumeral = "(UNSPECIFIED)";
            }
            else {
                yearAndNumeral = venue.toString();
            }
            this.venue = "N/A"; // No venue because it would have came after the year
        }

        StringBuilder temp = new StringBuilder(line); // Converts line to SB for modifications
        boolean containsWhiteSpace = true; // Checker for white spaces

        // If line contains only title and year, no venue
        if(this.venue.equals("N/A")) {
            temp = temp.delete(temp.lastIndexOf("("), temp.length()); // Deletes year so you're left with title
            temp = temp.reverse(); // Reverses title to rid of any extra white spaces left
            while (containsWhiteSpace) {
                if (Character.isWhitespace(temp.charAt(0))) {
                    temp.deleteCharAt(0);
                }
                else {
                    containsWhiteSpace = false; // Line contains no more extra white spaces after title
                }
            }
            temp.reverse(); // Reverses title back correctly
            title = temp.toString(); // Assigns title to the Movie
        }

        // If line contains title, year, and a venue
        else {
            temp = temp.delete(temp.lastIndexOf("("), temp.length()); // Deletes venue to find year

            // Finds occurrence of year (with potential numeral) and substrings it to the field
            String tempYear = temp.substring(temp.lastIndexOf("("), temp.lastIndexOf(")")+1);

            // Checks if year was unknown
            if (tempYear.equals("(????)")) {
                yearAndNumeral = "(UNSPECIFIED)";
            }
            else {
                yearAndNumeral = tempYear;
            }

            // Deletes year so you're left with title
            temp = temp.delete(temp.lastIndexOf("("), temp.lastIndexOf(")") + 1);
            temp = temp.reverse(); // Reverses title to rid of any extra white spaces left

            // Checks if there's white spaces left after title to remove them
            while (containsWhiteSpace) {
                if (Character.isWhitespace(temp.charAt(0))) {
                    temp = temp.deleteCharAt(0);
                }
                else {
                    containsWhiteSpace = false; // No more white spaces after title
                }
            }
            temp = temp.reverse(); // Reverses title back correctly
            title = temp.toString(); // Assigns title to Movie
        }
    }

    //An accessor for the variable title.
    @Override
    public String getTitle() {
        return title;
    }

    //A mutator for the variable title.
    public void setTitle(String title) {
        this.title = title;
    }

    //An accessor for the variable releaseYear.
    @Override
    public String getReleaseYear() {
        return releaseYear;
    }


    //A mutator for the variable releaseYear.
    public void setYear(String year) {
        this.releaseYear = year;
    }


    //An accessor for the variable yearAndNumeral.
    public String getYearWithNumeral() {
        return yearAndNumeral;
    }


    //A mutator for the variable yearAndNumeral.
    public void setYearAndNumeral(String yearAndNumeral) {
        this.yearAndNumeral = yearAndNumeral;
    }

    //An accessor for the variable venue.
    public String getVenue() {
        return venue;
    }


    //A mutator for the variable venue.
    public void setVenue(String venue) {
        this.venue= venue;
    }

    //Returns a string representing a movie and all of its data.
    public String toString(){
        String movie = "MOVIE (" + venue + "): " + title + " " +
                yearAndNumeral;
        return movie;
    }


    //Compares a movie to another to determine order.
    @Override
    public int compareTo(Media otherMovie) {
        // Comparing titles
        int movieTitleDiff = this.title.compareTo(otherMovie.getTitle());

        if (movieTitleDiff != 0) {
            return movieTitleDiff;
        }

        // Comparing release years
        int movieYearDiff = this.releaseYear.compareTo(otherMovie.getReleaseYear());

        if (movieYearDiff != 0) {
            return movieYearDiff;
        }

        Movie other = (Movie) otherMovie; // Type cast from Media to Movie to
        // compare other fields

        // Comparing venues
        int venueDiff = this.venue.compareTo(other.getVenue());

        if (venueDiff != 0) {
            return venueDiff;
        }

        // Comparing year and potential Roman Numerals
        int yearAndNumeralDiff = this.yearAndNumeral.compareTo(other.getYearWithNumeral());

        return yearAndNumeralDiff;// returns zero if all fields are the same because the movies are the same
    }


     //Mutator for the variable releaseYear.

    public void setReleaseYear(String s) {
        this.releaseYear = s;
    }
}
