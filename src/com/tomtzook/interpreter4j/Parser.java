package com.tomtzook.interpreter4j;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Parser {

	private Map<Object, OperatorToken> operators;
	private Map<String, Function> functions;
	private Map<Object, VariableToken> variables;
	
	private List<String> lines;
	private String line;
	private int currentLine;
	private int pos, lineLength;
	private int currentChar;
	
	public Parser(Map<Object, OperatorToken> operators, Map<String, Function> functions,
			Map<Object, VariableToken> variables) {
		this.operators = operators;
		this.functions = functions;
		this.variables = variables;
	}
	
	
	private void reset(List<String> lines){
		currentLine = 0;
		this.lines = lines;
		
		reset(lines.get(currentLine));
	}
	private void reset(String line){
		pos = 0;
		lineLength = line.length();
		currentChar = line.charAt(0);
		this.line = line;
	}
	
	private void parsingError(String msg){
		throw new RuntimeException("Parsing Error("+pos+"): "+msg);
	}
	
	private void nextChar(){
		currentChar = (++pos < lineLength) ? line.charAt(pos) : 0;
	}
	private void nextLine(){
		String line = (++currentLine < lines.size())? lines.get(currentLine) : null;
		if(line != null)
			reset(line);
	}
	private void skipspaces(){
		while(Character.isSpaceChar(currentChar))
			nextChar();
	}
	
	private Token nextToken(){
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
				return Token.PARENTHESES_L;
			}
			if(currentChar == ')'){
				nextChar();
				return Token.PARENTHESES_R;
			}
			
			//is separator
			if(currentChar == ','){
				nextChar();
				return Token.ARGUMENT_SEPARATOR;
			}
			
			//is block
			if(currentChar == '{'){
				nextChar();
				
				List<Token> inblock = new ArrayList<Token>();
				
				Token token = nextToken();
				while (token != null && token.getToken() != TokenType.Block_Close) {
					inblock.add(token);
					token = nextToken();
				}
				
				if(token == null)
					parsingError("Missing block closer");
				
				return new BlockToken((Token[]) inblock.toArray());
			}
			if(currentChar == '}'){
				nextChar();
				return Token.BLOCK_CLOSE;
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
	            	return new FunctionToken(functions.get(val));
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
	
	
	public Queue<Token> parseTokens(List<String> lines){
		reset(lines);
		
		Queue<Token> tokens = new ArrayDeque<Token>();
		Token currentToken = null;
		
		while(currentLine < lines.size()){
			currentToken = nextToken();
			while(currentToken != null){
				
				//System.out.println(currentToken);
				
				tokens.add(currentToken);
				currentToken = nextToken();
			}
			nextLine();
		}
		
		return tokens;
	}
}
