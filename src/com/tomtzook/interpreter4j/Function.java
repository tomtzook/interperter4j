package com.tomtzook.interpreter4j;

public abstract class Function {

	private String name;
	private int argCount;
	
	public Function(String name, int argCount) {
		this.name = name;
		this.argCount = argCount;
	}
	
	public String getName(){
		return name;
	}
	public int getArgumentCount(){
		return argCount;
	}
	
	public abstract Token apply(Token...tokens);
}
