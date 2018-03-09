package Util.Shift;

import Model.RosterModel;
import Util.Staff;

/**
 * This class captures the notion of an individual shift, it is a tuple and
 * a linked-list that stores the information required to make informed rosters
 */
public class Shift {

    /**
     * Stores the type of shift, as this changes what information
     * is required
     */
    public StrategyShift shiftType;

    /**
     * This stores the previous and next shift in a
     * double linked-list like manor
     */
    public Shift previous;

    public Shift next;

    private Staff staffMember;

    private Staff.SECTION section;

    /**
     * The times related that will be stored for any
     * type of shift
     */
    private String startTime;

    private String endTime;

    /**
     * Default constructor for a shift, not fully implemented yet
     */
    public Shift(Staff staff, String startTime, String endTime, Staff.SECTION section){
        this.shiftType = new DayShift();
        this.staffMember = staff;
        this.section = section;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * This method checks who is working the shift, against
     * the staff member assumed to be working it
     * @param name to be checked for
     * @return true if working, false otherwise
     */
    public boolean equals(String name){
        return this.staffMember.getName().equals(name);
    }

    /**
     * This method returns the shift as a string
     * @return the string ready to be displayed
     */
    public String toString(){
        String out = "";
        out += this.staffMember.getName() + ":\t" +
                this.startTime + "-" + this.endTime + "\n";
        return out;
    }

    public String getName(){
        return this.staffMember.getName();
    }

    public String getStartTime(){
        return this.startTime;
    }

    public String getEndTime(){
        return this.endTime;
    }

    public Staff getStaffMember(){
        return this.staffMember;
    }

    public Shift getPrevious(){
        return this.previous;
    }

    public Shift getNext(){
        return this.next;
    }

    public Staff.SECTION getSection() {
        return section;
    }

    public RosterModel.DAY_NAMES getDay(){
        return RosterModel.DAY_NAMES.MONDAY;
    }
}
