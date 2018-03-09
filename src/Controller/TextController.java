package Controller;

import Model.RandomRoster;
import View.TextUIView;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * This class is designed to capture the notion of a
 * a terminal like command interface for the user to
 * manipulate the text interface.
 */
public class TextController {

    private String terminalName = "rg2000";

    private TextUIView view;

    private RandomRoster model;

    /**
     * Patterns for the commands that are allowed
     */
    private static final Pattern COMMAND = Pattern.compile("run|exit|print|rain|mode");

    private static final Pattern BOOLEAN = Pattern.compile("on|off|true|false|0|1");

    private Pattern INTEGER = Pattern.compile("[0-9]*");

    /**
     * Exception messages for the various syntax errors the user may encounter
     */
    private static final String BOOLEAN_EXCEPTION_MSG =
            "\nInvalid Boolean>> Usage: <command> <bool>\n" +
                    "<bool>: on\t true\t off\t false\t 0\t 1\n";

    private static final String COMMAND_EXCEPTION_MSG =
            "Invalid Command>> Usage: <command>\n" +
            "<command>: run\t print\t exit\n";
    private static final String INTEGER_EXCEPTION_MSG =
            "Invalid Integer>> Usage: <command> <integer>\n" +
                    "<integer>: 0 - 9 (a single non-decimal positive number)";

    /**
     * Default constructor for TextController designed to
     * help handle text input COMMAND from the user
     * @param model
     * @param view
     */
    public TextController(RandomRoster model, TextUIView view){
        this.model = model;
        this.view = view;
        readTextInput();
    }

    /**
     * This method reads the text input from the user
     * using the System.in command line
     */
    public void readTextInput(){
        System.out.print(terminalName + ": ");
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()) {
            parseInput(sc);
            System.out.print(terminalName + ": ");
        }
    }

    /**
     * This method takes the input and takes the appropriate response
     * on the model/view
     * @param sc user command to be interpreted
     */
    public void parseInput(Scanner sc) {
        if (!sc.hasNext(COMMAND)) {
            System.err.print(COMMAND_EXCEPTION_MSG);
        }
        // Reads the user input from the scanner
        String input = sc.next();
        if (input.equals("exit"))
            close(sc);
        if (input.equals("print"))
            print();
        if (input.equals("run"))
            run();
        if (input.equals("rain")){
            try {
                setRain(parseBoolean(sc));
            } catch (Exception e) {
                System.err.print(e);
                sc.next();
            }
        }
        if (input.equals("mode")){
            try {
                setMode(parseInteger(sc));
            } catch (Exception e) {
                System.err.print(e);
                sc.next();
            }
        }
    }

    /**
     * This method reads a boolean argument from the user, and returns
     * the value that they have selected
     * @param sc to read from
     * @return the intended boolean
     * @throws Exception if invalid input
     */
    public boolean parseBoolean(Scanner sc) throws Exception {
        // Scanner is empty
        if (!sc.hasNext())
            throw new Exception("Scanner is empty!\n");

        // Invalid boolean check
        if (!sc.hasNext(BOOLEAN))
            throw new Exception(BOOLEAN_EXCEPTION_MSG);

        String in = sc.next();
        if (in.equals("on") || in.equals("off") || in.equals("true") || in.equals("1"))
            return true;
        else
            return false;
    }

    public int parseInteger(Scanner sc) throws Exception {
        // Scanner is empty
        if (!sc.hasNext())
            throw new Exception("Scanner is empty!\n");

        // Invalid integer check
        if (!sc.hasNext(INTEGER))
            throw new Exception(INTEGER_EXCEPTION_MSG);

        // Converts the integer into a string
        String in = sc.next();
        return Integer.parseInt(in);
    }

    /**
     * This method closes the user text input scanner and
     * deals with some of the errors that could crop up
     * @param sc the scanner to close
     */
    public void close(Scanner sc){
        sc.close();
    }

    /**
     * Displays the roster to the user on the command line
     */
    public void print(){
        this.view.print();
    }

    /**
     * Creates a new version of the roster
     */
    public void run(){
        this.model = new RandomRoster(model.getDate());
    }

    /**
     * Changes the rain on the roster model
     * @param b is it raining (T/F)
     */
    public void setRain(boolean b){
        this.model.setRain(b);
    }

    /**
     * This method changes the business of the shift
     * @param i Difficulty level
     */
    public void setMode(int i){
        this.model.setDifficulty(i);
    }
}
