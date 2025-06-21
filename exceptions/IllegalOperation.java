package exceptions;

public class IllegalOperation extends RuntimeException{
    public IllegalOperation(String message) {
        super(message);
    }
}