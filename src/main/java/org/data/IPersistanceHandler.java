package org.data;

import javafx.collections.ObservableList;

import java.util.List;

public interface IPersistanceHandler {
    public List<Credits> getCredits();
    public Credits getCredits(int id);
    public boolean createCredits(Credits credits);

    public ObservableList<Production> getProduction();
    public Production getProduction(int id);
    public boolean createProduction(Production production);

}
