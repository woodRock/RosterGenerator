package Model;

import Util.Shift.Shift;
import Util.Staff;

import java.util.ArrayList;

public class RandomRoster extends RosterModel {

    /**
     * These are the staff for the randomly generated roster, eventually they will be
     * stored and read from a serialized file
     */
    private String[] staffToAdd = {"Az", "Jack", "Harry", "Jesse", "Jerome", "Bella"};

    /**
     * These are the start times, they will eventually be stored as a tuple linked to a
     * shift type, that will store the end time and the break time as well
     */
    private String[] startTimes = {"0900", "1000", "1100", "1200", "1500", "1600", "1700"};
    private String[] endTimes = {"1500", "1600", "1700", "1900", "1100", "2200", "2100"};

    /**
     *  The normal constructor for the Random Roster, eventually needs to read a file
     *  and then create the roster based off that
     */
    public RandomRoster(String date){
        // Initializes the rosters days using the super class
        super(date);
        // Populates the roster randomly
        run();
    }

    /**
     * This method generates the random roster and populated it
     */
    public void run(){
        for (int i=0; i<staffToAdd.length; i++)
            staff.add(new Staff(staffToAdd[i], Math.random() > 0.9));

        ArrayList<Shift> test_Shifts = new ArrayList<>();

        for (Staff s: staff){
            String randomStartTime = startTimes[(int)(Math.random()*(startTimes.length))];
            String randomEndTime = endTimes[(int)(Math.random()*(endTimes.length))];
            Staff.SECTION randomSection = null;

            // Finds a random section for a staff member
            for (Staff.SECTION sec: Staff.SECTION.values()){
                if (s.isDm())
                    randomSection = Staff.SECTION.MANAGER;
                else {
                    // Dont assign a manger shift to a non dm
                    if (randomSection == Staff.SECTION.MANAGER)
                        randomSection = Staff.SECTION.OUTSIDE;
                    if (Math.random() > 0.5)
                        randomSection = sec;
                }
            }

            if (randomSection == null)
                randomSection = Staff.SECTION.OUTSIDE;

            test_Shifts.add(new Shift(s,randomStartTime, randomEndTime, randomSection));
        }

        test_Shifts = sortDay(test_Shifts);

        // Adds each day of shifts at a time
        for (int i=0; i<DAYS_IN_WEEK; i++) {
            for (Shift s : test_Shifts)
                this.get(i).add(s);
        }

    }
}
