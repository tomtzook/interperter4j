package com.tomtzook.interpreter4j;

public abstract class OperatorToken extends Token{
	
	//--------------------------------------------------------------------
	//-----------------------ASSIGNMENT-----------------------------------
	//--------------------------------------------------------------------
	
	public static final OperatorToken ASSIGNMENT = new OperatorToken("=", OperatorType.Expression){
		@Override
		public Token apply(Token left, Token right) {
			if(left.getType() != TokenType.Variable)
				operatorException("Expected left hand variable token", this);
			
			if(right.getType() == TokenType.Variable)
				((VariableToken)left).setValue(((VariableToken)right).getValue());
			else
				((VariableToken)left).setValue(right);
			
			return left;
		}
	};
	
	//--------------------------------------------------------------------
	//-----------------------ARITHMETIC-----------------------------------
	//--------------------------------------------------------------------
	
	public static final OperatorToken ADDITION = new OperatorToken("+", OperatorType.Expression){
		@Override
		public Token apply(Token left, Token right) {
			if(left.getType() != TokenType.Number || right.getType() != TokenType.Number)
				operatorException("Expected number tokens", this);
			
			NumberToken numl = (NumberToken)left;
			NumberToken numr = (NumberToken)right;
			
			if(numl.isFloatingPoint() || numr.isFloatingPoint())
				return new NumberToken(numl.doubleValue() + numr.doubleValue());
			return new NumberToken(numl.intValue() + numr.intValue());
		}
	};
	public static final OperatorToken SUBTRACTION = new OperatorToken("-", OperatorType.Expression){
		@Override
		public Token apply(Token left, Token right) {
			if(left.getType() != TokenType.Number || right.getType() != TokenType.Number)
				operatorException("Expected number tokens", this);
			
			NumberToken numl = (NumberToken)left;
			NumberToken numr = (NumberToken)right;
			
			if(numl.isFloatingPoint() || numr.isFloatingPoint())
				return new NumberToken(numl.doubleValue() - numr.doubleValue());
			return new NumberToken(numl.intValue() - numr.intValue());
		}
	};
	public static final OperatorToken MULTIPLICATION = new OperatorToken("*", OperatorType.Term){
		@Override
		public Token apply(Token left, Token right) {
			if(left.getType() != TokenType.Number || right.getType() != TokenType.Number)
				operatorException("Expected number tokens", this);
			
			NumberToken numl = (NumberToken)left;
			NumberToken numr = (NumberToken)right;
			
			if(numl.isFloatingPoint() || numr.isFloatingPoint())
				return new NumberToken(numl.doubleValue() * numr.doubleValue());
			return new NumberToken(numl.intValue() * numr.intValue());
		}
	};
	public static final OperatorToken DIVISION = new OperatorToken("/", OperatorType.Term){
		@Override
		public Token apply(Token left, Token right) {
			if(left.getType() != TokenType.Number || right.getType() != TokenType.Number)
				operatorException("Expected number tokens", this);
			
			NumberToken numl = (NumberToken)left;
			NumberToken numr = (NumberToken)right;
			
			if(numl.isFloatingPoint() || numr.isFloatingPoint())
				return new NumberToken(numl.doubleValue() / numr.doubleValue());
			return new NumberToken(numl.intValue() / numr.intValue());
		}
	};
	
	//--------------------------------------------------------------------
	//--------------------------LOGICAL-----------------------------------
	//--------------------------------------------------------------------
	
