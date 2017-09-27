package com.tomtzook.interpreter4j;

public class NumberToken extends Token{

	private boolean floatingpoint;
	
	public NumberToken(double value) {
		super(value, TokenType.Number);
		floatingpoint = true;
	}
	public NumberToken(int value) {
		super(value, TokenType.Number);
		floatingpoint = false;
	}
	
	public boolean isFloatingPoint(){
		return floatingpoint;
	}
	
	public int intValue(){
		if(!isFloatingPoint())
			return (int)getToken();
		return ((Double)getToken()).intValue();
	}
	public double doubleValue(){
		if(isFloatingPoint())
			return (double)getToken();
		return ((Integer)getToken()).doubleValue();
	}
}
