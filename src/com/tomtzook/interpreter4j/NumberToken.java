package com.tomtzook.interpreter4j;

public class NumberToken extends Token{

	public NumberToken(double value) {
		super(value, TokenType.Number);
	}
	public NumberToken(int value) {
		super(value, TokenType.Number);
	}
}
