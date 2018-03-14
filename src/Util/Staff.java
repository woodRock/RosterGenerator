package Util;

import Model.RosterModel;

import java.util.ArrayList;

/**
 *  This class captures the notion of a staff member, and all the
 *  information that will be required to be stored in order to
 *  create a roster
 */
public class Staff {

    /**
     * An enum to store the employment type of the staff member
     */
    public enum EMPLOYMENT {
        PARTIME, FULLTIME, OTHER
    }

    /**
     *  An enum to store what sections a staff member can work
     */
    public enum SECTION {
        OUTSIDE, BAR, RESTAURANT, BAR_FLOOR, MANAGER
    }

    /**
     *  An enum to store what skills a staff member can do
     */
    public enum SKILL {
        COFFEE, COCKTAILS, PASS, TABLE_SERVICE, UNDERWATER_CERAMICS
    }

    private String name;

    private ArrayList<SECTION> sections = new ArrayList<>();

    private ArrayList<RosterModel.DAY_NAMES> daysCantWork = new ArrayList<>();

    private int shiftCount;

    /**
     * Default constructor for a Staff member
     * @param name first name for the roster text
     * @param isDM whether or not they are a manager
     */
    public Staff(String name, boolean isDM){
        this.name = name;
        if (isDM)
            this.sections.add(SECTION.MANAGER);
        shiftCount = 0;
    }

    public String getName(){
        return this.name;
    }

    /**
     * This method checks if a staff member can work
     * a certain day
     * @param day to check for
     * @return true if they can, false otherwise
     */
    public boolean canWork(RosterModel.DAY_NAMES day){
        for (int i=0; i<daysCantWork.size(); i++){
            if (day.equals(daysCantWork.get(i)))
                return false;
        }
        return true;
    }

    /**
     * This method checks whether a staff member is a duty manager
     * @return true if they are, false otherwise
     */
    public boolean isDm(){
        // Is DM one of there sections?
        for (SECTION s: sections){
            if (s.equals(SECTION.MANAGER))
                return true;
        }
        return false;
    }

    public int getShiftCount(){
        return this.shiftCount;
    }

    public void addDayCantWork(RosterModel.DAY_NAMES day){
        daysCantWork.add(day);
    }
}
