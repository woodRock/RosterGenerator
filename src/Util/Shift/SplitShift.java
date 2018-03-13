package Util.Shift;

/**
 * This class captures the notion of a split shift;
 * that is a shift that usually has a 1 hour unpaid break
 * and is usually longer than 7 hours
 */
public class SplitShift implements StrategyShift {

    /**
     * Stores the start and end time for the split shift;
     * this is when the staff member is rostered on for
     * their 1 hour break, this may vary depending on how
     * busy it is that day
     */
    public String splitStart;
    public String splitEnd;
}
