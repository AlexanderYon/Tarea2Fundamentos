import java.util.Map;

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
    private final Map<String, Object> mapVar;
    private boolean result;

    public BooleanInterpreter(Map<String, Object> mapVar) {
        super();
        this.mapVar = mapVar;
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
        if (!(mapVar.containsKey(varName))){
            throw new VariableNotDeclared("Variable '" + varName + "' has not been declared yet");
        }

        // Verificar si corresponde al tipo adecuado que se está comparando
        PItem item = node.getItem();

        // Caso item es una expresión aritmética
        if (item instanceof AExprItem) {
            if (!(mapVar.get(varName) instanceof Number)){
                throw new TypeException("Variable '" + varName + "' does not match a numeric type");
            }
            result = ((Number) mapVar.get(varName)).doubleValue() == ((Number) new ArithmeticInterpreter(mapVar).eval(((AExprItem) item).getExpr())).doubleValue();
    
        // Caso item es un string
        } else {
            if (!(mapVar.get(varName) instanceof String)){
                throw new TypeException("Variable '" + varName + "' does not match string type");
            }
            result = mapVar.get(varName).equals(((AStringItem) item).getStringLiteral().getText());
        }
    }

    @Override
    public void caseAGreaterEqOther(AGreaterEqOther node) {
        String varName = node.getVar().getText();

        // Verificar que existe la variable
        if (!(mapVar.containsKey(varName))){
            throw new VariableNotDeclared("Variable '" + varName + "' has not been declared yet");
        }

        // Verificar si corresponde al tipo adecuado que se está comparando
        PItem item = node.getItem();

        if (item instanceof AStringItem || mapVar.get(varName) instanceof String) {
            throw new IllegalOperation(" The operator '>=' is undefined for type 'string' ");
        }
        result = ((Number) mapVar.get(varName)).doubleValue() >= ((Number) new ArithmeticInterpreter(mapVar).eval(((AExprItem) item).getExpr())).doubleValue();
    }

    @Override
    public void caseAGreaterOther(AGreaterOther node) {
        String varName = node.getVar().getText();

        // Verificar que existe la variable
        if (!(mapVar.containsKey(varName))){
            throw new VariableNotDeclared("Variable '" + varName + "' has not been declared yet");
        }

        // Verificar si corresponde al tipo adecuado que se está comparando
        PItem item = node.getItem();

        if (item instanceof AStringItem || mapVar.get(varName) instanceof String) {
            throw new IllegalOperation(" The operator '>' is undefined for type 'string' ");
        }
        result = ((Number) mapVar.get(varName)).doubleValue() > ((Number) new ArithmeticInterpreter(mapVar).eval(((AExprItem) item).getExpr())).doubleValue();
    }

    @Override
    public void caseALessEqOther(ALessEqOther node) {
        String varName = node.getVar().getText();

        // Verificar que existe la variable
        if (!(mapVar.containsKey(varName))){
            throw new VariableNotDeclared("Variable '" + varName + "' has not been declared yet");
        }

        // Verificar si corresponde al tipo adecuado que se está comparando
        PItem item = node.getItem();

        if (item instanceof AStringItem || mapVar.get(varName) instanceof String) {
            throw new IllegalOperation(" The operator '<=' is undefined for type 'string' ");
        }
        result = ((Number) mapVar.get(varName)).doubleValue() <= ((Number) new ArithmeticInterpreter(mapVar).eval(((AExprItem) item).getExpr())).doubleValue();
    }

    @Override
    public void caseALessOther(ALessOther node) {
        String varName = node.getVar().getText();

        // Verificar que existe la variable
        if (!(mapVar.containsKey(varName))){
            throw new VariableNotDeclared("Variable '" + varName + "' has not been declared yet");
        }

        // Verificar si corresponde al tipo adecuado que se está comparando
        PItem item = node.getItem();

        if (item instanceof AStringItem || mapVar.get(varName) instanceof String) {
            throw new IllegalOperation(" The operator '<' is undefined for type 'string' ");
        }
        result = ((Number) mapVar.get(varName)).doubleValue() < ((Number) new ArithmeticInterpreter(mapVar).eval(((AExprItem) item).getExpr())).doubleValue();
    }

    @Override
    public void caseANotEqualsOther(ANotEqualsOther node) {
        String varName = node.getVar().getText();
        
        // Verificar que existe la variable
        if (!(mapVar.containsKey(varName))){
            throw new VariableNotDeclared("Variable '" + varName + "' has not been declared yet");
        }

        // Verificar si corresponde al tipo adecuado que se está comparando
        PItem item = node.getItem();

        // Caso item es una expresión aritmética
        if (item instanceof AExprItem) {
            if (!(mapVar.get(varName) instanceof Number)){
                throw new TypeException("Variable '" + varName + "' does not match a numeric type");
            }
            result = mapVar.get(varName) != new ArithmeticInterpreter(mapVar).eval(((AExprItem) item).getExpr());
    
        // Caso item es un string
        } else {
            if (!(mapVar.get(varName) instanceof String)){
                throw new TypeException("Variable '" + varName + "' does not match string type");
            }
            result = !mapVar.get(varName).equals(((AStringItem) item).getStringLiteral().getText());
        }
    }
}
