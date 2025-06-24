import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import exceptions.IllegalOperation;
import exceptions.TypeException;
import exceptions.VariableNotDeclared;
import tarea.analysis.DepthFirstAdapter;
import tarea.node.AAndCondition;
import tarea.node.AEqualsOther;
import tarea.node.AExprItem;
import tarea.node.AGreaterEqOther;
import tarea.node.AGreaterOther;
import tarea.node.ALessEqOther;
import tarea.node.ALessOther;
import tarea.node.ANotEqualsOther;
import tarea.node.AOrCondition;
import tarea.node.AStringItem;
import tarea.node.PCondition;
import tarea.node.PItem;

public class BooleanInterpreter extends DepthFirstAdapter{
    private final Stack<HashMap<String, Object>> varScope;
    private boolean result;

    public BooleanInterpreter(Stack<HashMap<String, Object>> mapVar) {
        super();
        this.varScope = mapVar;
    }

    public boolean eval(PCondition condition){
        condition.apply(this);
        return result;
    }

    @Override
    public void caseAAndCondition(AAndCondition node) {
        node.getCondition().apply(this);
        boolean current = result;
        node.getOther().apply(this);
        result = current && result;
    }

    @Override
    public void caseAOrCondition(AOrCondition node) {
        node.getCondition().apply(this);
        boolean current = result;
        node.getOther().apply(this);
        result = current || result;
    }

    @Override
    public void caseAEqualsOther(AEqualsOther node) {
        String varName = node.getVar().getText();
        
        // Verificar que existe la variable
        if (!(varScope.stream().anyMatch(map -> map.containsKey(varName)))){
            throw new VariableNotDeclared("Variable '" + varName + "' has not been declared yet");
        }

        // Verificar si corresponde al tipo adecuado que se está comparando
        PItem item = node.getItem();

        // Caso item es una expresión aritmética
        Object value = varScope.stream().filter(map -> map.containsKey(varName)).findFirst().get().get(varName);
        if (item instanceof AExprItem) {
            if (!(value instanceof Number)){
                throw new TypeException("Variable '" + varName + "' does not match a numeric type");
            }
            result = ((Number) value).doubleValue() == ((Number) new ArithmeticInterpreter(varScope, Double.class).eval(((AExprItem) item).getExpr())).doubleValue();
    
        // Caso item es un string
        } else {
            if (!(value instanceof String)){
                throw new TypeException("Variable '" + varName + "' does not match string type");
            }
            result = value.equals(((AStringItem) item).getStringLiteral().getText());
        }
    }

    @Override
    public void caseAGreaterEqOther(AGreaterEqOther node) {
        String varName = node.getVar().getText();

        // Verificar que existe la variable
        if (!(varScope.stream().anyMatch(map -> map.containsKey(varName)))){
            throw new VariableNotDeclared("Variable '" + varName + "' has not been declared yet");
        }

        // Verificar si corresponde al tipo adecuado que se está comparando
        PItem item = node.getItem();
        Object value = varScope.stream().filter(map -> map.containsKey(varName)).findFirst().get().get(varName);
        if (item instanceof AStringItem || value instanceof String) {
            throw new IllegalOperation(" The operator '>=' is undefined for type 'string' ");
        }
        result = ((Number) value).doubleValue() >= ((Number) new ArithmeticInterpreter(varScope, Double.class).eval(((AExprItem) item).getExpr())).doubleValue();
    }

    @Override
    public void caseAGreaterOther(AGreaterOther node) {
        String varName = node.getVar().getText();

        // Verificar que existe la variable
        if (!(varScope.stream().anyMatch(map -> map.containsKey(varName)))){
            throw new VariableNotDeclared("Variable '" + varName + "' has not been declared yet");
        }

        // Verificar si corresponde al tipo adecuado que se está comparando
        PItem item = node.getItem();
        Object value = varScope.stream().filter(map -> map.containsKey(varName)).findFirst().get().get(varName);
        if (item instanceof AStringItem || value instanceof String) {
            throw new IllegalOperation(" The operator '>' is undefined for type 'string' ");
        }
        result = ((Number) value).doubleValue() > ((Number) new ArithmeticInterpreter(varScope, Double.class).eval(((AExprItem) item).getExpr())).doubleValue();
    }

    @Override
    public void caseALessEqOther(ALessEqOther node) {
        String varName = node.getVar().getText();

        // Verificar que existe la variable
        if (!(varScope.stream().anyMatch(map -> map.containsKey(varName)))){
            throw new VariableNotDeclared("Variable '" + varName + "' has not been declared yet");
        }

        // Verificar si corresponde al tipo adecuado que se está comparando
        PItem item = node.getItem();
        Object value = varScope.stream().filter(map -> map.containsKey(varName)).findFirst().get().get(varName);
        if (item instanceof AStringItem || value instanceof String) {
            throw new IllegalOperation(" The operator '<=' is undefined for type 'string' ");
        }
        result = ((Number) value).doubleValue() <= ((Number) new ArithmeticInterpreter(varScope, Double.class).eval(((AExprItem) item).getExpr())).doubleValue();
    }

    @Override
    public void caseALessOther(ALessOther node) {
        String varName = node.getVar().getText();

        // Verificar que existe la variable
        if (!(varScope.stream().anyMatch(map -> map.containsKey(varName)))){
            throw new VariableNotDeclared("Variable '" + varName + "' has not been declared yet");
        }

        // Verificar si corresponde al tipo adecuado que se está comparando
        PItem item = node.getItem();
        Object value = varScope.stream().filter(map -> map.containsKey(varName)).findFirst().get().get(varName);
        if (item instanceof AStringItem || value instanceof String) {
            throw new IllegalOperation(" The operator '<' is undefined for type 'string' ");
        }
        result = ((Number) value).doubleValue() < ((Number) new ArithmeticInterpreter(varScope, Double.class).eval(((AExprItem) item).getExpr())).doubleValue();
    }

    @Override
    public void caseANotEqualsOther(ANotEqualsOther node) {
        String varName = node.getVar().getText();
        
        // Verificar que existe la variable
        if (!(varScope.stream().anyMatch(map -> map.containsKey(varName)))){
            throw new VariableNotDeclared("Variable '" + varName + "' has not been declared yet");
        }

        // Verificar si corresponde al tipo adecuado que se está comparando
        PItem item = node.getItem();
        Object value = varScope.stream().filter(map -> map.containsKey(varName)).findFirst().get().get(varName);

        // Caso item es una expresión aritmética
        if (item instanceof AExprItem) {
            if (!(value instanceof Number)){
                throw new TypeException("Variable '" + varName + "' does not match a numeric type");
            }
            result = value != new ArithmeticInterpreter(varScope, Double.class).eval(((AExprItem) item).getExpr());
    
        // Caso item es un string
        } else {
            if (!(value instanceof String)){
                throw new TypeException("Variable '" + varName + "' does not match string type");
            }
            result = !value.equals(((AStringItem) item).getStringLiteral().getText());
        }
    }
}
