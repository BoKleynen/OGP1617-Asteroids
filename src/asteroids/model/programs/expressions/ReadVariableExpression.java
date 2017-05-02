package asteroids.model.programs.expressions;

import asteroids.part3.programs.SourceLocation;

public class ReadVariableExpression extends Expression<Object> {

	public ReadVariableExpression(String name, SourceLocation location) {
		this.name = name;
		this.location = location;
	}
	
	public String getName() {
		return this.name;
	}
	
	private String name;
	
	public SourceLocation getLocation() {
		return this.location;
	}
	
	private SourceLocation location;
	
	@Override
	public Object getValue() {
		return getStatement().getProgram().getGlobalVariable(name).getValue();
	}
	

}
