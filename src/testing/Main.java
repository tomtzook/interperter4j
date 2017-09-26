package testing;

import java.util.Scanner;

import com.tomtzook.interpreter4j.Interpreter;

public class Main {

	public static void main(String[] args){
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
