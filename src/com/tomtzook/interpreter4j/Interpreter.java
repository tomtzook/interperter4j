package com.tomtzook.interpreter4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Interpreter {

	private Map<Object, OperatorToken> operators;
	private Map<String, Function> functions;
	
	private List<Token> lineTokens;
	private Token currentToken;
	private String line;
	private int pos, lineLength;
	private StringBuilder stringBuilder;
	
	public Interpreter() {
		operators = createDefaultOperatorMap();
		functions = createDefaultFunctionMap();
		
		stringBuilder = new StringBuilder();
		lineTokens = new ArrayList<Token>();
	}
	
	private void reset(String line){
		pos = 0;
		currentToken = null;
		lineTokens.clear();
		lineLength = line.length();
		this.line = line;
	}
	
	private void parsingError(String msg){
		throw new RuntimeException("Parsing Error: "+msg);
	}
	
	
	private void eat(TokenType type){
		if(currentToken.getType() == type)
			currentToken = nextToken();
		else
			parsingError("Expected: "+type.toString());
	}
	private char peekChar(){
		if(pos >= lineLength)
			return 0;
		return line.charAt(pos);
	}
	private char nextChar(){
		char val = peekChar();
		++pos;
		return val;
	}
	
	private Token parseNumberToken(){
		char current = peekChar();
		
		if(Character.isDigit(current)){
			stringBuilder.setLength(0);
			stringBuilder.append(current);
			
			current = peekChar();
			
			while(Character.isDigit(current)){
				current = nextChar();
				stringBuilder.append(current);
			}
			
			double value = 0.0;
			try{
				value = Double.parseDouble(stringBuilder.toString());
			}catch(NumberFormatException e){
				parsingError("Expected digit: "+stringBuilder.toString());
			}
			return new NumberToken(value);
		}
		
		return null;
	}
	private Token parseOperatorToken(){
		char current = peekChar();
		
		if(operators.containsKey(current))
			return operators.get(current);
		
		return null;
	}
	
	private Token nextToken(){
		if(pos >= line.length())
			return null;
		
		Token token = null;
		
		nextChar();
		
		//is number
		token = parseNumberToken();
		if(token != null)
			return token;
		
		//is operator
		token = parseOperatorToken();
		if(token != null)
			return token;

		//unknown symbol
		parsingError("Invalid token: "+currentToken);
		return null;
	}
	
	private void parseTokens(){
		currentToken = nextToken();
		while(currentToken != null){
			
			System.out.println(currentToken);
			
			lineTokens.add(currentToken);
			currentToken = nextToken();
		}
	}
	
	public void evaluate(String line){
		reset(line);
		parseTokens();
	}
	
	
	public static Map<Object, OperatorToken> createDefaultOperatorMap(){
		Map<Object, OperatorToken> operators = new HashMap<Object, OperatorToken>();
		
		operators.put(OperatorToken.ASSIGNEMENT.getValue(), OperatorToken.ASSIGNEMENT);
		operators.put(OperatorToken.ADDITION.getValue(), OperatorToken.ADDITION);
		operators.put(OperatorToken.SUBTRACTION.getValue(), OperatorToken.SUBTRACTION);
		operators.put(OperatorToken.MULTIPLICATION.getValue(), OperatorToken.MULTIPLICATION);
		operators.put(OperatorToken.DIVISION.getValue(), OperatorToken.DIVISION);
		
		return operators;
	}
	public static Map<String, Function> createDefaultFunctionMap(){
		Map<String, Function> operators = new HashMap<String, Function>();
		
		return operators;
	}
}
