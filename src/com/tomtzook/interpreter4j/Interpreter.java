package com.tomtzook.interpreter4j;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Interpreter {
	
	private Map<Object, OperatorToken> operators;
	private Map<String, Function> functions;
	private Map<String, VariableToken> variables;
	
	private Queue<Token> lineTokens;
	private Token currentToken;
	private Token lastToken;
	private String line;
	private int pos, lineLength;
	private int currentChar;
	
	public Interpreter() {
		operators = createDefaultOperatorMap();
		functions = createDefaultFunctionMap();
		variables = createDefaultVariablesMap();
		
		lineTokens = new ArrayDeque<Token>();
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
	            String strval = line.substring(start, this.pos);
	            
	            if(strval.contains(".")){
		            double value = Double.parseDouble(strval);
		            return new NumberToken(value);
	            }else{
		            int value = Integer.parseInt(strval);
		            return new NumberToken(value);
	            }
			}
			
			//is parentheses
			if(currentChar == '('){
				nextChar();
				return new Token('(', TokenType.Parentheses_L);
			}
			if(currentChar == ')'){
				nextChar();
				return new Token(')', TokenType.Parentheses_R);
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
			
			//is a string: function, variable, boolean
			if(Character.isAlphabetic(currentChar) || currentChar == '_'){
	            while(Character.isAlphabetic(currentChar) || currentChar == '_') 
	            	nextChar();
	            String val = line.substring(start, this.pos);
	            
	            //checking for boolean tokens
	            if(val.equals("true")){
	            	return new BooleanToken(true);
	            }else if(val.equals("false")){
	            	return new BooleanToken(false);
	            }
	            
	            //is a function: we have parentheses
	            if(currentChar == '('){
	            	if(!functions.containsKey(val))
	            		parsingError("Unknown function: "+val);
	            }
	            
	            //is a variable
	            if(!variables.containsKey(val)){
	            	variables.put(val, new VariableToken(val));
	            }
	            return variables.get(val);
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
	
	private void operationError(String msg){
		throw new RuntimeException("Operation Error:" + msg);
	}
	
	public void nextOpToken(){
		lastToken = currentToken;
		currentToken = (lineTokens.isEmpty())? null : lineTokens.remove();
	}
	public boolean eatToken(TokenType type){
		return currentToken != null && currentToken.getType() == type;
	}
	public boolean eatOperator(OperatorType type){
		if(!eatToken(TokenType.Operator))
			return false;
		return ((OperatorToken)currentToken).getOperatorType() == type;
	}
	
	public Token performFactor(){
		Token result = null;
		
		if(currentToken.getType() == TokenType.Number || currentToken.getType() == TokenType.Boolean){
			result = currentToken;
			nextOpToken();
		}
		else if(currentToken.getType() == TokenType.Variable){
			VariableToken variable = (VariableToken)currentToken;
			nextOpToken();
			if(currentToken != null && currentToken.getType() == TokenType.Operator && 
					currentToken.equals(OperatorToken.ASSIGNMENT)){
				nextOpToken();
				result = performExpression();
				result = OperatorToken.ASSIGNMENT.apply(variable, result);
			}else{
				result = variable.getValue();
			}
		}
		else if(eatOperator(OperatorType.Factor)){
			OperatorToken operator = (OperatorToken)currentToken;
			nextOpToken();
			result = operator.apply(result, performExpression());
		}
		else if(currentToken.getType() == TokenType.Parentheses_L){
			nextOpToken();
			result = performExpression();
			
			if(currentToken.getType() != TokenType.Parentheses_R)
				operationError("Expected parentheses closer");
			nextOpToken();
		}
		
		return result;
	}
	public Token performTerm(){
		Token result = performFactor();
		
		OperatorToken operator;
		
		for(;;){
			if(eatOperator(OperatorType.Term)){
				operator = (OperatorToken)currentToken;
				nextOpToken();
				result = operator.apply(result, performFactor());
			}else
				return result;
		}
	}
	public Token performExpression(){
		Token result = performTerm();
		OperatorToken operator;
		
		for(;;){
			if(eatOperator(OperatorType.Expression)){
				operator = (OperatorToken)currentToken;
				nextOpToken();
				result = operator.apply(result, performTerm());
			}else
				return result;
		}
	}
	public void performOperations(){
		nextOpToken();
		Token token = performExpression();
		
		if(!lineTokens.isEmpty())
			operationError("Unexpected tokens left");
		
		if(token != null)
			System.out.println(token);
	}
	
	//--------------------------------------------------------------------
	//-----------------------Evaluating-----------------------------------
	//--------------------------------------------------------------------
	
	public void evaluate(String line){
		reset(line);
		System.out.println("Parsing:");
		parseTokens();
		System.out.println("Operations:");
		performOperations();
	}
	
	//--------------------------------------------------------------------
	//------------------------Static--------------------------------------
	//--------------------------------------------------------------------
	
	public static Map<Object, OperatorToken> createDefaultOperatorMap(){
		Map<Object, OperatorToken> map = new HashMap<Object, OperatorToken>();
		
		map.put(OperatorToken.ASSIGNMENT.getToken(), OperatorToken.ASSIGNMENT);
		
		map.put(OperatorToken.ADDITION.getToken(), OperatorToken.ADDITION);
		map.put(OperatorToken.SUBTRACTION.getToken(), OperatorToken.SUBTRACTION);
		map.put(OperatorToken.MULTIPLICATION.getToken(), OperatorToken.MULTIPLICATION);
		map.put(OperatorToken.DIVISION.getToken(), OperatorToken.DIVISION);
		
		map.put(OperatorToken.LOGICAL_AND.getToken(), OperatorToken.LOGICAL_AND);
		map.put(OperatorToken.LOGICAL_OR.getToken(), OperatorToken.LOGICAL_OR);
		
		return map;
	}
	public static Map<String, Function> createDefaultFunctionMap(){
		Map<String, Function> map = new HashMap<String, Function>();
		
		return map;
	}
	public static Map<String, VariableToken> createDefaultVariablesMap(){
		Map<String, VariableToken> map = new HashMap<String, VariableToken>();
		
		map.put("pi", new VariableToken("test", new NumberToken(Math.PI)));
		map.put("test", new VariableToken("test", new NumberToken(1)));
		
		return map;
	}
}
