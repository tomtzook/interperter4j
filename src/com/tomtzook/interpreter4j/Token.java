package com.tomtzook.interpreter4j;

public class Token {

	//--------------------------------------------------------------------
	//-----------------------SYNTAX TOKENS--------------------------------
	//--------------------------------------------------------------------
	
	public static final Token PARENTHESES_L = new Token('(', TokenType.Parentheses_L);
	public static final Token PARENTHESES_R = new Token(')', TokenType.Parentheses_R);
	public static final Token BLOCK_OPEN = new Token("{", TokenType.Block_Open);
	public static final Token BLOCK_CLOSE = new Token('}', TokenType.Block_Close);
	public static final Token ARGUMENT_SEPARATOR = new Token(',', TokenType.Argument_Separator); 
	
	public static final Token UNARY_PLUS = new Token('+', TokenType.Unary_Plus);
	public static final Token UNARY_MINUS = new Token('-', TokenType.Unary_Minus);
	
	public static final Token BLOCK_CONDITION = new Token("if", TokenType.Block_Condition);
	public static final Token BLOCK_ELSE = new Token("else", TokenType.Block_Else);
	
	//--------------------------------------------------------------------
	//-----------------------CLASS DEF------------------------------------
	//--------------------------------------------------------------------
	
	private TokenType type;
	private Object value;
	
	public Token(Object value, TokenType type) {
		this.value = value;
		this.type = type;
	}
	
	public TokenType getType(){
		return type;
	}
	public Object getToken(){
		return value;
	}
	
	@Override
	public String toString() {
		return "{TOKEN: "+value.toString()+", "+type.toString()+"}";
	}
}
