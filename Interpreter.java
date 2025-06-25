import java.io.InputStreamReader;
import java.io.PushbackReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

import exceptions.IllegalOperation;
import exceptions.TypeException;
import exceptions.VariableAlreadyDeclared;
import exceptions.VariableNotDeclared;
import tarea.analysis.DepthFirstAdapter;
import tarea.lexer.Lexer;
import tarea.node.AInitializationStatement;
import tarea.node.ADoubleInitializationDeclaration;
import tarea.node.ADecreaseVarStatement;
import tarea.node.ADoubleDeclaration;
import tarea.node.AElseFlowControl;
import tarea.node.AExprInitialization;
import tarea.node.AExprInitialization;
import tarea.node.AIfElseFlowControl;
import tarea.node.AIfFlowControl;
import tarea.node.AIncreaseVarStatement;
import tarea.node.AInputStatement;
import tarea.node.AIntInitializationDeclaration;
import tarea.node.AIntDeclaration;
import tarea.node.APrintExprStatement;
import tarea.node.APrintStringStatement;
import tarea.node.APrintlnExprStatement;
import tarea.node.APrintlnStringStatement;
import tarea.node.AStringInitialization;
import tarea.node.AStringInitializationDeclaration;
import tarea.node.AStringDeclaration;
import tarea.node.AWhileFlowControl;
import tarea.node.PInitialization;
import tarea.node.PProgram;
import tarea.node.Start;
import tarea.parser.Parser;

public class Interpreter extends DepthFirstAdapter{
    private final HashMap<String, Object> variables = new HashMap<>();
    private final Scanner sc = new Scanner(System.in);

    @Override
    public void caseADoubleDeclaration(ADoubleDeclaration node) {
        String varName = node.getVar().getText();
        if (containsVar(varName)){
            throw new VariableNotDeclared("Variable '" + varName + "' has been already declared");
        }
        variables.put(varName, 0.0);
    }

    @Override
    public void caseAIntDeclaration(AIntDeclaration node) {
        String varName = node.getVar().getText();
        if (containsVar(varName)){
            throw new VariableNotDeclared("Variable '" + varName + "' has been already declared");
        }
        variables.put(varName, 0);
    }

    @Override
    public void caseAStringDeclaration(AStringDeclaration node) {
        String varName = node.getVar().getText();
        if (containsVar(varName)){
            throw new VariableNotDeclared("Variable '" + varName + "' has been already declared");
        }
        variables.put(varName, null);
    }

    @Override
    public void caseADoubleInitializationDeclaration(ADoubleInitializationDeclaration node) {
        PInitialization init = node.getInitialization();
        /*
        Verificar si el tipo de variable concuerda con el valor asignado 
        Solo existen dos tipos de asignaciones; string_literal y expr.
        NO se puede asignar un valor string_literal a una variable declarada como double.
        */
        if (init instanceof AStringInitialization){
            throw new TypeException("Value '" + ((AStringInitialization) init).getStringLiteral().getText() + "' does not match type 'double'");
        }
        AExprInitialization parsedAssignment = (AExprInitialization) init;
        String varName = parsedAssignment.getVar().getText();
        if (containsVar(varName)){
            throw new VariableAlreadyDeclared("Variable '" + varName + "' has been already declared");
        }
        variables.put(varName, new ArithmeticInterpreter(this.variables, Double.class).eval(parsedAssignment.getExpr()));
    }
    
    @Override
    public void caseAIntInitializationDeclaration(AIntInitializationDeclaration node) {
        PInitialization init = node.getInitialization();
        /*
        Verificar si el tipo de variable concuerda con el valor asignado 
        Solo existen dos tipos de asignaciones; string_literal y expr.
        NO se puede asignar un valor string_literal a una variable declarada como int.
        */
        if (init instanceof AStringInitialization){
            throw new TypeException("Value '" + ((AStringInitialization) init).getStringLiteral().getText() + "' does not match type 'int'");
        }
        AExprInitialization parsedAssignment = (AExprInitialization) init;
        String varName = parsedAssignment.getVar().getText();
        if (containsVar(varName)){
            throw new VariableAlreadyDeclared("Variable '" + varName + "' has been already declared");
        }
        variables.put(varName, new ArithmeticInterpreter(this.variables, Integer.class).eval(parsedAssignment.getExpr()));
    }
    
