package Model.Heuristic;

import Model.Shift.Shift;

import static Model.Heuristic.StrategyHeuristic.QUALITY.*;
/**
 * This class captures the notion of deciding whether or not
 * a staff member is available for a rostered shift, the Availability
 * heuristic.
 */
public class Availability implements StrategyHeuristic {

    /**
     * This method works out if a staff member is available
     * to work a certian shift
     * @param shift to be evaluated
     * @return true if available, false otherwise
     */
    public boolean evaluate(Shift shift){
        return shift.getStaffMember().canWork(shift.getDay());
    }

    /**
     * This method works out the quality of a shift
     * @param shift to be evaluated
     * @return the quality of the shift based on availability
     */
    public QUALITY evaluateQuality(Shift shift){
        QUALITY quality = INVALID;
        if (shift.getStaffMember().canWork(shift.getDay()))
            quality = VALID;
        return quality;
    }
}
