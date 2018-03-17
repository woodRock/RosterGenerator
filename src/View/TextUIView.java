package View;

import Model.RosterModel;
import Util.Staff;

/**
 * This class captures allows for the roster to be represented as text
 */
public class TextUIView {

    private final static String HORIZONTAL_CELL_BORDER = "|";
    private final static String VERTICAL_CELL_BORDER = "-";
    private final static int ROSTER_CHARACTER_WIDTH = 41;
    private final static int CELL_SPACING = 5;
    private RosterModel roster;
    private String rosterText;

    public TextUIView(RosterModel roster){
        this.roster = roster;
        this.rosterText = buildRoster(roster);
    }

    private String buildRoster(RosterModel roster){

        // This will eventually be the full roster;
        String output = "";
        output += getTitle(roster.getDate());
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
            if (sectionShifts.size() == 0){
                continue; // Doesn't add attempt to print empty sections
            }
            output += getTitle(s.toString());

            for (int i = 0; i<sectionShiftMax; i++){
                // TODO: 3/15/18 Change this dayNo 
                output += addShiftRow(sectionShifts, 0, i);
                output += addLine();
            }
        }
        return output;
    }

    public String getTitle(String titleText){
        int titlePadding = 2;
        String output = "";
        // Loops across the entire width of the text represented roster
        for (int i = 0; i<(ROSTER_CHARACTER_WIDTH - titleText.length()); i++){
            // Calculates the center of the roster
            if (i == (ROSTER_CHARACTER_WIDTH - (titleText.length()+titlePadding)) /2)
                output += "[" + titleText + "]"; // Adds the border
            output += VERTICAL_CELL_BORDER;
        }
        output += "\n";
        return output;
    }

    public String addShiftRow(RosterModel roster, int dayNo, int n){
        String output = "";

        // sorts the shifts for that days
        for (int i = 0; i< RosterModel.DAYS_IN_WEEK; i++) {
            this.roster.set(i, RosterModel.sortDay(this.roster.get(i)));
        }

        // Adds the start times for all of the shifts to the roster
        for (int i = 0; i< RosterModel.DAYS_IN_WEEK; i++) {
            String startTime = roster.get(dayNo).get(n).getStartTime();
            output += formatSpacingText(startTime, i==0);
        }
        output += HORIZONTAL_CELL_BORDER + "\n";

        // Loop adds the names for the shift to the roster
        for (int i = 0; i< RosterModel.DAYS_IN_WEEK; i++) {
            String name = roster.get(dayNo).get(n).getName();
            output += formatSpacingText(name, i==0);
        }
        output += HORIZONTAL_CELL_BORDER + "\n";


        // Loop adds the end times for shifts to the roster
        for (int i=0; i< RosterModel.DAYS_IN_WEEK; i++) {
            String endTime = roster.get(dayNo).get(n).getEndTime();
            output += formatSpacingText(endTime, i==0);
        }
        output += HORIZONTAL_CELL_BORDER + "\n";

        // Returns the completed shift row ready for presentation
        return output;
    }

    public String formatSpacingText(String text, boolean first){
        String startSpace = "";
        String endSpace = "";
        if (text.length() > CELL_SPACING)
            text = text.substring(0,CELL_SPACING);
        int textLength = text.length();
        if (textLength == 5){
            endSpace = "";
            startSpace = HORIZONTAL_CELL_BORDER;
        }
        if (textLength == 4){
            startSpace = HORIZONTAL_CELL_BORDER;
            endSpace = " ";
        }
        if (textLength == 3){
            startSpace = HORIZONTAL_CELL_BORDER + " ";
            endSpace = " ";
        }
        if (textLength == 2){
            startSpace = HORIZONTAL_CELL_BORDER + " ";
            endSpace = "  ";
        }
        if (first){
            startSpace = HORIZONTAL_CELL_BORDER;
            if (textLength == 2)
                startSpace = HORIZONTAL_CELL_BORDER + " ";
        }
        return startSpace + text + endSpace;
    }

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
                space = " "+ HORIZONTAL_CELL_BORDER + " ";
            else
                space = HORIZONTAL_CELL_BORDER + " ";
            output += space + d.toString().substring(0,dayStringLength);
            i++;
        }
        output += " " + HORIZONTAL_CELL_BORDER +"\n";

        return output;
    }

    public String addLine(){
        String output = "";
        // Loop add a Line underneath the day titles
        for (int i = 0; i< RosterModel.DAYS_IN_WEEK; i++) {
            for (int n=0; n<6; n++)
                output += VERTICAL_CELL_BORDER;
        }
        output += VERTICAL_CELL_BORDER + "\n";
        return output;
    }

    public void update(){
        this.rosterText = buildRoster(this.roster);
    }

    public void print(){
        this.rosterText = buildRoster(this.roster);
        System.out.print(rosterText);
    }

}
