package com.tomtzook.interpreter4j;

public class BooleanToken extends Token{

	public BooleanToken(boolean value) {
		super(value, TokenType.Boolean);
	}

	public boolean booleanValue(){
		return (boolean)getToken();
	}
}
