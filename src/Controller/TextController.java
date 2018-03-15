package Controller;

import Model.RandomRoster;
import Model.RosterModel;
import Util.Shift.Shift;
import Util.Staff;
import View.TextUIView;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * This class is designed to capture the notion of a
 * a terminal like command interface for the user to
 * manipulate the text interface.
 */
public class TextController {

    private final static String TERMINAL_NAME = "rg2000";

    private TextUIView view;

    private RosterModel model;

    /**
     * Patterns for the inputs that are allowed in the user input
     */
    private static final Pattern COMMAND = Pattern.compile("run|exit|print|rain|mode|clear|add|rm|ls|swap|help");

    private static final Pattern BOOLEAN = Pattern.compile("on|off|true|false|0|1");

    private static final Pattern OBJECTS = Pattern.compile("staff|roster|shift");

    private Pattern INTEGER = Pattern.compile("[0-9]*");

    private Pattern DAYS = Pattern.compile("mon|tue|wed|thur|fri|sat|sun");

    private Pattern LETTERS = Pattern.compile("[a-zA-Z0-9]+");

    /**
     * Exception messages for the various syntax errors the user may encounter
     */
    private static final String TEXT_INPUT_ERROR_PREFACE = "\nInput Error: ";

    private static final String BOOLEAN_EXCEPTION_MSG =
            "Invalid Boolean!\nUsage: <command> <bool>\n<bool>: on\t true\t off\t false\t 0\t 1\n";
    private static final String COMMAND_EXCEPTION_MSG =
            "Invalid Command!\nUsage: <command>\n<command>: run\t print\t exit\t clear\n";
    private static final String INTEGER_EXCEPTION_MSG =
            "Invalid Integer!\nUsage: <command> <integer>\n<integer>: 0 - 9 (a single non-decimal positive number)\n";
    private static final String WORD_EXCEPTION_MSG =
            "Invalid Word!\nUsage: <command> <word>\n<name>: A-Z a-z (only letters are allowed)\n";
    private static final String OBJECT_ERROR_MSG =
            "Invalid Object!\nUsage: <command> <object>\n<object>: staff\t roster\t shift\n";
    private static final String DAY_ERROR_MSG =
            "Invalid Day!\nUsage: <command> <object> <day>\n<day>: mon\t tues\t wed\t thurs\t fri\t Sat\t Sun\t";

    /**
     * This maps the commands to the method calls they respond to
     */
    private static final HashMap<String, MethodArgumentComposite> commandsToMethodCalls = new HashMap<>();

    /**
     * Default constructor for TextController designed to
     * help handle text input COMMAND from the user
     * @param model
     * @param view
     */
    public TextController(RosterModel model, TextUIView view){
        this.model = model;
        this.view = view;
        readTextInput();
    }

