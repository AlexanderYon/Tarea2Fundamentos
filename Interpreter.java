import java.util.HashMap;
import java.util.Scanner;

import exceptions.TypeException;
import exceptions.VariableAlreadyDeclared;
import exceptions.VariableNotDeclared;
import tarea.analysis.DepthFirstAdapter;
import tarea.node.AAssignmentLine;
import tarea.node.ADoubleAssignmentDeclaration;
import tarea.node.ADoubleDeclaration;
import tarea.node.AElseStatement;
import tarea.node.AExprAssignmentAssignment;
import tarea.node.AIfElseFlowControl;
import tarea.node.AIfFlowControl;
import tarea.node.AInputLine;
import tarea.node.AIntAssignmentDeclaration;
import tarea.node.AIntDeclaration;
import tarea.node.APrintExprLine;
import tarea.node.APrintStringLine;
import tarea.node.APrintlnExprLine;
import tarea.node.APrintlnStringLine;
import tarea.node.AStringAssignmentAssignment;
import tarea.node.AStringAssignmentDeclaration;
import tarea.node.AStringDeclaration;
import tarea.node.AWhileFlowControl;
import tarea.node.PAssignment;

public class Interpreter extends DepthFirstAdapter{
    private final HashMap<String, Object> mapVar = new HashMap<>();

    @Override
    public void caseADoubleDeclaration(ADoubleDeclaration node) {
        String varName = node.getVar().getText();
        if (mapVar.containsKey(varName)){
            throw new VariableAlreadyDeclared("Variable '" + varName + "' has already been declared");
        }
        mapVar.put(varName, 0);
    }

    @Override
    public void caseAIntDeclaration(AIntDeclaration node) {
        String varName = node.getVar().getText();
        if (mapVar.containsKey(varName)){
            throw new VariableAlreadyDeclared("Variable '" + varName + "' has already been declared");
        }
        mapVar.put(varName, 0);
    }

    @Override
    public void caseAStringDeclaration(AStringDeclaration node) {
        String varName = node.getVar().getText();
        System.out.println(varName);
        if (mapVar.containsKey(varName)){
            throw new VariableAlreadyDeclared("Variable '" + varName + "' has already been declared");
        }
        mapVar.put(varName, null);
    }

    @Override
    public void caseADoubleAssignmentDeclaration(ADoubleAssignmentDeclaration node) {
        PAssignment assignment = node.getAssignment();
        /*
        Verificar si el tipo de variable concuerda con el valor asignado 
        Solo existen dos tipos de asignaciones; string_literal y expr.
        NO se puede asignar un valor string_literal a una variable declarada como double.
        */
        if (assignment instanceof AStringAssignmentAssignment){
            AStringAssignmentAssignment parsedAssignment = (AStringAssignmentAssignment) assignment;
            String varValue = parsedAssignment.getStringLiteral().getText();
            throw new TypeException("Value '" + varValue + "' doesn't match with type 'double'");
        }
        AExprAssignmentAssignment parsedAssignment = (AExprAssignmentAssignment) assignment;
        String varName = parsedAssignment.getVar().getText();
        if (mapVar.containsKey(varName)){
            throw new VariableAlreadyDeclared("Variable '" + varName + "' has already been declared");
        }
        mapVar.put(varName, new ArithmeticInterpreter(this.mapVar).eval(parsedAssignment.getExpr()));
    }
    
    @Override
    public void caseAIntAssignmentDeclaration(AIntAssignmentDeclaration node) {
        PAssignment assignment = node.getAssignment();
        /*
        Verificar si el tipo de variable concuerda con el valor asignado 
        Solo existen dos tipos de asignaciones; string_literal y expr.
        NO se puede asignar un valor string_literal a una variable declarada como double.
        */
        if (assignment instanceof AStringAssignmentAssignment){
            AStringAssignmentAssignment parsedAssignment = (AStringAssignmentAssignment) assignment;
            String varValue = parsedAssignment.getStringLiteral().getText();
            throw new TypeException("Value '" + varValue + "' doesn't match with type 'double'");
        }
        AExprAssignmentAssignment parsedAssignment = (AExprAssignmentAssignment) assignment;
        String varName = parsedAssignment.getVar().getText();
        if (mapVar.containsKey(varName)){
            throw new VariableAlreadyDeclared("Variable '" + varName + "' has already been declared");
        }
        mapVar.put(varName, new ArithmeticInterpreter(this.mapVar).eval(parsedAssignment.getExpr()));
    }
    
