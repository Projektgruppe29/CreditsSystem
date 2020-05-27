package org.data;

import javafx.collections.ObservableList;

public interface IPersistanceHandler {
    public ObservableList<Credits> getCredits();
    public ObservableList<Credits> getCredits(int id);
    public void createCredits(Credits credits);

    public ObservableList<Production> getProduction();
    public Production getProduction(int id);
    public void createProduction(Production production);

}
