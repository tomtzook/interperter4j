package com.tomtzook.interpreter4j;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;

public class Program {

	private Map<Object, VariableToken> variables;
	
	private Queue<Token> tokens;
	private Token currentToken;
	
	public Program(Map<Object, VariableToken> variables, Queue<Token> tokens){
		this.tokens = tokens;
		
		this.variables = new HashMap<Object, VariableToken>();
		for (Iterator<Object> iterator = variables.keySet().iterator(); iterator.hasNext();) {
			Object key = iterator.next();
			this.variables.put(key, variables.get(key));
		}
	}
	
	private void operationError(String msg){
		throw new RuntimeException("Operation Error:" + msg);
	}
	
	private void nextOpToken(){
		currentToken = (tokens.isEmpty())? null : tokens.remove();
	}
	private boolean eatToken(TokenType type){
		return currentToken != null && currentToken.getType() == type;
	}
	private boolean eatOperator(OperatorType type){
		if(!eatToken(TokenType.Operator))
			return false;
		return ((OperatorToken)currentToken).getOperatorType() == type;
	}
	
	private Token performFactor(){
		if(eatOperator(OperatorType.Expression)){
			if(currentToken.equals(OperatorToken.ADDITION)){
				nextOpToken();
				return performFactor();
			}
			if(currentToken.equals(OperatorToken.SUBTRACTION)){
				nextOpToken();
				return OperatorToken.SUBTRACTION.apply(NumberToken.ZERO, performFactor());
			}
		}
		
		Token result = null;
		
		if(currentToken.getType() == TokenType.Number || currentToken.getType() == TokenType.Boolean){
			result = currentToken;
			nextOpToken();
		}
		else if(currentToken.getType() == TokenType.Variable){
			VariableToken variable = (VariableToken)currentToken;
			nextOpToken();
			if(currentToken != null && currentToken.getType() == TokenType.Operator && 
					currentToken.equals(OperatorToken.ASSIGNMENT)){
				nextOpToken();
				result = performExpression();
				result = OperatorToken.ASSIGNMENT.apply(variable, result);
			}else{
				result = variable.getValue();
			}
		}
		else if(eatOperator(OperatorType.Factor)){
			OperatorToken operator = (OperatorToken)currentToken;
			nextOpToken();
			result = operator.apply(result, performExpression());
		}
		else if(currentToken.getType() == TokenType.Parentheses_L){
			nextOpToken();
			result = performExpression();
			
			if(currentToken.getType() != TokenType.Parentheses_R)
				operationError("Expected parentheses closer");
			nextOpToken();
		}
		else if(currentToken.getType() == TokenType.FunctionCall){
			FunctionToken function = (FunctionToken) currentToken;
			nextOpToken();
			if(currentToken.getType() != TokenType.Parentheses_L)
				operationError("Expected parentheses");
			nextOpToken();
			
			int arguments = function.getFunction().getArgumentCount();
			Token[] args = new Token[arguments];
			int i;
			for (i = 0; i < args.length; i++) {
				Token token = performExpression();
				nextOpToken();
				if(token == null)
					break;
				args[i] = token;
			}
			if(i < args.length)
				operationError("Expected "+arguments+" arguments for function "+function.getFunction());
			
			result = function.call(args);
		}
		else if(currentToken.getType() == TokenType.Argument_Separator){
			return null;
		}
		
		return result;
	}
	private Token performTerm(){
		Token result = performFactor();
		
		OperatorToken operator;
		
		for(;;){
			if(eatOperator(OperatorType.Term)){
				operator = (OperatorToken)currentToken;
				nextOpToken();
				result = operator.apply(result, performFactor());
			}else
				return result;
		}
	}
	private Token performExpression(){
		Token result = performTerm();
		OperatorToken operator;
		
		for(;;){
			if(eatOperator(OperatorType.Expression)){
				operator = (OperatorToken)currentToken;
				nextOpToken();
				result = operator.apply(result, performTerm());
			}else
				return result;
		}
	}
	private void performOperations(){
		nextOpToken();
		
		performExpression();
		
		while(!tokens.isEmpty()){
			performExpression();
		}
	}
	
	
	public void run(){
		performOperations();
	}
}
