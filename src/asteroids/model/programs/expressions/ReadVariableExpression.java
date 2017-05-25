package asteroids.model.programs.expressions;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public class ReadVariableExpression extends Expression {

	public ReadVariableExpression(String name) {
		this.name = name;
	}

	private String name;

	@Override
	public Object getValue() {
		return getStatement().getParent().getVariable(name).getValue();
	}

	@Override
	public Expression clone() {
		return new ReadVariableExpression(name);
	}
}
