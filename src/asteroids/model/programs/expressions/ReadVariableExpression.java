package asteroids.model.programs.expressions;

import asteroids.part3.programs.SourceLocation;

public class ReadVariableExpression extends Expression<Object> {

	public ReadVariableExpression(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	private String name;

	private SourceLocation location;
	
	@Override
	public Object getValue() {
		return getStatement().getProgram().getGlobalVariable(name).getValue();
	}
	

}
