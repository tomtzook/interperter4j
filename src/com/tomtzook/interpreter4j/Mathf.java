package com.tomtzook.interpreter4j;

public class Mathf {
	private Mathf(){}
	
	
	private static final double ROOT_DIFFERENCE = 1e-8;
	
	//--------------------------------------------------------------------
	//--------------------------General-----------------------------------
	//--------------------------------------------------------------------
	
	public static double avg(double...ds){
		double res = 0;
		for (int i = 0; i < ds.length; i++) 
			res += ds[i];
		return res / ds.length;
	}
	public static boolean validDouble(double d){
		return !Double.isNaN(d) && Double.isFinite(d);
	}
	public static double translateAngle(double value){
		value %= 360;
		if(value < 0)
			value += 360;
		return value;
	}
	public static double constrain(double value, double min, double max){
		if(value > max) value = max;
		else if(value < min) value = min;
		return value;
	}
	public static double constrain2(double value, double min, double max){
		double mag = Math.abs(value);
		if(mag > max) mag = max;
		else if(mag < min) mag = min;
		return value >= 0? mag : -mag;
	}
	public static boolean constrained(double value, double min, double max){
		return value >= min && value <= max;
	}
	
	public static double scale(double value, double min, double max){
		return (value - min) / (max - min);
	}
	
	public static double roundDecimal(double x){
		return roundDecimal(x, 2);
	}
	public static double roundDecimal(double x, int decimalNums){
		double m = Math.pow(10, decimalNums);
		return  Math.round(x * m) / m;
	}
	public static double roundToMultiplier(double val, double multiplier){
		return multiplier * Math.round(val / multiplier);
	}
	public static double roundToMultiplier(double val, double multiplier, boolean up){
		double rounded = roundToMultiplier(val, multiplier);
		if(rounded < val)
			rounded += up? multiplier : -multiplier;
		return rounded;
	}
	
	public static double pythagorasTheorem(double a, double b, double c){
		return Math.sqrt((a * a) + (b * b) + (c * c));
	}
	public static double pythagorasTheorem(double a, double b){
		return pythagorasTheorem(a, b, 0);
	}
	public static double reversePythagorasTheorem(double a, double c){
		return Math.sqrt((c * c) - (a * a));
	}

	public static double root(double result, int degree){
		if(result < 0)
			throw new IllegalArgumentException("Cannot calculate negative root!");
        if(result == 0) 
            return 0;
        
        double x1 = result;
        double x2 = result / degree;  
        while (Math.abs(x1 - x2) > ROOT_DIFFERENCE){
            x1 = x2;
            x2 = ((degree - 1.0) * x2 + result / Math.pow(x2, degree - 1.0)) / degree;
        }
        return x2;
	}
}
