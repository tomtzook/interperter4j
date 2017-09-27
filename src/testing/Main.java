package testing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import com.tomtzook.interpreter4j.Interpreter;

public class Main {

	public static void main(String[] args) throws Exception{
		fileInterpreter();
	}
	
	private static void fileInterpreter() throws IOException{
		String file = "test.func";
		List<String> lines = Files.readAllLines(Paths.get(file));
		
		Interpreter interpreter = new Interpreter();
		interpreter.evaluate(lines);
	}
	private static void lineInterpreter(){
		Scanner in = new Scanner(System.in);
		
		Interpreter interpreter = new Interpreter();
		
		String line;
		
		while (true) {
			System.out.print(">");
			line = in.nextLine();
			interpreter.evaluate(line);
		}
	}
}
