import java.util.HashMap;

import tarea.analysis.DepthFirstAdapter;

public class Interpreter extends DepthFirstAdapter{
    private final HashMap<String, Object> mapVar = new HashMap<>();
    private static final BooleanInterpreter BOOLEAN_INTERPRETER = new BooleanInterpreter();
    private static final ArithmeticInterpreter ARITHMETIC_INTERPRETER = new ArithmeticInterpreter();
    

}
