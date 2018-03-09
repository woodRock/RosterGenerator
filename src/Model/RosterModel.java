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

    /**
     * This enum was create to store the busyness of a shift
     */
    public enum Difficulty {
        // Levels of difficulty in ascending order of busyness
        DEAD (0), EASY (1), MEDIUM (2), HARD (3), INSANE (4);

        private final int level;

        Difficulty(int i){
            this.level = i;
        }

        public int getLevel(){
            return level;
        }
    }

    /**
     *  Stores the day names so that they can be manipulated (i.e shortened) easily
     */
      public enum DAY_NAMES {
        MONDAY (0), TUESDAY (1), WEDNESDAY (2), THURSDAY (3), FRIDAY (4), SATURDAY (5), SUNDAY(6);

        private int day;

        DAY_NAMES(int d){
            this.day = d;
        }

        public int getDay(){
            return day;
        }

    }

    public static final int DAYS_IN_WEEK = 7;

    /**
     * This stores the staff for this iteration of the roster
     */
    protected ArrayList<Staff> staff = new ArrayList<>();

    private String date;

    /**
     * This section stores the information for the tweaks which affect
     * the creation of the roster, (i.e. its selection process)
     */
    public boolean rain;

    /**
     * Stores the difficulty or how busy a shift is going to be,
     * this effects how many staff will be needed for each shift
     */
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

    /**
     * This method returns all the shifts for a day
     * @param d the day to get the shifts for
     * @return shifts for that day
     */
    public ArrayList<Shift> getShifts(DAY_NAMES d){
        ArrayList<Shift> shifts = new ArrayList<>();
        switch (d){
            case MONDAY: return this.get(0);
            case TUESDAY: return this.get(1);
            case WEDNESDAY: return this.get(2);
            case THURSDAY: return this.get(3);
            case FRIDAY: return this.get(4);
            case SATURDAY: return this.get(5);
            case SUNDAY: return this.get(6);
        }
        return shifts;
    }

    /**
     * This method sets the difficulty of the model based
     * on a number
     * @param i level of difficulty
     */
    public void setDifficulty(int i) {
        for (Difficulty d : Difficulty.values()) {
            if (d.getLevel() == i)
                this.difficulty = d;
        }
    }

    public String getDate(){
        return this.date;
    }

    public void setRain (boolean b){
        this.rain = b;
    }

    public void addStaff(Staff s){
        this.staff.add(s);
    }

    public boolean removeStaff(String nm){
        return this.staff.remove(getStaff(nm));
    }

    public void addShift(DAY_NAMES d, String name, String start, String end, String sec){
        this.get(d.getDay()).add(new Shift(getStaff(name), start, end, findSection(sec)));
    }

    public boolean removeShift(DAY_NAMES d, String name){
        Staff staff = getStaff(name);
        for (Shift s: this.get(d.getDay())){
            if (s.getStaffMember().getName().equals(name))
                return this.get(d.getDay()).remove(s);
        }
        return false;
    }

    public Staff getStaff(String name){
        for (Staff s: this.staff){
            if (s.getName().equals(name)) {
                return s;
            }
        }
        return null;
    }

    public Staff.SECTION findSection(String sec){
        switch (sec){
            case "bar": return Staff.SECTION.BAR;
            case "resty": return Staff.SECTION.RESTAURANT;
            case "barfloor": return Staff.SECTION.BAR_FLOOR;
            case "manager": return Staff.SECTION.MANAGER;
            default: return null;
        }
    }

    public ArrayList<Staff> getStaffList() {
        return staff;
    }

    public RosterModel(){}
}
