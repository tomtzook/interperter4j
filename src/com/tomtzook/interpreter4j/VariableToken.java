package com.tomtzook.interpreter4j;

public class VariableToken extends Token{

	private Token variableValue;
	private boolean isfinal;
	
	public VariableToken(Object name, Token value, boolean isfinal) {
		super(name, TokenType.Variable);
		this.variableValue = value;
		this.isfinal = isfinal;
	}
	public VariableToken(Object name, Token value) {
		this(name, value, false);
	}
	public VariableToken(Object name) {
		this(name, null);
	}

	public boolean isFinal(){
		return isfinal;
	}
	public Token getValue(){
		return variableValue;
	}
	public void setValue(Token newVal){
		if(isFinal())
			assignmentException("Variable is constant", this);
		variableValue = newVal;
	}
	
	@Override
	public String toString() {
		return "{VARIABLE: "+getToken().toString()+", "+variableValue+"}";
	}
	
	private static void assignmentException(String error, VariableToken variable){
		throw new RuntimeException("Assignment error ("+variable.toString()+"): "+error);
	}
}
