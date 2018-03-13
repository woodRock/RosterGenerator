package Util.Shift;

import Model.RosterModel;
import Util.Staff;
import Util.Time;

/**
 * This class captures the notion of an individual shift, it is a tuple and
 * a linked-list that stores the information required to make informed rosters
 */
public class Shift implements Comparable<Shift>{

    /**
     * Stores the type of shift, as this changes what information
     * is required
     */
    public StrategyShift shiftType;

    public RosterModel.DAY_NAMES day;

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
    private Time startTime;

    private Time endTime;

    /**
     * Default constructor for a shift, not fully implemented yet
     */
    public Shift(Staff staff, String startTime, String endTime, Staff.SECTION section){
        this.staffMember = staff;
        this.section = section;
        this.startTime = new Time(startTime);
        this.endTime = new Time(endTime);
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
        return this.startTime.toString();
    }

    public String getEndTime(){
        return this.endTime.toString();
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
        return this.section;
    }

    public RosterModel.DAY_NAMES getDay(){
        return this.day;
    }

    public int compareTo(Shift other){
        return this.startTime.compareTo(other.startTime);
    }
}
