import java.util.Map;

import exceptions.IllegalOperation;
import exceptions.TypeException;
import exceptions.VariableNotDeclared;
import tarea.analysis.DepthFirstAdapter;
import tarea.node.AAndCondition;
import tarea.node.AEqualsSecondCondition;
import tarea.node.AExprItem;
import tarea.node.AGreaterEqSecondCondition;
import tarea.node.AGreaterSecondCondition;
import tarea.node.ALessEqSecondCondition;
import tarea.node.ALessSecondCondition;
import tarea.node.ANotEqualsSecondCondition;
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
        node.getSecondCondition().apply(this);
        result = current && result;
    }

    @Override
    public void caseAOrCondition(AOrCondition node) {
        node.getCondition().apply(this);
        boolean current = result;
        node.getSecondCondition().apply(this);
        result = current || result;
    }

    @Override
    public void caseAEqualsSecondCondition(AEqualsSecondCondition node) {
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
    public void caseAGreaterEqSecondCondition(AGreaterEqSecondCondition node) {
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
    public void caseAGreaterSecondCondition(AGreaterSecondCondition node) {
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
    public void caseALessEqSecondCondition(ALessEqSecondCondition node) {
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
    public void caseALessSecondCondition(ALessSecondCondition node) {
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
    public void caseANotEqualsSecondCondition(ANotEqualsSecondCondition node) {
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
