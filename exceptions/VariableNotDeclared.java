package exceptions;

public class VariableNotDeclared extends RuntimeException {
    public VariableNotDeclared(String message) {
        super(message);
    }
}
