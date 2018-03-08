package Model.Heuristic;

import Model.Shift.Shift;

/**
 * This class captures the notion of the Preferred StrategyHeuristic, taking
 * into account the staff members preferences towards there shifts
 */
public class Preferred implements StrategyHeuristic {

    /**
     * This method evaluates whether a staff member would prefer to work
     * a shift.
     * @param shift to be evaluated
     * @return true if preferred, false otherwise
     */
    public boolean evaluate(Shift shift){
        boolean result = false;
        /**
         * If a staff member isn't working the previous/next day
         * start time is not factored into the preference heuristic
         */
        if (shift.getPrevious() == null && shift.getNext() == null)
            result = true;
        // TODO: 3/8/18 Check late finishes, early starts, days off etc.
        return result;
    }

    /**
     * This method returns the Quality of a shift based on the preference of
     * the staff member
     * @param shift to be evaluated
     * @return the quality of the shift, based on staff preferences
     */
    public StrategyHeuristic.QUALITY evaluateQuality(Shift shift){
        // TODO: 3/7/18 to be implemented
        return null;
    }

}

