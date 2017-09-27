package com.tomtzook.interpreter4j;

public class NumberToken extends Token{

	//--------------------------------------------------------------------
	//-----------------------MATH CONST-----------------------------------
	//--------------------------------------------------------------------
	
	public static final VariableToken PI = new VariableToken("pi", new NumberToken(Math.PI), true);
	public static final VariableToken E = new VariableToken("e", new NumberToken(Math.E), true);
	
	public static final NumberToken ZERO = new NumberToken(0);
	
	//--------------------------------------------------------------------
	//-----------------------CLASS DEF------------------------------------
	//--------------------------------------------------------------------
	
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
