package sheep.fun;

/**
 * @provided
 */
public class FunException extends Exception {
    public FunException(String message) {
        super(message);
    }

    public FunException(Exception base) {
        super(base);
    }
}
