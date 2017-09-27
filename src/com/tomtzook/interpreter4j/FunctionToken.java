package com.tomtzook.interpreter4j;

public class FunctionToken extends Token{

	private Function function;
	
	public FunctionToken(Function function) {
		super(function.getName(), TokenType.FunctionCall);
		this.function = function;
	}
	
	public Function getFunction(){
		return function;
	}
	
	public Token call(Token...args){
		if(args.length != function.getArgumentCount())
			functionException("Expected "+function.getArgumentCount()+" arguments", this);
		
		return function.apply(args);
	}
	
	
	private static void functionException(String error, FunctionToken function){
		throw new RuntimeException("Function error ("+function.toString()+"): "+error);
	}
}
