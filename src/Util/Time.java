package Util;

/** This class captures the notion of a time.
 *  it is useful when describing the start or finish
 *  times of breaks or shifts for the roster
 */
public class Time implements Comparable<Time>{

    private String time;

    public Time(String time){
        this.time = time;
    }

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
        // Times must be equal to each other
        return 0;
    }

    public String toString(){
        return this.time;
    }
}
