package com.tomtzook.interpreter4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Interpreter {

	private Map<Object, OperatorToken> operators;
	private Map<String, Function> functions;
	private Map<String, VariableToken> variables;
	
	private List<Token> lineTokens;
	private Token currentToken;
	private String line;
	private int pos, lineLength;
	private int currentChar;
	
	public Interpreter() {
		operators = createDefaultOperatorMap();
		functions = createDefaultFunctionMap();
		variables = createDefaultVariablesMap();
		
		lineTokens = new ArrayList<Token>();
	}
	
	//--------------------------------------------------------------------
	//--------------------Parsing - General-------------------------------
	//--------------------------------------------------------------------
	
	private void reset(String line){
		pos = 0;
		currentToken = null;
		lineTokens.clear();
		lineLength = line.length();
		currentChar = line.charAt(0);
		this.line = line;
	}
	
	private void parsingError(String msg){
		throw new RuntimeException("Parsing Error("+pos+"): "+msg);
	}
	
	private void nextChar(){
		currentChar = (++pos < lineLength) ? line.charAt(pos) : -1;
	}
	private void skipspaces(){
		while(Character.isSpaceChar(currentChar))
			nextChar();
	}
		
	//--------------------------------------------------------------------
	//---------------------Parsing - Tokens-------------------------------
	//--------------------------------------------------------------------
	
	private Token nextToken(){
		if(pos >= line.length())
			return null;
		
		int start;
		
		while(currentChar != 0){
			start = pos;
			
			//skip whitespaces
			if(Character.isWhitespace(currentChar)){
				skipspaces();
				continue;
			}
			
			//is number
			if(Character.isDigit(currentChar) || currentChar == '.'){
	            while(Character.isDigit(currentChar) || currentChar == '.') 
	            	nextChar();
	            double value = Double.parseDouble(line.substring(start, this.pos));
	            return new NumberToken(value);
			}
			
			//is operator
			int chartype = Character.getType(currentChar);
			if(chartype == Character.MATH_SYMBOL || chartype == Character.OTHER_PUNCTUATION || 
					chartype == Character.DASH_PUNCTUATION){
				while(chartype == Character.MATH_SYMBOL || chartype == Character.OTHER_PUNCTUATION || 
						chartype == Character.DASH_PUNCTUATION){
					nextChar();
					chartype = Character.getType(currentChar);
				}
				
				String strval = line.substring(start, pos);
				if(operators.containsKey(strval))
					return operators.get(strval);
			}
			
			//unknown symbol
			parsingError("Unexpected character: "+currentChar);
		}

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
	
	//--------------------------------------------------------------------
	//-------------------Parsing - Operations-----------------------------
	//--------------------------------------------------------------------
	
	public void performOperations(){
		
	}
	
	//--------------------------------------------------------------------
	//-----------------------Evaluating-----------------------------------
	//--------------------------------------------------------------------
	
	public void evaluate(String line){
		reset(line);
		parseTokens();
		performOperations();
	}
	
	//--------------------------------------------------------------------
	//------------------------Static--------------------------------------
	//--------------------------------------------------------------------
	
	public static Map<Object, OperatorToken> createDefaultOperatorMap(){
		Map<Object, OperatorToken> map = new HashMap<Object, OperatorToken>();
		
		map.put(OperatorToken.ASSIGNEMENT.getToken(), OperatorToken.ASSIGNEMENT);
		map.put(OperatorToken.ADDITION.getToken(), OperatorToken.ADDITION);
		map.put(OperatorToken.SUBTRACTION.getToken(), OperatorToken.SUBTRACTION);
		map.put(OperatorToken.MULTIPLICATION.getToken(), OperatorToken.MULTIPLICATION);
		map.put(OperatorToken.DIVISION.getToken(), OperatorToken.DIVISION);
		
		return map;
	}
	public static Map<String, Function> createDefaultFunctionMap(){
		Map<String, Function> map = new HashMap<String, Function>();
		
		return map;
	}
	public static Map<String, VariableToken> createDefaultVariablesMap(){
		Map<String, VariableToken> map = new HashMap<String, VariableToken>();
		
		return map;
	}
}
