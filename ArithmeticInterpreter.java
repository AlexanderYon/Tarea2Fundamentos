import java.util.Map;
import java.util.Stack;

import exceptions.TypeException;
import exceptions.VariableNotDeclared;
import tarea.analysis.DepthFirstAdapter;
import tarea.node.ADivFactor;
import tarea.node.AMinusExpr;
import tarea.node.AModFactor;
import tarea.node.AMultFactor;
import tarea.node.ANumberTerm;
import tarea.node.APlusExpr;
import tarea.node.AVarTerm;
import tarea.node.PExpr;

public class ArithmeticInterpreter extends DepthFirstAdapter {
    private Map<String, Object> mapVar;
    private Stack<Object> stack = new Stack<>();
    /* Guardará el primer valor de tipo string que encuentre si es que lo hace 
     * Será util para comunicarle al Interpreter que se procesó un texto y, posiblmente el usuario
     * quiera imprimirlo.
    */
    public String conflict;

    public ArithmeticInterpreter(Map<String, Object> mapVar) {
        super();
        this.mapVar = mapVar;
    }

    public Object eval(PExpr expr){
        expr.apply(this);
        return stack.pop();
    }

    @Override
    public void caseANumberTerm(ANumberTerm node) {
        String value = node.getNumber().getText();

        // Verificar que value sea un número
        if (value.matches("^-?\\d+\\.\\d+$")){ // caso double: [0-9]+.[0-9]+
            stack.push(Double.parseDouble(value));
        }else{ // caso int: [0-9]+
            stack.push(Integer.parseInt(value));
        }
    }

    @Override
    public void caseAVarTerm(AVarTerm node) {
        String varName = node.getVar().getText();
        // Verificar que la variable exista
        if (!(mapVar.containsKey(varName))){
            throw new VariableNotDeclared("Variable '" + varName + "' has not been declared yet");
        }

        // Verificar que la variable sea del tipo correcto (número)
        Object varValue = mapVar.get(varName);
        if (!(varValue instanceof Number)){
            conflict = String.valueOf(varValue);
            throw new TypeException("Variable '" + varName + "' doesn't match a numeric type");
        }

        // Guardar el número en la pila con su tipo respectivo
        if (varValue instanceof Double){
            stack.push(((Number) varValue).doubleValue());
        }else{
            stack.push(((Number) varValue).intValue());
        }
    }

    @Override
    public void caseADivFactor(ADivFactor node) {
        super.caseADivFactor(node);
        Number right = (Number) stack.pop();
        Number left = (Number) stack.pop();

        // Se define una promoción de tipo int -> double
        if (right instanceof Double || left instanceof Double){
            stack.push(left.doubleValue() / right.doubleValue());
        }else{
            stack.push(left.intValue() / right.intValue());
        }
    }

    @Override
    public void caseAMinusExpr(AMinusExpr node) {
        super.caseAMinusExpr(node);
        Number right = (Number) stack.pop();
        Number left = (Number) stack.pop();

        // Se define una promoción de tipo int -> double
        if (right instanceof Double || left instanceof Double){
            stack.push(left.doubleValue() - right.doubleValue());
        }else{
            stack.push(left.intValue() - right.intValue());
        }
    }

    @Override
    public void caseAModFactor(AModFactor node) {
        super.caseAModFactor(node);
        Number right = (Number) stack.pop();
        Number left = (Number) stack.pop();

        // Se define una promoción de tipo int -> double
        if (right instanceof Double || left instanceof Double){
            stack.push(left.doubleValue() % right.doubleValue());
        }else{
            stack.push(left.intValue() % right.intValue());
        }
    }


    @Override
    public void caseAMultFactor(AMultFactor node) {
        super.caseAMultFactor(node);
        Number right = (Number) stack.pop();
        Number left = (Number) stack.pop();

        // Se define una promoción de tipo int -> double
        if (right instanceof Double || left instanceof Double){
            stack.push(left.doubleValue() * right.doubleValue());
        }else{
            stack.push(left.intValue() * right.intValue());
        }
    }

    @Override
    public void caseAPlusExpr(APlusExpr node) {
        super.caseAPlusExpr(node);
        Number right = (Number) stack.pop();
        Number left = (Number) stack.pop();

        // Se define una promoción de tipo int -> double
        if (right instanceof Double || left instanceof Double){
            stack.push(left.doubleValue() + right.doubleValue());
        }else{
            stack.push(left.intValue() + right.intValue());
        }
    }
}