    @Override
    public void caseAStringInitializationDeclaration(AStringInitializationDeclaration node) {
        PInitialization init = node.getInitialization();
        /*
        Verificar si el tipo de variable concuerda con el valor asignado 
        Solo existen dos tipos de asignaciones; string_literal y expr.
        NO se puede asignar un valor numérico (int o double) a una variable declarada como string.
        */
        if (init instanceof AExprInitialization){
            // AExprAssignmentAssignment parsedAssignment = (AExprAssignmentAssignment) assignment;
            // String varValue = parsedAssignment.getExpr();
            throw new TypeException("Cannot set a numeric value to a string variable");
        }
        AStringInitialization parsedAssignment = (AStringInitialization) init;
        String varName = parsedAssignment.getVar().getText();
        if (containsVar(varName)){
            throw new VariableAlreadyDeclared("Variable '" + varName + "' has been already declared");
        }
        variables.put(varName, parsedAssignment.getStringLiteral().getText());
    }

    @Override
    public void caseAInitializationStatement(AInitializationStatement node) {
        PInitialization init = node.getInitialization();
        String varName;
        Object varValue;
        if (init instanceof AExprInitialization){
            AExprInitialization parsedAssignment = (AExprInitialization) init;
            varName = parsedAssignment.getVar().getText();
            varValue = new ArithmeticInterpreter(variables, ((Number) variables.get(varName)).getClass()).eval(parsedAssignment.getExpr());
        }else{
            AStringInitialization parsedAssignment = (AStringInitialization) init;
            varName = parsedAssignment.getVar().getText();
            varValue = parsedAssignment.getStringLiteral().getText();
        }
        variables.put(varName, varValue);
    }

    @Override
    public void caseAPrintExprStatement(APrintExprStatement node) {
        // Intentar resolver la expresión. Si no resulta, es porque se desea imprimir un string
        ArithmeticInterpreter ai = new ArithmeticInterpreter(this.variables, Double.class);
        try{
            System.out.print(ai.eval(node.getExpr()));
        } catch (TypeException e){
            System.out.print(ai.conflict.substring(1, ai.conflict.length() - 1));
        }
    }

    @Override
    public void caseAPrintlnExprStatement(APrintlnExprStatement node) {
        // Intentar resolver la expresión. Si no resulta, es porque se desea imprimir un string
        ArithmeticInterpreter ai = new ArithmeticInterpreter(this.variables, Double.class);
        try {
            System.out.println(ai.eval(node.getExpr()));
        } catch (TypeException e) {
            System.out.println(ai.conflict.substring(1, ai.conflict.length() - 1));
        }
    }

    @Override
    public void caseAPrintStringStatement(APrintStringStatement node) {
        System.out.print(node.getStringLiteral().getText().substring(1, node.getStringLiteral().getText().length() - 1));
    }


    @Override
    public void caseAPrintlnStringStatement(APrintlnStringStatement node) {
        System.out.println(node.getStringLiteral().getText().substring(1, node.getStringLiteral().getText().length() - 1));
    }

    @Override
    public void caseAIfElseFlowControl(AIfElseFlowControl node) {
        Set<String> oldVars = new HashSet<>(variables.keySet()); // copiar variables antes de entrar al contexto
        if (new BooleanInterpreter(variables).eval(node.getCondition())){
            node.getStatement().forEach(line -> line.apply(this));
        }else{
            ((AElseFlowControl) node.getElseFlowControl()).getStatement().forEach(line -> line.apply(this));
        }
        Set<String> currentVars = new HashSet<>(variables.keySet()); // copiar variables luego de salir del contexto
        currentVars.removeAll(oldVars);

        // Eliminar las variables nuevas que se crearon
        for (String newVar : currentVars){
            variables.remove(newVar);
        }
    }

    @Override
    public void caseAIfFlowControl(AIfFlowControl node) {
        Set<String> oldVars = new HashSet<>(variables.keySet()); // copiar variables antes de entrar al contexto
        if (new BooleanInterpreter(variables).eval(node.getCondition())){
            node.getStatement().forEach(line -> line.apply(this));
        }
        Set<String> currentVars = new HashSet<>(variables.keySet()); // copiar variables luego de salir del contexto
        currentVars.removeAll(oldVars);

        // Eliminar las variables nuevas que se crearon
        for (String newVar : currentVars){
            variables.remove(newVar);
        }
    }

