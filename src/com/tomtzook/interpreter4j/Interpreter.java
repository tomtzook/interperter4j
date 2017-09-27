package com.tomtzook.interpreter4j;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Interpreter {
	
	private Map<Object, OperatorToken> operators;
	private Map<String, Function> functions;
	private Map<Object, VariableToken> variables;
	
	private Parser parser;
	
	public Interpreter() {
		operators = createDefaultOperatorMap();
		functions = createDefaultFunctionMap();
		variables = createDefaultVariablesMap();
		
		parser = new Parser(operators, functions, variables);
	}
	
	//--------------------------------------------------------------------
	//-----------------------Evaluating-----------------------------------
	//--------------------------------------------------------------------
	
	public Program evaluate(List<String> lines){
		Queue<Token> tokens = parser.parseTokens(lines);
		
		Map<Object, VariableToken> variables = new HashMap<Object, VariableToken>();
		for (Iterator<Object> iterator = this.variables.keySet().iterator(); iterator.hasNext();) {
			Object key = iterator.next();
			variables.put(key, this.variables.get(key));
		}
		
		return new Program(variables, tokens);
	}
	
	//--------------------------------------------------------------------
	//------------------------Static--------------------------------------
	//--------------------------------------------------------------------
	
	public static Map<Object, OperatorToken> createDefaultOperatorMap(){
		Map<Object, OperatorToken> map = new HashMap<Object, OperatorToken>();
		
		map.put(OperatorToken.ASSIGNMENT.getToken(), OperatorToken.ASSIGNMENT);
		
		map.put(OperatorToken.ADDITION.getToken(), OperatorToken.ADDITION);
		map.put(OperatorToken.SUBTRACTION.getToken(), OperatorToken.SUBTRACTION);
		map.put(OperatorToken.MULTIPLICATION.getToken(), OperatorToken.MULTIPLICATION);
		map.put(OperatorToken.DIVISION.getToken(), OperatorToken.DIVISION);
		
		map.put(OperatorToken.LOGICAL_AND.getToken(), OperatorToken.LOGICAL_AND);
		map.put(OperatorToken.LOGICAL_OR.getToken(), OperatorToken.LOGICAL_OR);
		
		return map;
	}
	public static Map<String, Function> createDefaultFunctionMap(){
		Map<String, Function> map = new HashMap<String, Function>();
		
		map.put(Function.MATH_POW.getName(), Function.MATH_POW);
		map.put(Function.MATH_ABS.getName(), Function.MATH_ABS);
		map.put(Function.MATH_SQRT.getName(), Function.MATH_SQRT);
		
		map.put(Function.PRINT.getName(), Function.PRINT);
		
		return map;
	}
	public static Map<Object, VariableToken> createDefaultVariablesMap(){
		Map<Object, VariableToken> map = new HashMap<Object, VariableToken>();
		
		map.put(NumberToken.PI.getToken(), NumberToken.PI);
		map.put(NumberToken.E.getToken(), NumberToken.E);
		
		return map;
	}
}
