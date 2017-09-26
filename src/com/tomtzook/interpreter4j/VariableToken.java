package com.tomtzook.interpreter4j;

public class VariableToken extends Token{

	private Object varValue;
	
	public VariableToken(Object name) {
		super(name, TokenType.Variable);
	}

	public Object getValue(){
		return varValue;
	}
	public void setValue(Object newVal){
		varValue = newVal;
	}
}