    @Override
    public void caseAStringAssignmentDeclaration(AStringAssignmentDeclaration node) {
        PAssignment assignment = node.getAssignment();
        /*
        Verificar si el tipo de variable concuerda con el valor asignado 
        Solo existen dos tipos de asignaciones; string_literal y expr.
        NO se puede asignar un valor string_literal a una variable declarada como double.
        */
        if (assignment instanceof AExprAssignmentAssignment){
            // AExprAssignmentAssignment parsedAssignment = (AExprAssignmentAssignment) assignment;
            // String varValue = parsedAssignment.getExpr();
            throw new TypeException("Cannot set a numeric value to a string variable");
        }
        AStringAssignmentAssignment parsedAssignment = (AStringAssignmentAssignment) assignment;
        String varName = parsedAssignment.getVar().getText();
        if (mapVar.containsKey(varName)){
            throw new VariableAlreadyDeclared("Variable '" + varName + "' has already been declared");
        }
        mapVar.put(varName, parsedAssignment.getStringLiteral().getText());
    }

    @Override
    public void caseAAssignmentLine(AAssignmentLine node) {
        PAssignment assignment = node.getAssignment();
        String varName;
        Object varValue;
        if (assignment instanceof AExprAssignmentAssignment){
            AExprAssignmentAssignment parsedAssignment = (AExprAssignmentAssignment) assignment;
            varName = parsedAssignment.getVar().getText();
            varValue = new ArithmeticInterpreter(mapVar).eval(parsedAssignment.getExpr());
        }else{
            AStringAssignmentAssignment parsedAssignment = (AStringAssignmentAssignment) assignment;
            varName = parsedAssignment.getVar().getText();
            varValue = parsedAssignment.getStringLiteral().getText();
        }
        mapVar.put(varName, varValue);
    }

    @Override
    public void caseAPrintExprLine(APrintExprLine node) {
        // Intentar resolver la expresión. Sino, es porque se desea imprimir un string
        ArithmeticInterpreter ai = new ArithmeticInterpreter(this.mapVar);
        try{
            System.out.print(ai.eval(node.getExpr()));
        } catch (TypeException e){
            System.out.print(ai.conflict.substring(1, ai.conflict.length() - 1));
        }
    }

    @Override
    public void caseAPrintlnExprLine(APrintlnExprLine node) {
        // Intentar resolver la expresión. Sino, es porque se desea imprimir un string
        ArithmeticInterpreter ai = new ArithmeticInterpreter(this.mapVar);
        try {
            System.out.println(ai.eval(node.getExpr()));
        } catch (TypeException e) {
            System.out.println(ai.conflict.substring(1, ai.conflict.length() - 1));
        }
    }

    @Override
    public void caseAPrintStringLine(APrintStringLine node) {
        System.out.print(node.getStringLiteral().getText().substring(1, node.getStringLiteral().getText().length() - 1));
    }


    @Override
    public void caseAPrintlnStringLine(APrintlnStringLine node) {
        System.out.println(node.getStringLiteral().getText().substring(1, node.getStringLiteral().getText().length() - 1));
    }

    @Override
    public void caseAIfElseFlowControl(AIfElseFlowControl node) {
        if (new BooleanInterpreter(mapVar).eval(node.getCondition())){
            node.getLine().forEach(line -> line.apply(this));
        }else{
            ((AElseStatement) node.getElseStatement()).getLine().forEach(line -> line.apply(this));
        }
    }

    @Override
    public void caseAIfFlowControl(AIfFlowControl node) {
        if (new BooleanInterpreter(mapVar).eval(node.getCondition())){
            node.getLine().forEach(line -> line.apply(this));
        }
    }

    @Override
    public void caseAWhileFlowControl(AWhileFlowControl node) {
        while (new BooleanInterpreter(mapVar).eval(node.getCondition())){
            node.getLine().forEach(line -> line.apply(this));
        }
    }

    @Override
    public void caseAInputLine(AInputLine node) {
        String varName = node.getVar().getText();
        if (!(mapVar.containsKey(varName))){
            throw new VariableNotDeclared("Variable '" + varName + "' has not been declared yet");
        }
        Scanner sc = new Scanner(System.in);
        String value = sc.nextLine();
        sc.close();

        try {
            if (value.matches("-?\\d+")){ // caso int
                int parsedValue = Integer.parseInt(value);
                if (mapVar.get(varName) instanceof Integer) {
                    mapVar.put(varName, parsedValue);
                } else if (mapVar.get(varName) instanceof Double) {
                    mapVar.put(varName, (double) parsedValue);
                } else {
                    throw new TypeException("Cannot set a 'int' value to a 'string' variable");
                }
            } else {
                double parsedValue = Double.parseDouble(value);
                if (mapVar.get(varName) instanceof Double) {
                    mapVar.put(varName, parsedValue);
                } else if (mapVar.get(varName) instanceof Integer) {
                    throw new TypeException("Cannot set a 'double' value to an 'int' variable");
                } else {
                    throw new TypeException("Cannot set a 'double' value to a 'string' variable");
                }
            }
        } catch (NumberFormatException e) {
            if (!(mapVar.get(varName) instanceof String)) {
                throw new TypeException("Cannot set a 'string' value to a numeric variable");
            }
            mapVar.put(varName, value);
        }
    }

    // ================================== MÉTDOS PARA DEBUG ==================================
    public void printVariables(){
        System.out.println(mapVar);
    }
}