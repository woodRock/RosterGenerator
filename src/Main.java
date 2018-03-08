import Model.RandomRoster;
import Model.RosterModel;
import View.TextUIView;

/**
 * This Main class runs the program by creating an instance of the
 * RosterGenerator UI
 */
public class Main {
    public static void main(String[] args){
        TextUIView test = new TextUIView(new RandomRoster("Penis"));
    }
}


