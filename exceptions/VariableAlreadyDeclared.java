package exceptions;

public class VariableAlreadyDeclared extends RuntimeException{
    public VariableAlreadyDeclared(String message) {
        super(message);
    }
}