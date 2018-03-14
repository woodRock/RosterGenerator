package Model;

import Util.Shift.Shift;
import Util.Staff;

import java.util.ArrayList;

/**
 * This class capture the notion of a basic version of the roster
 * which implemented the basic heuristic of availability
 */
public class BasicRoster extends RosterModel {

    private static final int MANGERS_NEEDED_PER_DAY = 2;

    private static final int SHIFT_MAX = 5;

    /**
     * These are the staff for the randomly generated roster, eventually they will be
     * stored and read from a serialized file
     */
    private String[] staffToAdd = {"Harry", "Jesse", "Jerome", "Bella"};

    /**
     * These are the start times, they will eventually be stored as a tuple linked to a
     * shift type, that will store the end time and the break time as well
     */
    private String[] startTimes = {"0900", "1000", "1100", "1200", "1500", "1600", "1700"};
    private String[] endTimes = {"1500", "1600", "1700", "1900", "1100", "2200", "2100"};

    /**
     * The constructor for a BasicRoster which creates a roster with some slightly informed
     * decisions behind the selection of shifts
     */
    public BasicRoster(String date){
        super(date);
        // Add the staff to the roster
        this.staff.add(new Staff("Az", true));
        this.staff.add(new Staff("Jack", true));
        for (int i=0; i<staffToAdd.length; i++){
            this.staff.add(new Staff(staffToAdd[i], false));
        }

        ArrayList<Staff> managers = getManagers();

        // Add all the manager shifts in for the week
        for (int d=0; d<DAY_NAMES.values().length; d++) {
            for (Staff m: managers){
                if (this.get(d).size() < MANGERS_NEEDED_PER_DAY && m.getShiftCount() < SHIFT_MAX)
                    this.get(d).add(new Shift(m, "0900", "1600", Staff.SECTION.MANAGER));
            }
        }
    }

    /**
     * This method returns a list of all of the managers
     * that are in the list of staff on the roster
     * @return the list of managers
     */
    public ArrayList<Staff> getManagers(){
        ArrayList<Staff> out = new ArrayList<>();
        for (Staff s: this.staff){
            if (s.isDm())
                out.add(s);
        }
        return out;
    }
}
