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
public abstract class RosterModel extends ArrayList<ArrayList<Shift>>{

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
    protected ArrayList<Staff> staff = new ArrayList<>();
    private String date;
    public boolean rain;
    private Difficulty difficulty;

    public RosterModel(String date){
        this.date = date;
        // Initialize the second dimension of the roster
        for (int i=0; i<DAYS_IN_WEEK; i++){
            this.add(new ArrayList<>());
        }
    }

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

    public RosterModel getRosterSection(Staff.SECTION section){

        RosterModel output = new BasicRoster(date);

        for (int day=0; day<DAYS_IN_WEEK; day++){
            ArrayList<Shift> shiftList = this.get(day);
            for (Shift s: shiftList){
                if (s.getSection().equals(section));
            }
        }
        return output;
    }

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
}
