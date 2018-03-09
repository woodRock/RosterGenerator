package Model;

import Util.Staff;

import java.util.ArrayList;

/**
 * This class capture the notion of a basic version of the roster
 * which implemented the basic heuristic of availability
 */
public class BasicRoster extends RosterModel {

    /**
     * These are the staff for the randomly generated roster, eventually they will be
     * stored and read from a serialized file
     */
    private String[] staffToAdd = {"Az", "Jack", "Harry", "Jesse", "Jerome", "Bella"};

    /**
     * These are the start times, they will eventually be stored as a tuple linked to a
     * shift type, that will store the end time and the break time as well
     */
    private String[] startTimes = {"9am", "10am", "11am", "12pm", "3pm", "4pm", "5pm"};
    private String[] endTimes = {"3pm", "4pm", "5pm", "7pm", "9pm", "10pm", "TC"};

    /**
     * The constructor for a BasicRoster which creates a roster with some slightly informed
     * decisions behind the selection of shifts
     */
    public BasicRoster(String date){

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
