package Model;

import Util.Shift.Shift;

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

    public RosterModel(){
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
        RosterModel output = new RosterModel();

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

    public Shift getShift(int i){
        return this.get(i).get(0);
    }

    public int rows(){
        return this.get(0).size();
    }

    public int cols(){
        return this.size();
    }

    public ArrayList<ArrayList<Shift>> getRoster() {
        return this;
    }
}
