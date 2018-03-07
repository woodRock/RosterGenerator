package View;

import Model.Roster;

/**
 * This class captures allows for the roster to be represented as text
 */
public class TextUI {

    private String rosterText;

    /**
     * The default constructor for the text representation
     * of the user interface
     * @param roster to be converted to text
     */
    public TextUI(Roster roster){
        this.rosterText = buildRoster(roster);
    }

    /**
     * This method builds the string for the TextUI
     * @param roster to be represented by text
     * @return a text representation of the roster
     */
    private String buildRoster(Roster roster){
        return "";
    }



}
