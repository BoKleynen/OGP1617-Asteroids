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
//		System.out.println("read var: " + name + " from parent: " + getStatement().getParent());
		return getStatement().getParent().getVariable(name).getValue();
	}
	

}
