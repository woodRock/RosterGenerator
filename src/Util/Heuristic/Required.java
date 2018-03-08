package Util.Heuristic;

import Util.Shift.Shift;

/**
 * This class captures the notion of judging whether a staff member
 * is required for a certain shift, this heuristic takes into account
 * things like how busy the shift will be
 */
public class Required implements StrategyHeuristic {

    /**
     * This method evaluates whether a staff member is required for
     * a shift, using the Required StrategyHeuristic
     * @param shift to be evaluated
     * @return true if required, false otherwise
     */
    public boolean evaluate(Shift shift){
        // TODO: 3/7/18 to be implemented 
        return true;
    }

    /**
     * This method evaluates the quality of a shift based on whether
     * on the Required StrategyHeuristic (ie. is this staff member needed today)
     * @param shift to be evaluated
     * @return the quality of this shift based on Requirements
     */
    public StrategyHeuristic.QUALITY evaluateQuality(Shift shift){
        // TODO: 3/7/18 to be implemented 
        return null;
    }
}
