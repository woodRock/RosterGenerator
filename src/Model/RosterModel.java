package Model;

import Util.Shift.Shift;
import Util.Staff;
import Util.Time;
import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
     * This method will sort the shifts for the day according
     * to there start times, earlier ones first
     * @param shifts the day to be sorted
     * @return the sorted day
     */
    public static ArrayList<Shift> sortDay(ArrayList<Shift> shifts){
        // Compares start times to each other
        Comparator<Shift> TIME = new Comparator<Shift>() {
            @Override
            public int compare(Shift shift, Shift t1) {
                return shift.compareTo(t1);
            }
        };
        Collections.sort(shifts, TIME);
        return shifts;
    }

    /**
     * This method sorts the roster by sections
     * @param section to be sorted into
     * @return only shifts from this section
     */
    public RosterModel getRosterSection(Staff.SECTION section){

     // This ia the output roster that comprises of only shifts
     // that belong to the section parameter
        RosterModel output = new RosterModel(date);

     // This nested loop traverses the roster and isolates the
     // desired shifts that belong to the corresponding section
        for (int col=0; col<DAYS_IN_WEEK; col++){ // Iterates through the columns (days)
            ArrayList<Shift> shiftList = this.get(col);
            for (Shift s: shiftList){
                // If this shift corresponds to the correct section
                if (s.getSection().equals(section))
                    output.get(col).add(s); // Add this shift to the collection
            }
        }
         // This is the completed RosterModel which only contains the section
         // specified by the parameters
        return output;
    }

    /**
     * This method swaps two individual shifts with eachother
     * @param d1 first day to swap
     * @param n1 name of the worker to swap
     * @param d2 second day to be swapped with
     * @param n2 name of the second worker to swap with the first
     */
    public boolean swapShift(DAY_NAMES d1, String n1, DAY_NAMES d2, String n2){
        // TODO: 3/14/18 fix this (doesn't swap over days), and sections
        // This temp local variable is needed for the swap
        Shift s1 = getShift(d1, n1);
        Shift s2 = getShift(d2, n2);
        Staff tempStaff = s1.getStaffMember();

        // Checks the if the first shift exists
        int dayNo = getDayIndex(d1);
        int idx = this.get(dayNo).indexOf(getShift(d1, n1));

        int dayNo2 = getDayIndex(d2);
        int idx2 = this.get(dayNo).indexOf(getShift(d2,n2));

        this.get(dayNo).get(idx).setStaff(s2.getStaffMember());
        this.get(dayNo2).get(idx2).setStaff(tempStaff);
        // TODO: 3/14/18 Make this check properly 
        return true;
    }

    public Shift getShift(DAY_NAMES d, String name){
        for (Shift s: this.get(d.getDay())){
            if (s.equals(name))
                return s;
        }
        return null;
    }

    public int getDayIndex(DAY_NAMES d){
        return d.getDay();
    }

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

    public ArrayList<Shift> getShifts(DAY_NAMES d){
        return this.get(d.getDay());
    }

    public void setShifts(DAY_NAMES d, ArrayList<Shift> shifts){
        this.set(d.getDay(), shifts);
    }

    public RosterModel(){}
}
