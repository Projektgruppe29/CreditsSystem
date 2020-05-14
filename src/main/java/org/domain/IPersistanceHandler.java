package org.domain;

import org.data.Credits;

import java.util.List;

public interface IPersistanceHandler {
    public List<Movie> getMovies();
    public List<Credits> getCredits();
    public Credits getCredits(int id);
    public boolean createCredits(Credits credits);
    public boolean createMovies(Movie movie);

}
