package com.tomtzook.interpreter4j;

public class StringToken extends Token{

	public StringToken(String value) {
		super(value, TokenType.String);
	}

}
