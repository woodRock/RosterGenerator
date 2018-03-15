package Controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * This class captures the notion of storing a method and its arguments
 * such that they can be executed with a simple command and stored in
 * a collection
 */
public class MethodArgumentComposite {

    private Method methodToCall;
    private Object[] arguments;

    /**
     * This is the default constructor for this class. It will
     * @param methodName name of the method to be called
     * @param args arguments to be passed to the method
     * @param c class the method belongs to
     */
    public MethodArgumentComposite(String methodName,  Object[] args, Class<?> c){
        try {
            this.methodToCall = c.getMethod(methodName);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        this.arguments = args;
    }

    /**
     * This method will execute the method with
     * the arguments that have been stored in the
     * composite
     * @return true if successful, false otherwise
     */
    public boolean execute(){
        try {
            this.methodToCall.invoke(this.arguments);
        } catch (IllegalAccessException e) {
            return false;
        } catch (InvocationTargetException e) {
            return false;
        }
        return true;
    }
}