    /**
     * This method reads the text input from the user
     * using the System.in command line
     */
    private void readTextInput(){
        System.out.print(TERMINAL_NAME + ": ");
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()) {
            parseInput(sc);
            System.out.print(TERMINAL_NAME + ": ");
        }
    }

    /**
     * This method takes the input and takes the appropriate response
     * on the model/view
     * @param sc user command to be interpreted
     */
    private void parseInput(Scanner sc) {
        try {
            validPattern(COMMAND, COMMAND_EXCEPTION_MSG, sc);
        } catch (Exception e) {
            displayError(e.getMessage());
        }

        String in = sc.next();
        if (in.equals("exit"))
            close(sc);

        if (in.equals("clear"))
            clear();

        if (in.equals("print"))
            print();

        if (in.equals("run"))
            run();

        if (in.equals("rain")) {
            try {
                setRain(parseBoolean(sc));
            } catch (Exception e) {
                displayError(e.getMessage());
                sc.next();
            }
        }
        if (in.equals("mode")) {
            try {
                setMode(parseInteger(sc));
            } catch (Exception e) {
                displayError(e.getMessage());
                sc.next();
            }
        }
        if (in.equals("add")){
            try {
                parseAdd(sc);
            } catch (Exception e) {
                displayError(e.getMessage());
                sc.next();
            }
        }
        if (in.equals("rm")){
            try {
                parseRemove(sc);
            } catch (Exception e) {
                displayError(e.getMessage());
                sc.next();
            }
        }
        if (in.equals("ls")){
            try {
                parseList(sc);
            } catch (Exception e) {
                displayError(e.getMessage());
                sc.next();
            }
        }
        if (in.equals("swap")){
            try {
                parseSwap(sc);
            } catch (Exception e) {
                displayError(e.getMessage());
                sc.next();
            }
        }
        // TODO: 3/14/18 Implement help functions for usage once all commands
        // TODO: are finalized
    }

    /**
     * This method handles the parsing of an add command
     * from the user command line input interface
     * @param sc scanner to read input from
     * @throws Exception if it is an invalid input
     */
    private void parseAdd(Scanner sc) throws Exception{
        validPattern(OBJECTS, OBJECT_ERROR_MSG, sc);
        String in = sc.next();
        if (in.equals("staff"))
            addStaff(parseString(sc), parseBoolean(sc));
        if (in.equals("shift"))
            addShift(parseDay(sc), parseString(sc), parseString(sc), parseString(sc), parseString(sc));
    }

    /**
     * This method handles the parsing of a remove command
     * where the user wished to remove a staff member from
     * the roster
     * @param sc scanner to read from
     * @throws Exception if the input is invalid
     */
    private void parseRemove(Scanner sc) throws Exception{
        validPattern(OBJECTS, OBJECT_ERROR_MSG, sc);
        String in = sc.next();
        if (in.equals("staff")){
            if (!removeStaff(parseString(sc)))
                displayError("staff member does not exist!");
        }
        if (in.equals("shift")){
            if (!removeShift(parseDay(sc), parseString(sc)))
                displayError("shift does not exist!");
        }
    }

    /**
     * This method handles the parsing of a list
     * @param sc scanner to read the input from
     * @throws Exception if there is an invalid input
     */
    private void parseList(Scanner sc) throws Exception {
        validPattern(OBJECTS, OBJECT_ERROR_MSG, sc);
        String in = sc.next();
        if (in.equals("staff"))
            listStaff();
        if (in.equals("shift"))
            listShift(parseDay(sc));
    }

    /**
     * This method handles the parsing of swap commands
     * @param sc scanner to read the input from
     * @throws Exception if the user input is invalid
     */
    private void parseSwap(Scanner sc) throws Exception {
        validPattern(OBJECTS, OBJECT_ERROR_MSG, sc);
        String in = sc.next();
        if (in.equals("shift")){
            if (!swapShift(parseDay(sc), parseString(sc), parseDay(sc), parseString(sc)))
                throw new Exception("Shift cannot be swapped!");
        }
        if (in.equals("staff")){

        }
    }

    /**
     * This handles the parsing of days
     * @param sc scanner to read the input from
     * @return the day that was read, or null if non existent
     * @throws Exception if there was an invalid input
     */
    private RosterModel.DAY_NAMES parseDay(Scanner sc) throws Exception{
        validPattern(DAYS, DAY_ERROR_MSG, sc);
        String in = sc.next();
        switch (in){
            case "mon": return (RosterModel.DAY_NAMES.MONDAY);
            case "tue": return (RosterModel.DAY_NAMES.TUESDAY);
            case "wed": return (RosterModel.DAY_NAMES.WEDNESDAY);
            case "thu": return (RosterModel.DAY_NAMES.THURSDAY);
            case "fri": return (RosterModel.DAY_NAMES.FRIDAY);
            case "sat": return (RosterModel.DAY_NAMES.SATURDAY);
            case "sun": return (RosterModel.DAY_NAMES.SUNDAY);
            default: return null;
        }
    }

    /**
     * This method reads a boolean argument from the user, and returns
     * the value that they have selected
     * @param sc to read from
     * @return the intended boolean
     * @throws Exception if invalid input
     */
    private boolean parseBoolean(Scanner sc) throws Exception {
        validPattern(BOOLEAN, BOOLEAN_EXCEPTION_MSG, sc);
        String in = sc.next();

        // Checks all the true cases, if its not these it must be false
        return in.equals("on") || in.equals("true") || in.equals("1");
    }

    /**
     * This method handles the parsing of an integer
     * @param sc scanner to read input from
     * @return the integer if it exists
     * @throws Exception if the input is invalid
     */
    private int parseInteger(Scanner sc) throws Exception {
        validPattern(INTEGER, INTEGER_EXCEPTION_MSG, sc);
        String in = sc.next();
        return Integer.parseInt(in);
    }

    /**
     * This method handles the parsing of a string in the
     * command line user interface
     * @param sc scanner to read input from
     * @return the users string input if valid
     * @throws Exception if an invalid inputs
     */
    private String parseString(Scanner sc) throws Exception{
        validPattern(LETTERS, WORD_EXCEPTION_MSG, sc);
        return sc.next();
    }

    /**
     * This method checks whether the next input matches a specific pattern
     * @param patternToMatch the input should be part of this pattern
     * @param errorMessage to display if it does not match
     * @param userInput to check agaisnt the pattern
     * @return true if they match, false/error otherwise
     * @throws Exception if the pattern does not match or the scanner is empty
     */
    private boolean validPattern(Pattern patternToMatch, String errorMessage,Scanner userInput) throws Exception {
        if (!userInput.hasNext())
            throw new Exception("Scanner is empty!\n");
        boolean isValid = userInput.hasNext(patternToMatch);
        if (!isValid)
            throw new Exception(errorMessage);
        return isValid;
    }

    /**
     * This method closes the user text input scanner and
     * deals with some of the errors that could crop up
     * @param sc the scanner to close
     */
    private static void close(Scanner sc){
        System.exit(0);
    }

    /**
     * Displays the roster to the user on the command line
     */
    private void print(){
        this.view.print();
    }

    /**
     * Creates a new version of the roster
     */
    private void run(){
        this.model = new RandomRoster(model.getDate());
        this.view = new TextUIView(this.model);
    }

    /**
     * Clears the text user interface for text
     */
    private void clear(){
       // System.out.println(new String(new char[50]).replace("\0", "\r\n"));
        for(int i = 0; i < 80*300; i++) // Default Height of cmd is 300 and Default width is 80
            System.out.print("\b"); // Prints a backspace
    }
    /**
     * Changes the rain on the roster model
     * @param b is it raining (T/F)
     */
    private void setRain(boolean b){
        this.model.setRain(b);
    }

    /**
     * This method changes the business of the shift
     * @param i Difficulty level
     */
    private void setMode(int i){
        this.model.setDifficulty(i);
    }

    /**
     * This method adds a staff member to the roster from
     * the command line interface
     * @param name of the new staff member
     * @param isDm is a duty manager?
     */
    private void addStaff(String name, Boolean isDm){
        this.model.addStaff(new Staff(name, isDm));
        this.view.update();
    }

    /**
     * This method removes a member of staff from the roster, like
     * in the case where someone cannot work this week
     * @param name of the staff member to be removed
     */
    private boolean removeStaff(String name){
        boolean b = this.model.removeStaff(name);
        if (b)
            this.view.update();
        return b;
    }

    /**
     * This method displays all the current staff on the roster to
     * the user, so that they know who will be rostered for that week
     */
    private void listStaff(){
        for (Staff s: this.model.getStaffList()){
            System.out.print(s.getName() + "\n");
        }
        System.out.println();
    }

    /**
     * This method lists all of the shifts
     * @param d the given day
     */
    private void listShift(RosterModel.DAY_NAMES d){
        for (Shift s: this.model.getShifts(d)){
            System.out.print(s.toString());
        }
        System.out.println();
    }

    /**
     * This method adds a shift to the model
     * @param name of the employee working the shift
     * @param start the time the shift starts
     * @param end the time the shift ends
     * @param section the section of the shift
     */
    private void addShift(RosterModel.DAY_NAMES d, String name, String start, String end, String section){
        this.model.addShift(d, name, start, end, section);
        this.view.update();
    }

    /**
     * This method removes a shift from the model
     * @param d day to remove the shift from
     * @param name of the staff member who will no longer work
     * @return true if successful, false otherwise
     */
    private boolean removeShift(RosterModel.DAY_NAMES d, String name){
        boolean b = this.model.removeShift(d, name);
        if (b)
            this.view.update();
        return b;
    }

    /**
     * This method swaps two individual shifts with eachother
     * @param d1 first day to be swapped
     * @param n1 name of first staff member swapping
     * @param d2 second day to swap with
     * @param n2 name of the second staff member
     * @return true if swap was successful, false otherwise
     */
    private boolean swapShift(
            RosterModel.DAY_NAMES d1, String n1, RosterModel.DAY_NAMES d2, String n2){
            boolean b = this.model.swapShift(d1, n1, d2, n2);
            if (b)
                this.view.update();
            return b;
    }

    /**
     * This method formats an error message related to the user input
     * on the command line interface
     * @param errorMsg to be displayed
     */
    private void displayError(String errorMsg){
        System.err.print(TEXT_INPUT_ERROR_PREFACE+errorMsg);
    }
}
