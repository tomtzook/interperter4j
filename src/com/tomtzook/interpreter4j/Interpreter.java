package com.tomtzook.interpreter4j;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Interpreter {
	
	private Map<Object, OperatorToken> operators;
	private Map<String, Function> functions;
	private Map<String, VariableToken> variables;
	
	private Parser parser;
	
	public Interpreter() {
		operators = createDefaultOperatorMap();
		functions = createDefaultFunctionMap();
		variables = createDefaultVariablesMap();
		
		parser = new Parser(operators, functions, variables);
	}
	
	//--------------------------------------------------------------------
	//-----------------------Settings-------------------------------------
	//--------------------------------------------------------------------
	
	public Interpreter variable(VariableToken variable){
		variables.put(variable.getName(), variable);
		return this;
	}
	public Interpreter variable(String name, Token value){
		variables.put(name, new VariableToken(name, value));
		return this;
	}
	public Interpreter function(Function function){
		functions.put(function.getName(), function);
		return this;
	}
	
	public Set<String> getVariableNames(){
		return variables.keySet();
	}
	public Set<String> getFunctionNames(){
		return functions.keySet();
	}
	
	//--------------------------------------------------------------------
	//-----------------------Evaluating-----------------------------------
	//--------------------------------------------------------------------
	
	public Program evaluate(List<String> lines){
		Queue<Token> tokens = parser.parseTokens(lines);
		
		Map<String, VariableToken> variables = new HashMap<String, VariableToken>();
		for (Iterator<String> iterator = this.variables.keySet().iterator(); iterator.hasNext();) {
			String key = iterator.next();
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
		map.put(OperatorToken.MODULO.getToken(), OperatorToken.MODULO);
		
		map.put(OperatorToken.LOGICAL_AND.getToken(), OperatorToken.LOGICAL_AND);
		map.put(OperatorToken.LOGICAL_OR.getToken(), OperatorToken.LOGICAL_OR);
		
		map.put(OperatorToken.EQUAL_TO.getToken(), OperatorToken.EQUAL_TO);
		map.put(OperatorToken.NOT_EQUAL_TO.getToken(), OperatorToken.NOT_EQUAL_TO);
		map.put(OperatorToken.BIGGER_THAN.getToken(), OperatorToken.BIGGER_THAN);
		map.put(OperatorToken.SMALLER_THAN.getToken(), OperatorToken.SMALLER_THAN);
		map.put(OperatorToken.BIGGER_EQUAL_TO.getToken(), OperatorToken.BIGGER_EQUAL_TO);
		map.put(OperatorToken.SMALLER_EQUAL_TO.getToken(), OperatorToken.SMALLER_EQUAL_TO);
		
		return map;
	}
	public static Map<String, Function> createDefaultFunctionMap(){
		Map<String, Function> map = new HashMap<String, Function>();
		
		map.put(Function.MATH_POW.getName(), Function.MATH_POW);
		map.put(Function.MATH_SQRT.getName(), Function.MATH_SQRT);
		map.put(Function.MATH_ROOT.getName(), Function.MATH_ROOT);
		
		map.put(Function.MATH_MIN.getName(), Function.MATH_MIN);
		map.put(Function.MATH_MAX.getName(), Function.MATH_MAX);
		
		map.put(Function.MATH_ABS.getName(), Function.MATH_ABS);
		map.put(Function.MATH_SINGNUM.getName(), Function.MATH_SINGNUM);
		map.put(Function.MATH_ROUND.getName(), Function.MATH_ROUND);
		
		map.put(Function.MATH_RAD.getName(), Function.MATH_RAD);
		map.put(Function.MATH_DEG.getName(), Function.MATH_DEG);
		
		map.put(Function.MATH_SIN.getName(), Function.MATH_SIN);
		map.put(Function.MATH_COS.getName(), Function.MATH_COS);
		map.put(Function.MATH_TAN.getName(), Function.MATH_TAN);
		map.put(Function.MATH_ASIN.getName(), Function.MATH_ASIN);
		map.put(Function.MATH_ACOS.getName(), Function.MATH_ACOS);
		map.put(Function.MATH_ATAN.getName(), Function.MATH_ATAN);
		map.put(Function.MATH_ATAN2.getName(), Function.MATH_ATAN2);
		
		map.put(Function.MATH_LN.getName(), Function.MATH_LN);
		
		map.put(Function.MATH_CLAMP.getName(), Function.MATH_CLAMP);
		map.put(Function.MATH_CLAMPED.getName(), Function.MATH_CLAMPED);
		map.put(Function.MATH_SCALE.getName(), Function.MATH_SCALE);
		
		map.put(Function.PRINT.getName(), Function.PRINT);
		map.put(Function.NOT.getName(), Function.NOT);
		
		return map;
	}
	public static Map<String, VariableToken> createDefaultVariablesMap(){
		Map<String, VariableToken> map = new HashMap<String, VariableToken>();
		
		map.put(NumberToken.PI.getName(), NumberToken.PI);
		map.put(NumberToken.E.getName(), NumberToken.E);
		
		return map;
	}
}