    @Override
    public void caseAWhileFlowControl(AWhileFlowControl node) {
        Set<String> oldVars = new HashSet<>(variables.keySet()); // copiar variables antes de entrar al contexto
        while (new BooleanInterpreter(variables).eval(node.getCondition())){
            node.getStatement().forEach(line -> line.apply(this));
            Set<String> currentVars = new HashSet<>(variables.keySet()); // copiar variables luego de salir del contexto
            currentVars.removeAll(oldVars);
    
            // Eliminar las variables nuevas que se crearon
            for (String newVar : currentVars){
                variables.remove(newVar);
            }
        }
        Set<String> currentVars = new HashSet<>(variables.keySet()); // copiar variables luego de salir del contexto
        currentVars.removeAll(oldVars);

        // Eliminar las variables nuevas que se crearon
        for (String newVar : currentVars) {
            variables.remove(newVar);
        }
    }

    @Override
    public void caseAIncreaseVarStatement(AIncreaseVarStatement node) {
        String varName = node.getVar().getText();
        if (!(variables.containsKey(varName))){
            throw new VariableNotDeclared("Variable '" + varName + "' has not been declared yet");
        }
        if (!(variables.get(varName) instanceof Number)) {
            throw new IllegalOperation("The operator '++' is undefined for type 'string'");
        }
        if (variables.get(varName) instanceof Integer){
            variables.put(varName, ((Integer) variables.get(varName)) + 1);
        }else{
            variables.put(varName, ((Double) variables.get(varName)) + 1);
        }
    }

    @Override
    public void caseADecreaseVarStatement(ADecreaseVarStatement node) {
        String varName = node.getVar().getText();
        if (!(variables.containsKey(varName))){
            throw new VariableNotDeclared("Variable '" + varName + "' has not been declared yet");
        }
        if (!(variables.get(varName) instanceof Number)) {
            throw new IllegalOperation("The operator '++' is undefined for type 'string'");
        }
        if (variables.get(varName) instanceof Integer){
            variables.put(varName, ((Integer) variables.get(varName)) - 1);
        }else{
            variables.put(varName, ((Double) variables.get(varName)) - 1);
        }
    }

    @Override
    public void caseAInputStatement(AInputStatement node) {
        String varName = node.getVar().getText();

        // Verificar que la variable existe
        if (!(variables.containsKey(varName))) {
            throw new VariableNotDeclared("Variable '" + varName + "' has not been declared yet");
        }
        
        Object currentValue = variables.get(varName);
        
        // verificar el tipo de variable para saber qué tipo de entrada se espera recibir
        boolean variableEsInt       = currentValue instanceof Integer;
        boolean variableEsString    = currentValue instanceof String;

        if (variableEsString) {
            String s = sc.nextLine().trim();
            if (!s.startsWith("\"") && !s.endsWith("\"")) {
                s = "\"" + s + "\""; // lo envolvemos como string_literal válido
            }
            variables.put(varName, s);
            return;
        }

        String entrada = sc.nextLine().trim();

        try {
            String programa;
            if (variableEsInt){
                if (entrada.matches("^-?\\d+\\.\\d+$")){ // la entrada es un double
                    throw new TypeException("Cannot set a 'double' value to an 'int' variable");
                }
                programa = "main(){int var = " + entrada + ";}"; 
            } else {
                programa = "main(){double var = " + entrada + ";}";
            }
            Parser parser = new Parser(new Lexer(new PushbackReader(new StringReader(programa), 1024)));
            Start ast = parser.parse();
            ArithmeticInterpreter interpreter = new ArithmeticInterpreter(variables, Double.class);
            ast.apply(interpreter);
            variables.put(varName, interpreter.stack.pop());
        } catch (Exception e) {
            throw new TypeException("The expression '" + entrada + "' does not match an arithmetic expression. Please check the syntaxis");
        }
    }

    private boolean containsVar(String varName) {
        return variables.containsKey(varName);
    }

    private void checkContainsVar(String varName) throws VariableNotDeclared{
        if (!containsVar(varName)){
            throw new VariableNotDeclared("Variable '" + varName + "' has not been declared yet");
        }
    }

    private Object get(String varName){
        return variables.get(varName);
    }

    // =============================================== MÉTDOS PARA DEBUG ===============================================
    public void printVariables(){
        System.out.println(variables);
    }
}