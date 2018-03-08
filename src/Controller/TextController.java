package Controller;

import View.TextUIView;

/**
 * This class is designed to capture the notion of a
 * a terminal like command interface for the user to
 * manipulate the text interface.
 */
public class TextController {

    private String input;

    /**
     * Default constructor for TextController designed to
     * help handle text input commands from the user
     * @param input
     */
    public TextController(String input){
        this.input = input;
        try {
            parseInput(input);
        } catch (Exception e) {
            System.err.print("Invalid Input >>" + input);
        }
    }

    /**
     * This method takes the input and takes the appropriate response
     * on the model/view
     * @param input user command to be interpreted
     * @throws Exception when command is invalid
     */
    public void parseInput(String input) throws Exception {
        TextUIView textUI;

        if (isValidInput(input))
            throw new Exception();
        if (input.equals("run"))
            ; // TODO: 3/8/18 run
    }

    /**
     * This method checks whether the input command from the user
     * is a valid command, garbage input or a mistake.
     * @param input
     * @return true if valid, false otherwise
     */
    public boolean isValidInput(String input){
        return true;
    }
}
