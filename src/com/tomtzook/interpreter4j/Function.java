package com.tomtzook.interpreter4j;

public abstract class Function {

	//--------------------------------------------------------------------
	//-----------------------MATH FUNCS-----------------------------------
	//--------------------------------------------------------------------
	
	public static final Function MATH_POW = new Function("pow", 2){
		@Override
		public Token apply(Token... tokens) {
			if(tokens[0].getType() != TokenType.Number || tokens[1].getType() != TokenType.Number)
				functionException("Expected number arguments", this);
				
			NumberToken base = (NumberToken)tokens[0];
			NumberToken exp = (NumberToken)tokens[0];
			
			return new NumberToken(
					Math.pow(
					base.doubleValue(),
					exp.doubleValue()));
		}
	};
	public static final Function MATH_ABS = new Function("pow", 2){
		@Override
		public Token apply(Token... tokens) {
			if(tokens[0].getType() != TokenType.Number)
				functionException("Expected number argument", this);
				
			NumberToken num = (NumberToken)tokens[0];
			
			if(num.isFloatingPoint())
				return new NumberToken(Math.abs(num.intValue()));
			return new NumberToken(Math.abs(num.doubleValue()));
		}
	};
	
	//--------------------------------------------------------------------
	//-----------------------CLASS DEF------------------------------------
	//--------------------------------------------------------------------
	
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
	
	
	protected static void functionException(String error, Function function){
		throw new RuntimeException("Function error ("+function.toString()+"): "+error);
	}
}
