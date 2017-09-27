package com.tomtzook.interpreter4j;

public class VariableToken extends Token{

	private Token variableValue;
	
	public VariableToken(Object name, Token value) {
		super(name, TokenType.Variable);
		this.variableValue = value;
	}
	public VariableToken(Object name) {
		this(name, null);
	}

	public Token getValue(){
		return variableValue;
	}
	public void setValue(Token newVal){
		variableValue = newVal;
	}
	
	@Override
	public String toString() {
		return "{VARIABLE: "+getToken().toString()+", "+variableValue.toString()+"}";
	}
}
