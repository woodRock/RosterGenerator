package Util.Shift;

/**
 * This class captures the notion of a split shift;
 * that is a shift that usually has a 1 hour unpaid break
 * and is usually longer than 7 hours
 */
public class SplitShift implements StrategyShift {

    public String splitStartTime;

    public String splitEndTime;
}
