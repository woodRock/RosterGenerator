package Util.Shift;

import Model.RosterModel;
import Util.Staff;
import Util.Time;

/**
 * This class captures the notion of an individual shift, it is a tuple and
 * a linked-list that stores the information required to make informed rosters
 */
public class Shift implements Comparable<Shift>{

    public StrategyShift shiftType;
    public RosterModel.DAY_NAMES day;
    public Shift previous;
    public Shift next;
    private Staff staffMember;
    private Staff.SECTION section;
    private Time startTime;
    private Time endTime;

    public Shift(Staff staff, String startTime, String endTime, Staff.SECTION section){
        this.staffMember = staff;
        this.section = section;
        this.startTime = new Time(startTime);
        this.endTime = new Time(endTime);
    }

    public boolean equals(String name){
        return this.staffMember.getName().equals(name);
    }

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

    public void setStartTime(String time){
        this.startTime = new Time(time);
    }

    public void setEndTime(String time){
        this.endTime = new Time(time);
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

    public void setSection(Staff.SECTION section){
        this.section = section;
    }

    public void setStaff(Staff staff){
        this.staffMember = staff;
    }
    public RosterModel.DAY_NAMES getDay(){
        return this.day;
    }

    public int compareTo(Shift other){
        return this.startTime.compareTo(other.startTime);
    }
}
