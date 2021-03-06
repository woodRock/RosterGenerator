import Controller.TextController;
import Model.BasicRoster;
import Model.RandomRoster;
import Model.RosterModel;
import View.TextUIView;

/**
 * This Main class runs the program by creating an instance of the
 * RosterGenerator UI
 */
public class Main {
    public static void main(String[] args){
        System.out.print("Welcome to the RosterGenerator2000!\n");
        BasicRoster model = new BasicRoster("March, Mon-Sun");
        TextUIView view = new TextUIView(model);
        TextController controller = new TextController(model, view);
    }
}


