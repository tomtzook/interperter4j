package testing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.tomtzook.interpreter4j.Function;
import com.tomtzook.interpreter4j.Interpreter;
import com.tomtzook.interpreter4j.NumberToken;
import com.tomtzook.interpreter4j.Program;
import com.tomtzook.interpreter4j.Token;
import com.tomtzook.interpreter4j.TokenType;
import com.tomtzook.interpreter4j.VariableToken;

public class Main {

	public static void main(String[] args) throws Exception{
		fileInterpreter();
	}
	
	private static void fileInterpreter() throws IOException{
		String file = "test.func";
		List<String> lines = Files.readAllLines(Paths.get(file));
		
		Interpreter interpreter = new Interpreter();
		interpreter.function(new Function("angledeff", 1){
			@Override
			public Token apply(Token... tokens) {
				if(tokens[0].getType() != TokenType.Number)
					throw new RuntimeException("Expected number token");
				
				NumberToken num = (NumberToken)tokens[0];
				
				System.out.println("angledeff: "+num.doubleValue());
				
				return null;
			}
		});
		interpreter.function(new Function("speed", 1){
			@Override
			public Token apply(Token... tokens) {
				if(tokens[0].getType() != TokenType.Number)
					throw new RuntimeException("Expected number token");
				
				NumberToken num = (NumberToken)tokens[0];
				
				System.out.println("speed: "+num.doubleValue());
				
				return null;
			}
		});
		interpreter.variable(new VariableToken("antAngle", NumberToken.ZERO));
		
		Program program = interpreter.evaluate(lines);
		program.variable("antAngle", new NumberToken(180.0));
		program.run();
	}
}
