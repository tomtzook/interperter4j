package com.tomtzook.interpreter4j;

public class Token {

	private TokenType type;
	private Object value;
	
	public Token(Object value, TokenType type) {
		this.value = value;
		this.type = type;
	}
	
	public TokenType getType(){
		return type;
	}
	public Object getValue(){
		return value;
	}
	
	@Override
	public String toString() {
		return "{TOKEN: "+value.toString()+", "+type.toString()+"}";
	}
}
