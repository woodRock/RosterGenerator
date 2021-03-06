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

    private String[] staffToAdd = {"Harry", "Jesse", "Jerome", "Bella"};
    private String[] startTimes = {"0900", "1000", "1100", "1200", "1500", "1600", "1700"};
    private String[] endTimes = {"1500", "1600", "1700", "1900", "1100", "2200", "2100"};

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
    }

    public ArrayList<Staff> getManagers(){
        ArrayList<Staff> out = new ArrayList<>();
        for (Staff s: this.staff){
            if (s.isDm())
                out.add(s);
        }
        return out;
    }
}
