package Model;

import Util.Shift.Shift;

import java.util.ArrayList;

public class RandomRoster extends RosterModel {

    /**
     *  The normal constructor for the roster class;
     */
    public RandomRoster(String str){
        // Initialize the second dimension of the roster
        for (int i=0; i<DAYS_IN_WEEK; i++){
            this.add(new ArrayList<>());
        }

        String[] staffToAdd = {"Az", "Jack", "Harry", "Jesse", "Jerome", "Bella"};
        String[] startTimes = {"9am", "10am", "11am", "12pm", "3pm", "4pm", "5pm"};
        String[] endTimes = {"3pm", "4pm", "5pm", "7pm", "9pm", "10pm", "TC"};

        for (int i=0; i<staffToAdd.length; i++){
            staff.add(new Staff(staffToAdd[i], Math.random() > 0.9));
        }

        // Initialise the second dimension of the roster
        // TODO: 3/7/18 properly implement this
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
                        continue;
                    if (Math.random() > 0.5)
                        randomSection = sec;
                }
            }
            test_Shifts.add(new Shift(s,randomStartTime, randomEndTime, randomSection));
        }

        // Adds each day of shifts at a time
        for (int i=0; i<DAYS_IN_WEEK; i++) {
            for (Shift s : test_Shifts) {
                this.get(i).add(s);
            }
        }
    }
}
