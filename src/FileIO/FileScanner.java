package FileIO;

import Util.Staff;

import java.io.*;

/**
 * This class captures the notion of a scanner for text files,
 * it aims to read a file storing the roster information.
 */
public class FileScanner {

    /**
     * Stores the location of where the Staff information is stored
     */
    public static final String STAFF_FILE_NAME = "/tmp/staff.ser";

    public static final String ERROR_MESSAGE_PREFACE = "FileIO Error>>";

    /**
     * This method reads an object from a given file
     * @param fileName the file to be read
     * @return the object if it exists, null otherwise
     */
    public Object readObject(String fileName){
        Object output = null;
        try {

            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            // Cast the read object as a Staff member
            output = in.readObject();
            in.close();
        }
        catch (FileNotFoundException e) {
            displayError("File does not exist!");
        }
        catch (IOException e) {
            displayError("Input Output error");
        }
        catch (ClassNotFoundException e) {
            displayError("This objects class does not exist!");
        }
        return output;
    }

    /**
     * This method write as object to a given file
     * @param ob to be written
     * @param fileName the file to be written to
     */
    public void writeObject(Object ob, String fileName){
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(ob);
            out.close();
        }
        catch (FileNotFoundException e) {
            displayError("File does not exist!");
        }
        catch (IOException e) {
            displayError("Input Output error");
        }

    }

    /**
     * This method formats the errors related to file scanning
     * @param errorMsg the error to be displayed
     */
    public void displayError(String errorMsg){
        System.err.print(ERROR_MESSAGE_PREFACE+errorMsg+"\n");
    }
}
