package testing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.tomtzook.interpreter4j.Interpreter;
import com.tomtzook.interpreter4j.Program;

public class Main {

	public static void main(String[] args) throws Exception{
		fileInterpreter();
	}
	
	private static void fileInterpreter() throws IOException{
		String file = "test.func";
		List<String> lines = Files.readAllLines(Paths.get(file));
		
		Interpreter interpreter = new Interpreter();
		
		Program program = interpreter.evaluate(lines);
		program.run();
	}
}
