package Model;

import Util.Shift.Shift;
import Util.Staff;

import java.util.ArrayList;

/**
 *  This class captures the notion of an entire RosterModel.
 *  It is comprised of several smaller lightweight classes
 *  that combine to represent a roster.
 */
public class RosterModel extends ArrayList<ArrayList<Shift>>{

    public static final int DAYS_IN_WEEK = 7;

    /**
     *  Stores the day names so that they can be manipulated (i.e shortened) easily
     */
      public enum DAY_NAMES {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

    /**
     * This stores the staff for this iteration of the roster
     */
    protected ArrayList<Staff> staff = new ArrayList<>();

    private String date;

    public RosterModel(){}

    /**
     * This section stores the information for the tweaks which affect
     * the creation of the roster, (i.e. its selection process)
     */
    public boolean rain;

    public enum Difficulty {

        DEAD (0), EASY (1), MEDIUM (2), HARD (3), INSANE (4);

        private final int level;

        Difficulty(int i){
            this.level = i;
        }

        public int getLevel(){
            return level;
        }
    }

    private Difficulty difficulty;

    /**
     * This is the default constructor for the RosterModel
     * @param date to be printed at the top of the roster
     */
    public RosterModel(String date){
        this.date = date;
        // Initialize the second dimension of the roster
        for (int i=0; i<DAYS_IN_WEEK; i++){
            this.add(new ArrayList<>());
        }
    }

    /**
     * This method sorts the roster by sections
     * @param section to be sorted into
     * @return only shifts from this section
     */
    public RosterModel getRosterSection(Staff.SECTION section){
        /**
         * This ia the output roster that comprises of only shifts
         * that belong to the section parameter
         */
        RosterModel output = new RosterModel(date);

        /**
         * This nested loop traverses the roster and isolates the
         * desired shifts that belong to the corresponding section
         */
        for (int col=0; col<DAYS_IN_WEEK; col++){ // Iterates through the columns (days)
            ArrayList<Shift> shiftList = this.get(col);
            for (Shift s: shiftList){
                // If this shift corresponds to the correct section
                if (s.getSection().equals(section))
                    output.get(col).add(s); // Add this shift to the collection
            }
        }

        /**
         * This is the completed RosterModel which only contains the section
         * specified by the parameters
         */
        return output;
    }

    public void run(){ }

    /**
     * This method returns a shift from the Roster based on
     * the staff member who may be working that day
     * @param day to check for the staff member
     * @param name to find on the shifts of the day
     * @return the shift if it exists, false otherwise
     */
    public Shift getShift(int day, String name){
        for (Shift s: this.get(day)){
            if (s.equals(name))
                return s;
        }
        return null;
    }

    public ArrayList<ArrayList<Shift>> getRoster() {
        return this;
    }

    public String getDate(){
        return date;
    }

    public void setRain (boolean b){
        this.rain = b;
    }

    /**
     * This method sets the difficulty of the model based
     * on a number
     * @param i
     */
    public void setDifficulty(int i){
        for (Difficulty d: Difficulty.values()){
            if (d.getLevel() == i)
                this.difficulty = d;
        }
    }
}
