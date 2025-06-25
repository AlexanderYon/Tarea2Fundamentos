import java.io.FileReader;
import java.io.PushbackReader;

import tarea.lexer.Lexer;
import tarea.node.Start;
import tarea.parser.Parser;

public class Main {
    public static void main(String[] args) throws Exception{
        Parser parser = new Parser(new Lexer(new PushbackReader(new FileReader("Test.txt"), 1024)));
        Start ast = parser.parse();    
        Interpreter interpreter = new Interpreter();
        ast.apply(interpreter);
    }
}
