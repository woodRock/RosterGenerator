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

    //Patterns for the inputs that are allowed in the user input
    private static final Pattern COMMAND = Pattern.compile("run|exit|print|rain|mode|clear|add|rm|ls|swap|help");
    private static final Pattern BOOLEAN = Pattern.compile("on|off|true|false|0|1");
    private static final Pattern OBJECTS = Pattern.compile("staff|roster|shift");
    private Pattern INTEGER = Pattern.compile("[0-9]*");
    private Pattern DAYS = Pattern.compile("mon|tue|wed|thur|fri|sat|sun");
    private Pattern LETTERS = Pattern.compile("[a-zA-Z0-9]+");

    //Exception messages for the various syntax errors the user may encounter
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

    private static final HashMap<String, MethodArgumentComposite> commandsToMethodCalls = new HashMap<>();

    public TextController(RosterModel model, TextUIView view){
        this.model = model;
        this.view = view;
        readTextInput();
    }

    private void readTextInput(){
        System.out.print(TERMINAL_NAME + ": ");
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()) {
            parseInput(sc);
            System.out.print(TERMINAL_NAME + ": ");
        }
    }

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

    private void parseAdd(Scanner sc) throws Exception{
        validPattern(OBJECTS, OBJECT_ERROR_MSG, sc);
        String in = sc.next();
        if (in.equals("staff"))
            addStaff(parseString(sc), parseBoolean(sc));
        if (in.equals("shift"))
            addShift(parseDay(sc), parseString(sc), parseString(sc), parseString(sc), parseString(sc));
    }

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

    private void parseList(Scanner sc) throws Exception {
        validPattern(OBJECTS, OBJECT_ERROR_MSG, sc);
        String in = sc.next();
        if (in.equals("staff"))
            listStaff();
        if (in.equals("shift"))
            listShift(parseDay(sc));
    }

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

    private boolean parseBoolean(Scanner sc) throws Exception {
        validPattern(BOOLEAN, BOOLEAN_EXCEPTION_MSG, sc);
        String in = sc.next();

        // Checks all the true cases, if its not these it must be false
        return in.equals("on") || in.equals("true") || in.equals("1");
    }

    private int parseInteger(Scanner sc) throws Exception {
        validPattern(INTEGER, INTEGER_EXCEPTION_MSG, sc);
        String in = sc.next();
        return Integer.parseInt(in);
    }

    private String parseString(Scanner sc) throws Exception{
        validPattern(LETTERS, WORD_EXCEPTION_MSG, sc);
        return sc.next();
    }

    private boolean validPattern(Pattern patternToMatch, String errorMessage,Scanner userInput) throws Exception {
        if (!userInput.hasNext())
            throw new Exception("Scanner is empty!\n");
        boolean isValid = userInput.hasNext(patternToMatch);
        if (!isValid)
            throw new Exception(errorMessage);
        return isValid;
    }

    private static void close(Scanner sc){
        System.exit(0);
    }

    private void print(){
        this.view.print();
    }

    private void run(){
        this.model = new RandomRoster(model.getDate());
        this.view = new TextUIView(this.model);
    }

    private void clear(){
       // System.out.println(new String(new char[50]).replace("\0", "\r\n"));
        for(int i = 0; i < 80*300; i++) // Default Height of cmd is 300 and Default width is 80
            System.out.print("\b"); // Prints a backspace
    }

    private void setRain(boolean b){
        this.model.setRain(b);
    }

    private void setMode(int i){
        this.model.setDifficulty(i);
    }

    private void addStaff(String name, Boolean isDm){
        this.model.addStaff(new Staff(name, isDm));
        this.view.update();
    }

    private boolean removeStaff(String name){
        boolean b = this.model.removeStaff(name);
        if (b)
            this.view.update();
        return b;
    }

    private void listStaff(){
        for (Staff s: this.model.getStaffList()){
            System.out.print(s.getName() + "\n");
        }
        System.out.println();
    }

    private void listShift(RosterModel.DAY_NAMES d){
        for (Shift s: this.model.getShifts(d)){
            System.out.print(s.toString());
        }
        System.out.println();
    }

    private void addShift(RosterModel.DAY_NAMES d, String name, String start, String end, String section){
        this.model.addShift(d, name, start, end, section);
        this.view.update();
    }

    private boolean removeShift(RosterModel.DAY_NAMES d, String name){
        boolean b = this.model.removeShift(d, name);
        if (b)
            this.view.update();
        return b;
    }

    private boolean swapShift(
            RosterModel.DAY_NAMES d1, String n1, RosterModel.DAY_NAMES d2, String n2){
            boolean b = this.model.swapShift(d1, n1, d2, n2);
            if (b)
                this.view.update();
            return b;
    }

    private void displayError(String errorMsg){
        System.err.print(TEXT_INPUT_ERROR_PREFACE+errorMsg);
    }
}
