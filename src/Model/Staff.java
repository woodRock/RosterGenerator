package Model;

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

    private EMPLOYMENT role;

    private boolean shiftManager;

    private ArrayList<SKILL> skills;

    private ArrayList<SECTION> sections;

    private int maxShiftNo;

    private int minShiftNo;

    private ArrayList<String> daysCantWork;

    public Staff(String name){
        this.name = name;
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
    public boolean canWork(String day){
        for (int i=0; i<daysCantWork.size(); i++){
            if (day.equals(daysCantWork.get(i)))
                return false;
        }
        return true;
    }
}
