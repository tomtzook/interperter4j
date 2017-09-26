package com.tomtzook.interpreter4j;

public class OperatorType {
	
	public static final int OPERATOR_ASSIGNMENT = 0;
	public static final int OPERATOR_ADDITION = 1;
	public static final int OPERATOR_SUBTRACTION = 2;
	public static final int OPERATOR_MULTIPLICATION = 3;
	public static final int OPERATOR_DIVISION = 4;
	
	private int value;
	
	public OperatorType(int value){
		this.value = value;
	}
	
	public int getValue(){
		return value;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof OperatorType)
			return ((OperatorType)obj).value == this.value;
		return super.equals(obj);
	}
	
	public static final OperatorType ASSIGNMENT =
			new OperatorType(OPERATOR_ASSIGNMENT);
	
	public static final OperatorType ADDITION =
			new OperatorType(OPERATOR_ADDITION);
	public static final OperatorType SUBTRACTION =
			new OperatorType(OPERATOR_SUBTRACTION);
	public static final OperatorType MULTIPLICATION =
			new OperatorType(OPERATOR_MULTIPLICATION);
	public static final OperatorType DIVISION =
			new OperatorType(OPERATOR_DIVISION);
}
