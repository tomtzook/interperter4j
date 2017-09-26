package com.tomtzook.interpreter4j;

public class OperatorToken extends Token{

	public static final OperatorToken ASSIGNEMENT = 
			new OperatorToken('=', OperatorType.ASSIGNMENT);
	
	public static final OperatorToken ADDITION = 
			new OperatorToken('+', OperatorType.ADDITION);
	public static final OperatorToken SUBTRACTION = 
			new OperatorToken('-', OperatorType.SUBTRACTION);
	public static final OperatorToken MULTIPLICATION = 
			new OperatorToken('*', OperatorType.MULTIPLICATION);
	public static final OperatorToken DIVISION = 
			new OperatorToken('/', OperatorType.DIVISION);
	
	private OperatorType optype;
	
	public OperatorToken(Object value, OperatorType optype) {
		super(value, TokenType.Operator);
		this.optype = optype;
	}

	public OperatorType getOperatorType(){
		return optype;
	}
}
