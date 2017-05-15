package asteroids.model.programs.expressions;

import asteroids.part3.programs.SourceLocation;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public class ReadVariableExpression extends Expression {

	public ReadVariableExpression(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	private String name;

	@Override
	public Object getValue() {
		assert (getStatement() != null);
		return getStatement().getParent().getVariable(name).getValue();
	}
	

}
