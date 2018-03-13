package Util;

/** This class captures the notion of a time.
 *  it is useful when describing the start or finish
 *  times of breaks or shifts for the roster
 */
public class Time implements Comparable<Time>{

    /**
     * Stores the string version of the time
     */
    private String time;

    /**
     * Stores it in 24 hour format to avoid confusion
     * between the morning and night
     * @param time is the string version of the time
     */
    public Time(String time){
        this.time = time;
    }

    /**
     * This method compares to times to each other to
     * find which one is before the other
     * @param other
     * @return 1 if this is earlier, -1 if other is earlier, 0 if equal
     */
    public int compareTo(Time other){
        for (int i=0; i<this.time.length(); i++){
            int c1 = Integer.parseInt(Character.toString(this.time.charAt(i)));
            int c2 = Integer.parseInt(Character.toString(other.time.charAt(i)));

            // This time is earlier than the other
            if (c1 < c2)
                return 1;
            // This time is later than the other
            if (c2 > c1)
                return -1;
        }
        // Times must be equal to eachother
        return 0;
    }

    /**
     * Method for printing purposes
     * @return the string version of the time
     */
    public String toString(){
        return this.time;
    }
}
