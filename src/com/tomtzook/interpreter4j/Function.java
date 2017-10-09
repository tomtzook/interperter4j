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
			
			if(token.getType() == TokenType.Number || token.getType() == TokenType.Boolean ||
					token.getType() == TokenType.String)
				System.out.println("Interpreter: "+token.getToken());
			
			return null;
		}
	};
	public static final Function NOT = new Function("not", 1){
		@Override
		public Token apply(Token... tokens) {
			if(tokens[0].getType() != TokenType.Boolean)
				functionException("Expected boolean argument", this);
			
			return new BooleanToken(!(boolean)tokens[0].getToken());
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
			NumberToken exp = (NumberToken)tokens[1];
			
			return new NumberToken(
					Math.pow(
					base.doubleValue(),
					exp.doubleValue()));
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
	public static final Function MATH_ROOT = new Function("root", 2){
		@Override
		public Token apply(Token... tokens) {
			if(tokens[0].getType() != TokenType.Number || tokens[1].getType() != TokenType.Number)
				functionException("Expected number arguments", this);
				
			NumberToken num = (NumberToken)tokens[0];
			NumberToken deg = (NumberToken)tokens[1];
			
			return new NumberToken(Mathf.root(num.doubleValue(), deg.intValue()));
		}
	};
	
	
	public static final Function MATH_ABS = new Function("abs", 1){
		@Override
		public Token apply(Token... tokens) {
			if(tokens[0].getType() != TokenType.Number)
				functionException("Expected number argument", this);
				
			NumberToken num = (NumberToken)tokens[0];
			
			if(!num.isFloatingPoint())
				return new NumberToken(Math.abs(num.intValue()));
			return new NumberToken(Math.abs(num.doubleValue()));
		}
	};
	public static final Function MATH_SINGNUM = new Function("signum", 1){
		@Override
		public Token apply(Token... tokens) {
			if(tokens[0].getType() != TokenType.Number)
				functionException("Expected number argument", this);
				
			NumberToken num = (NumberToken)tokens[0];
			
			return new NumberToken(Math.signum(num.doubleValue()));
		}
	};
	public static final Function MATH_ROUND = new Function("round", 1){
		@Override
		public Token apply(Token... tokens) {
			if(tokens[0].getType() != TokenType.Number)
				functionException("Expected number argument", this);
				
			NumberToken num = (NumberToken)tokens[0];
			
			return new NumberToken(Math.round(num.doubleValue()));
		}
	};

	
	public static final Function MATH_SIN = new Function("sin", 1){
		@Override
		public Token apply(Token... tokens) {
			if(tokens[0].getType() != TokenType.Number)
				functionException("Expected number argument", this);
				
			NumberToken num = (NumberToken)tokens[0];
			
			return new NumberToken(Math.sin(Math.toRadians(num.doubleValue())));
		}
	};
	public static final Function MATH_COS = new Function("cos", 1){
		@Override
		public Token apply(Token... tokens) {
			if(tokens[0].getType() != TokenType.Number)
				functionException("Expected number argument", this);
				
			NumberToken num = (NumberToken)tokens[0];
			
			return new NumberToken(Math.cos(Math.toRadians(num.doubleValue())));
		}
	};
	public static final Function MATH_TAN = new Function("tan", 1){
		@Override
		public Token apply(Token... tokens) {
			if(tokens[0].getType() != TokenType.Number)
				functionException("Expected number argument", this);
				
			NumberToken num = (NumberToken)tokens[0];
			
			return new NumberToken(Math.tan(Math.toRadians(num.doubleValue())));
		}
	};
	public static final Function MATH_ASIN = new Function("asin", 1){
		@Override
		public Token apply(Token... tokens) {
			if(tokens[0].getType() != TokenType.Number)
				functionException("Expected number argument", this);
				
			NumberToken num = (NumberToken)tokens[0];
			
			return new NumberToken(Math.toDegrees(Math.asin(num.doubleValue())));
		}
	};
	public static final Function MATH_ACOS = new Function("acos", 1){
		@Override
		public Token apply(Token... tokens) {
			if(tokens[0].getType() != TokenType.Number)
				functionException("Expected number argument", this);
				
			NumberToken num = (NumberToken)tokens[0];
			
			return  new NumberToken(Math.toDegrees(Math.acos(num.doubleValue())));
		}
	};
	public static final Function MATH_ATAN = new Function("atan", 1){
		@Override
		public Token apply(Token... tokens) {
			if(tokens[0].getType() != TokenType.Number)
				functionException("Expected number argument", this);
				
			NumberToken num = (NumberToken)tokens[0];
			
			return new NumberToken(Math.toDegrees(Math.atan(num.doubleValue())));
		}
	};
	public static final Function MATH_ATAN2 = new Function("atan2", 2){
		@Override
		public Token apply(Token... tokens) {
			if(tokens[0].getType() != TokenType.Number || tokens[1].getType() != TokenType.Number)
				functionException("Expected number arguments", this);
				
			NumberToken y = (NumberToken)tokens[0];
			NumberToken x = (NumberToken)tokens[1];
			
			return new NumberToken(Math.toDegrees(Math.atan2(y.doubleValue(), x.doubleValue())));
		}
	};
	
	public static final Function MATH_RAD = new Function("rad", 1){
		@Override
		public Token apply(Token... tokens) {
			if(tokens[0].getType() != TokenType.Number)
				functionException("Expected number argument", this);
				
			NumberToken num = (NumberToken)tokens[0];
			
			return new NumberToken(Math.toRadians(num.doubleValue()));
		}
	};
	public static final Function MATH_DEG = new Function("deg", 1){
		@Override
		public Token apply(Token... tokens) {
			if(tokens[0].getType() != TokenType.Number)
				functionException("Expected number argument", this);
				
			NumberToken num = (NumberToken)tokens[0];
			
			return new NumberToken(Math.toDegrees(num.doubleValue()));
		}
	};
	
	public static final Function MATH_LN = new Function("ln", 1){
		@Override
		public Token apply(Token... tokens) {
			if(tokens[0].getType() != TokenType.Number)
				functionException("Expected number argument", this);
				
			NumberToken num = (NumberToken)tokens[0];
			
			return new NumberToken(Math.log(num.doubleValue()));
		}
	};
	
	public static final Function MATH_MAX = new Function("max", 2){
		@Override
		public Token apply(Token... tokens) {
			if(tokens[0].getType() != TokenType.Number || tokens[1].getType() != TokenType.Number)
				functionException("Expected number arguments", this);
				
			NumberToken num1 = (NumberToken)tokens[0];
			NumberToken num2 = (NumberToken)tokens[1];
			
			if(num1.isFloatingPoint() || num2.isFloatingPoint())
				return new NumberToken(Math.max(num1.doubleValue(), num2.doubleValue()));
			return new NumberToken(Math.max(num1.intValue(), num2.intValue()));
		}
	};
	public static final Function MATH_MIN = new Function("min", 2){
		@Override
		public Token apply(Token... tokens) {
			if(tokens[0].getType() != TokenType.Number || tokens[1].getType() != TokenType.Number)
				functionException("Expected number arguments", this);
				
			NumberToken num1 = (NumberToken)tokens[0];
			NumberToken num2 = (NumberToken)tokens[1];
			
			if(num1.isFloatingPoint() || num2.isFloatingPoint())
				return new NumberToken(Math.min(num1.doubleValue(), num2.doubleValue()));
			return new NumberToken(Math.min(num1.intValue(), num2.intValue()));
		}
	};
	
	public static final Function MATH_CLAMP = new Function("clamp", 3){
		@Override
		public Token apply(Token... tokens) {
			if(tokens[0].getType() != TokenType.Number || tokens[1].getType() != TokenType.Number
					|| tokens[2].getType() != TokenType.Number)
				functionException("Expected number arguments", this);
				
			NumberToken num = (NumberToken)tokens[0];
			NumberToken min = (NumberToken)tokens[1];
			NumberToken max = (NumberToken)tokens[2];
			
			return new NumberToken(Mathf.constrain(num.doubleValue(), min.doubleValue(), max.doubleValue()));
		}
	};
	public static final Function MATH_CLAMPED = new Function("clamped", 3){
		@Override
		public Token apply(Token... tokens) {
			if(tokens[0].getType() != TokenType.Number || tokens[1].getType() != TokenType.Number
					|| tokens[2].getType() != TokenType.Number)
				functionException("Expected number arguments", this);
				
			NumberToken num = (NumberToken)tokens[0];
			NumberToken min = (NumberToken)tokens[1];
			NumberToken max = (NumberToken)tokens[2];
			
			return new BooleanToken(Mathf.constrained(num.doubleValue(), min.doubleValue(), max.doubleValue()));
		}
	};
	public static final Function MATH_SCALE = new Function("scale", 3){
		@Override
		public Token apply(Token... tokens) {
			if(tokens[0].getType() != TokenType.Number || tokens[1].getType() != TokenType.Number
					|| tokens[2].getType() != TokenType.Number)
				functionException("Expected number arguments", this);
				
			NumberToken num = (NumberToken)tokens[0];
			NumberToken min = (NumberToken)tokens[1];
			NumberToken max = (NumberToken)tokens[2];
			
			return new NumberToken(Mathf.scale(num.doubleValue(), min.doubleValue(), max.doubleValue()));
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
