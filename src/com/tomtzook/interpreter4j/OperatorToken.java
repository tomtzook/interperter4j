package com.tomtzook.interpreter4j;

public abstract class OperatorToken extends Token{

	public static final OperatorToken ASSIGNEMENT = new OperatorToken("="){
			@Override
			public Token apply(Token left, Token right) {
				if(left.getType() != TokenType.Variable)
					operatorException("Expected left variable token", this);
				if(right.getType() == TokenType.Function)
					operatorException("Expected right number or variable token", this);
				
				VariableToken variable = (VariableToken)left;
				
				if(right.getType() == TokenType.Variable)
					variable.setValue(((VariableToken)right).getValue());
				else if(right.getType() == TokenType.Number)
					variable.setValue(right.getToken());
				
				return null;
			}
	};
	
	public static final OperatorToken ADDITION = new OperatorToken("+"){
		@Override
		public Token apply(Token left, Token right) {
			if(left.getType() != TokenType.Number || right.getType() != TokenType.Number)
				operatorException("Expected number tokens", this);
			
			Object leftval = left.getToken();
			Object rightval = right.getToken();
			
			if(leftval instanceof Double || rightval instanceof Double)
				return new NumberToken((Double)leftval + (Double)rightval);
			return new NumberToken((Integer)leftval + (Integer)rightval);
		}
	};
	public static final OperatorToken SUBTRACTION = new OperatorToken("-"){
		@Override
		public Token apply(Token left, Token right) {
			if(left.getType() != TokenType.Number || right.getType() != TokenType.Number)
				operatorException("Expected number tokens", this);
			
			Object leftval = left.getToken();
			Object rightval = right.getToken();
			
			if(leftval instanceof Double || rightval instanceof Double)
				return new NumberToken((Double)leftval - (Double)rightval);
			return new NumberToken((Integer)leftval - (Integer)rightval);
		}
	};
	public static final OperatorToken MULTIPLICATION = new OperatorToken("*"){
		@Override
		public Token apply(Token left, Token right) {
			if(left.getType() != TokenType.Number || right.getType() != TokenType.Number)
				operatorException("Expected number tokens", this);
			
			Object leftval = left.getToken();
			Object rightval = right.getToken();
			
			if(leftval instanceof Double || rightval instanceof Double)
				return new NumberToken((Double)leftval * (Double)rightval);
			return new NumberToken((Integer)leftval * (Integer)rightval);
		}
	};
	public static final OperatorToken DIVISION = new OperatorToken("/"){
		@Override
		public Token apply(Token left, Token right) {
			if(left.getType() != TokenType.Number || right.getType() != TokenType.Number)
				operatorException("Expected number tokens", this);
			
			Object leftval = left.getToken();
			Object rightval = right.getToken();
			
			if(leftval instanceof Double || rightval instanceof Double)
				return new NumberToken((Double)leftval / (Double)rightval);
			return new NumberToken((Integer)leftval / (Integer)rightval);
		}
	};
	
	public OperatorToken(Object value) {
		super(value, TokenType.Operator);
	}
	
	public abstract Token apply(Token left, Token right);
	
	private static void operatorException(String error, OperatorToken operator){
		throw new RuntimeException("Operation error ("+operator.toString()+"): "+error);
	}
}
