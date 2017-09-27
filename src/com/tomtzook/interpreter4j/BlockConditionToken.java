package com.tomtzook.interpreter4j;

public class BlockConditionToken extends Token{

	private BlockToken block;
	
	public BlockConditionToken(String value, BlockToken block) {
		super(value, TokenType.Block_Condition);
		
		this.block = block;
	}
	
	public BlockToken getBlock(){
		return block;
	}
}
