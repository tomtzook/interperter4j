package com.tomtzook.interpreter4j;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;

public class Program {

	private Map<Object, VariableToken> programVariables;
	private Queue<Token> programTokens;
	
	private Map<Object, VariableToken> variables;
	
	private Queue<Token> tokens;
	private Token currentToken;
	
	public Program(Map<Object, VariableToken> variables, Queue<Token> tokens){
		this.tokens = new ArrayDeque<Token>();
		this.programTokens = tokens;
		
		this.variables = new HashMap<Object, VariableToken>();
		this.programVariables = variables;
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
	
	private void performBlock(BlockToken block){
		Token[] blocktokens = block.getTokens();
		Queue<Token> tokens = new ArrayDeque<Token>(blocktokens.length);
		for (int i = 0; i < blocktokens.length; i++)
			tokens.add(blocktokens[i]);
		Program program = new Program(variables, tokens);
		program.run();
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
		else if(currentToken.getType() == TokenType.Block){
			BlockToken block = (BlockToken)currentToken;
			performBlock(block);
			nextOpToken();
		}
		else if(currentToken.getType() == TokenType.Block_Condition){
			nextOpToken();
			Token token = performExpression();
			
			if(token == null || token.getType() != TokenType.Boolean)
				operationError("Expected boolean condition");
			
			boolean condition = (boolean)token.getToken();
			
			if(!eatToken(TokenType.Block))
				operationError("Expected code block");
			
			if(condition){
				performBlock((BlockToken) currentToken);
			}
			nextOpToken();
			
			if(eatToken(TokenType.Block_Else)){
				nextOpToken();
				
				if(!eatToken(TokenType.Block))
					operationError("Expected code block");
				
				if(!condition){
					performBlock((BlockToken) currentToken);
				}
				nextOpToken();
			}
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
	
	//--------------------------------------------------------------------
	//-----------------------Settings-------------------------------------
	//--------------------------------------------------------------------
	
	public Program variable(String name, Token value){
		if(programVariables.containsKey(name)){
			programVariables.get(name).setValue(value);
		}
		return this;
	}
	
	//--------------------------------------------------------------------
	//-----------------------Evaluating-----------------------------------
	//--------------------------------------------------------------------
	
	public void run(){
		tokens.clear();
		tokens.addAll(programTokens);
		
		variables.clear();
		for (Iterator<Object> iterator = programVariables.keySet().iterator(); iterator.hasNext();) {
			Object key = iterator.next();
			this.variables.put(key, programVariables.get(key));
		}
		
		performOperations();
	}
}
