package com.tomtzook.interpreter4j;

public class BlockToken extends Token{

	private Token[] tokens;
	
	public BlockToken(Token[] tokens) {
		super(null, TokenType.Block);
		this.tokens = tokens;
	}
	
	public Token[] getTokens(){
		return tokens;
	}
	
	@Override
	public String toString() {
		return "{BLOCK: "+tokens.length+"}";
	}
}
