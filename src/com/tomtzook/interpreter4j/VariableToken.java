package com.tomtzook.interpreter4j;

public class VariableToken extends Token{

	private boolean isfinal;
	private Token value;
	
	public VariableToken(Object name, Token value, boolean isfinal) {
		super(name, TokenType.Variable);
		this.isfinal = isfinal;
		this.value = value;
	}
	public VariableToken(Object name, Token value) {
		this(name, value, false);
	}
	public VariableToken(Object name) {
		this(name, null, false);
	}

	public String getName() {
		return (String)getToken();
	}
	
	public boolean isFinal(){
		return isfinal;
	}
	
	public Token getValue(){
		return value;
	}
	public void setValue(Token newVal){
		if(isFinal())
			assignmentException("Variable is constant", this);
		value = newVal;
	}
	
	@Override
	public String toString() {
		return "{VARIABLE: "+getToken().toString()+","+value+"}";
	}
	
	private static void assignmentException(String error, VariableToken variable){
		throw new RuntimeException("Assignment error ("+variable.toString()+"): "+error);
	}
}
