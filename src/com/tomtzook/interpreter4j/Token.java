package com.tomtzook.interpreter4j;

public class Token {

	public static final Token PARENTHESES_L = new Token('(', TokenType.Parentheses_L);
	public static final Token PARENTHESES_R = new Token(')', TokenType.Parentheses_R);
	public static final Token BLOCK_OPEN = new Token("(", TokenType.BLOCK_OPEN);
	public static final Token BLOCK_CLOSE = new Token(')', TokenType.BLOCK_CLOSE);
	
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
