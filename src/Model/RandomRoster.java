package Model;

import Util.Shift.Shift;
import Util.Staff;

import java.util.ArrayList;

public class RandomRoster extends RosterModel {

    private String[] staffToAdd = {"Az", "Jack", "Harry", "Jesse", "Jerome", "Bella"};
    private String[] startTimes = {"0900", "1000", "1100", "1200", "1500", "1600", "1700"};
    private String[] endTimes = {"1500", "1600", "1700", "1900", "1100", "2200", "2100"};

    public RandomRoster(String date){
        // Initializes the rosters days using the super class
        super(date);
        // Populates the roster randomly
        run();
    }

    public void run(){
        for (int i=0; i<staffToAdd.length; i++)
            staff.add(new Staff(staffToAdd[i], Math.random() > 0.9));

        ArrayList<Shift> test_Shifts = new ArrayList<>();

        for (Staff s: staff){
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

            int rndStart = (int)(Math.random()*(startTimes.length));
            int rndEnd = (int)(Math.random()*(endTimes.length));

            test_Shifts.add(new Shift(s,startTimes[rndStart], endTimes[rndEnd], randomSection));
        }

        test_Shifts = sortDay(test_Shifts);

        // Adds each day of shifts at a time
        for (int i=0; i<DAYS_IN_WEEK; i++) {
            for (Shift s : test_Shifts)
                this.get(i).add(s);
        }

    }
}