	public static final OperatorToken LOGICAL_OR = new OperatorToken("||", OperatorType.Expression){
		@Override
		public Token apply(Token left, Token right) {
			if(left.getType() != TokenType.Boolean || right.getType() != TokenType.Boolean)
				operatorException("Expected boolean tokens", this);
			
			boolean leftval = (boolean)left.getToken();
			boolean rightval = (boolean)right.getToken();
			
			return new BooleanToken(leftval || rightval);
		}
	};
	public static final OperatorToken LOGICAL_AND = new OperatorToken("&&", OperatorType.Expression){
		@Override
		public Token apply(Token left, Token right) {
			if(left.getType() != TokenType.Boolean || right.getType() != TokenType.Boolean)
				operatorException("Expected boolean tokens", this);
			
			boolean leftval = (boolean)left.getToken();
			boolean rightval = (boolean)right.getToken();
			
			return new BooleanToken(leftval && rightval);
		}
	};
	
	
	public static final OperatorToken EQUAL_TO = new OperatorToken("==", OperatorType.Term){
		@Override
		public Token apply(Token left, Token right) {
			if(left.getType() != TokenType.Number || right.getType() != TokenType.Number)
				operatorException("Expected number tokens", this);
			
			NumberToken numl = (NumberToken)left;
			NumberToken numr = (NumberToken)right;
			
			return new BooleanToken(numl.doubleValue() == numr.doubleValue());
		}
	};
	public static final OperatorToken NOT_EQUAL_TO = new OperatorToken("!=", OperatorType.Term){
		@Override
		public Token apply(Token left, Token right) {
			if(left.getType() != TokenType.Number || right.getType() != TokenType.Number)
				operatorException("Expected number tokens", this);
			
			NumberToken numl = (NumberToken)left;
			NumberToken numr = (NumberToken)right;
			
			return new BooleanToken(numl.doubleValue() != numr.doubleValue());
		}
	};
	public static final OperatorToken BIGGER_THAN = new OperatorToken(">", OperatorType.Term){
		@Override
		public Token apply(Token left, Token right) {
			if(left.getType() != TokenType.Number || right.getType() != TokenType.Number)
				operatorException("Expected number tokens", this);
			
			NumberToken numl = (NumberToken)left;
			NumberToken numr = (NumberToken)right;
			
			return new BooleanToken(numl.doubleValue() > numr.doubleValue());
		}
	};
	public static final OperatorToken SMALLER_THAN = new OperatorToken("<", OperatorType.Term){
		@Override
		public Token apply(Token left, Token right) {
			if(left.getType() != TokenType.Number || right.getType() != TokenType.Number)
				operatorException("Expected number tokens", this);
			
			NumberToken numl = (NumberToken)left;
			NumberToken numr = (NumberToken)right;
			
			return new BooleanToken(numl.doubleValue() < numr.doubleValue());
		}
	};
	
	public static final OperatorToken BIGGER_EQUAL_TO = new OperatorToken(">=", OperatorType.Term){
		@Override
		public Token apply(Token left, Token right) {
			if(left.getType() != TokenType.Number || right.getType() != TokenType.Number)
				operatorException("Expected number tokens", this);
			
			NumberToken numl = (NumberToken)left;
			NumberToken numr = (NumberToken)right;
			
			return new BooleanToken(numl.doubleValue() >= numr.doubleValue());
		}
	};
	public static final OperatorToken SMALLER_EQUAL_TO = new OperatorToken("<=", OperatorType.Term){
		@Override
		public Token apply(Token left, Token right) {
			if(left.getType() != TokenType.Number || right.getType() != TokenType.Number)
				operatorException("Expected number tokens", this);
			
			NumberToken numl = (NumberToken)left;
			NumberToken numr = (NumberToken)right;
			
			return new BooleanToken(numl.doubleValue() <= numr.doubleValue());
		}
	};
	
	//--------------------------------------------------------------------
	//-----------------------CLASS DEF------------------------------------
	//--------------------------------------------------------------------
	
	private OperatorType optype;
	
	public OperatorToken(Object value, OperatorType type) {
		super(value, TokenType.Operator);
		this.optype = type;
	}
	
	public OperatorType getOperatorType(){
		return optype;
	}
	
	public abstract Token apply(Token left, Token right);
	
	private static void operatorException(String error, OperatorToken operator){
		throw new RuntimeException("Operation error ("+operator.toString()+"): "+error);
	}
}
