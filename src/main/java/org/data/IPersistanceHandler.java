package org.data;

import javafx.collections.ObservableList;

public interface IPersistanceHandler {
    public ObservableList<Credits> getCredits();
    public Credits getCredits(int id);
    public boolean createCredits(Credits credits);

    public ObservableList<Production> getProduction();
    public Production getProduction(int id);
    public boolean createProduction(Production production);

}
