package com.tomtzook.interpreter4j;

public enum TokenType {
	Boolean, Number, Operator, Variable, FunctionCall, 
	Parentheses_L, Parentheses_R, Block_Open, Block_Close,
	Argument_Separator, Unary_Plus, Unary_Minus, Block,
	Block_Condition, Block_Else
}
