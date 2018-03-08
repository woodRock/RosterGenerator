package Model.Heuristic;

import Model.Shift.Shift;

/**
 * This interface captures the notion of a priority, a priority
 * is a comparison that will be taken into account when seeing
 * if a shift is valid, preferred or optimal
 */
public interface StrategyHeuristic {

    /**
     * This is the enum for the quality of a shift.
     * Valid - it can be worked
     * Preferred - the staff member wants to work at this time
     * Required - the staff member is needed
     * Optimal - Preferred && Required
     */
    enum QUALITY {
        INVALID, VALID, PREFERRED, REQUIRED, OPTIMAL
    }

    /**
     * This method returns whether or not a shift will work
     * @param shift
     * @return
     */
    boolean evaluate(Shift shift);

    /**
     * This method returns the quality of the suggested shift
     * based on the current priority
     * @param shift
     * @return
     */
    QUALITY evaluateQuality(Shift shift);
}
