package Util.Heuristic;

import Util.Shift.Shift;

public abstract class Heuristic {

    /**
     * Stores the basic information needed for all heuristics, using an
     * interface so that further heuristics can easily be implemented
     */
    public StrategyHeuristic heuristic;

    public boolean evalutate(Shift shift){
        return this.heuristic.evaluate(shift);
    }

    public StrategyHeuristic.QUALITY evaluateQuality(Shift shift){
        return this.heuristic.evaluateQuality(shift);
    }
}
