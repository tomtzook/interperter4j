package com.tomtzook.interpreter4j;

public abstract class Function {

	//--------------------------------------------------------------------
	//-----------------------GENERAL FUNCS--------------------------------
	//--------------------------------------------------------------------
	
	public static final Function PRINT = new Function("print", 1){
		@Override
		public Token apply(Token... tokens) {
			Token token = tokens[0];
			if(token.getType() == TokenType.Variable)
				token = ((VariableToken)token).getValue();
			
			if(token.getType() == TokenType.Number || token.getType() == TokenType.Boolean)
				System.out.println("Interpreter: "+token.getToken());
			
			return null;
		}
	};
	
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
	public static final Function MATH_ABS = new Function("abs", 1){
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
	public static final Function MATH_SQRT = new Function("sqrt", 1){
		@Override
		public Token apply(Token... tokens) {
			if(tokens[0].getType() != TokenType.Number)
				functionException("Expected number argument", this);
				
			NumberToken num = (NumberToken)tokens[0];
			
			return new NumberToken(Math.sqrt(num.doubleValue()));
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
