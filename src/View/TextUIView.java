package View;

import Model.RosterModel;
import Model.Staff;

/**
 * This class captures allows for the roster to be represented as text
 */
public class TextUIView {

    private String rosterText;

    private final static int ROSTER_CHARACTER_WIDTH = 41;

    private final static int CELL_SPACING = 5;

    /**
     * The default constructor for the text representation
     * of the user interface
     * @param roster to be converted to text
     */
    public TextUIView(RosterModel roster){
        this.rosterText = buildRoster(roster);

        /**
         * Displays the text representation of the roster to the user
         */
        System.out.print(rosterText);
    }

    /**
     * This method builds the string for the TextUIView
     * @param roster to be represented by text
     * @return a text representation of the roster
     */
    private String buildRoster(RosterModel roster){

        // This will eventually be the full roster;
        String output = "";
        output += addLine();
        output += addDays();
        output += addLine();

        RosterModel sectionShifts;

        for (Staff.SECTION s: Staff.SECTION.values()){
            sectionShifts = roster.getRosterSection(s);
            int sectionShiftMax = 0;
            for (int i = 0; i<sectionShifts.size(); i++){
                if (sectionShifts.size() > sectionShiftMax) {
                    sectionShiftMax  = sectionShifts.get(i).size();
                }
            }
            output += getTitle(s.toString());
            // Adds these shifts to the output
            for (int i = 0; i<sectionShiftMax; i++){
                output += addShiftRow(sectionShifts, 0, i);
                output += addLine();
            }
        }

        return output;
    }

    /**
     * This method returns a centered title based on the width
     * of the roster
     * @param titleText to be centered and prepared for display
     * @return the finished title with borders
     */
    public String getTitle(String titleText){
        int titlePadding = 2;
        String output = "";
        // Loops across the entire width of the text represented roster
        for (int i = 0; i<(ROSTER_CHARACTER_WIDTH - titleText.length()); i++){
            // Calculates the center of the roster
            if (i == (ROSTER_CHARACTER_WIDTH - (titleText.length()+titlePadding)) /2)
                output += "[" + titleText + "]"; // Adds the border
            output += "-";
        }
        output += "\n";
        return output;
    }

    /**
     * This method adds a row of shifts to the text representation
     * of the roster
     * @param roster to be turned into text
     * @return the text representation
     */
    public String addShiftRow(RosterModel roster, int m, int n){

        String startSpace = "";
        String endSpace = "";
        String output = "";

        // Adds the start times for all of the shifts to the roster
        for (int i = 0; i< RosterModel.DAYS_IN_WEEK; i++) {
            String startTime = roster.getRoster().get(m).get(n).getStartTime();
            int startTimeLength = roster.getRoster().get(m).get(n).getStartTime().length();
            if (startTimeLength == 5){
                endSpace = "";
                startSpace = "|";
            }
            if (startTimeLength == 4){
                startSpace = "| ";
                endSpace = "";
            }
            if (startTimeLength == 3){
                startSpace = "| ";
                endSpace = " ";
            }
            if (i == 0){
                startSpace = "| ";
            }
            output += startSpace + startTime + endSpace;
        }

        output += "|\n";

        // Loop adds the names for the shift to the roster
        for (int i = 0; i< RosterModel.DAYS_IN_WEEK; i++) {
            String name = roster.getRoster().get(m).get(n).getName();
            if (name.length() > CELL_SPACING)
                name = name.substring(0,CELL_SPACING);
            int nameLength = name.length();
            if (nameLength == 5){
                endSpace = "";
                startSpace = "|";
            }
            if (nameLength == 4){
                startSpace = "|";
                endSpace = " ";
            }
            if (nameLength == 3){
                startSpace = "| ";
                endSpace = " ";
            }
            if (nameLength == 2){
                startSpace = "| ";
                endSpace = "  ";
            }
            if (i == 0){
                startSpace = "|";
                if (nameLength == 2)
                    startSpace = "| ";
            }
            output += startSpace +  name + endSpace;
        }
        output += "|\n";


        // Loop adds the end times for shifts to the roster
        for (int i = 0; i< RosterModel.DAYS_IN_WEEK; i++) {
            String endTime = roster.getRoster().get(m).get(n).getEndTime();
            int endTimeLength = roster.getRoster().get(m).get(n).getEndTime().length();
            if (endTimeLength == 5){
                endSpace = "";
                startSpace = "|";
            }
            if (endTimeLength == 4){
                startSpace = "| ";
                endSpace = " ";
            }
            if (endTimeLength == 3){
                startSpace = "| ";
                endSpace = " ";
            }
            if (i == 0){
                startSpace = "| ";
            }
            output += startSpace + endTime + endSpace;
        }
        output += "|\n";

        // Returns the completed shift row ready for presentation
        return output;
    }

    /**
     * Adds the day names across the top of the roster
     * @return
     */
    public String addDays(){
        String output = "";

        // The character length of day names on the rosters
        int dayStringLength = 3;

        // Spaces added if not the first
        String space = "";

        // Loop to go through the columns of the roster
        int i = 0;
        for (RosterModel.DAY_NAMES d: RosterModel.DAY_NAMES.values()){
            if (i!=0)
                space = " | ";
            else
                space = "| ";
            output += space + d.toString().substring(0,dayStringLength);
            i++;

        }
        output += " |\n";
        return output;
    }

    /**
     * This method adds a row of text to the roster
     * @param str to be added along the row
     * @return the finished row with padding
     */
    public String addRow(String str){
        String space;
        String output = "";
        // Loop to go through the columns of the roster
        for (int i = 0; i< RosterModel.DAYS_IN_WEEK; i++) {
            if (i!=0)
                space = " |";
            else
                space = "|";
            output += space + str;
        }
        return output;
    }

    /**
     * This method adds a long horizontal on the roster
     * @return the line string to be added
     */
    public String addLine(){
        String output = "";
        // Loop add a Line underneath the day titles
        for (int i = 0; i< RosterModel.DAYS_IN_WEEK; i++) {
            output += "------";
        }
        output += "-\n";
        return output;
    }
}
