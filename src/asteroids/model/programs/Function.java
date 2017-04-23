package asteroids.model.programs;

import asteroids.part3.programs.SourceLocation;

public class Function {

	public Function(String functionName, Statement body, SourceLocation sourceLocation) {
		this.name = functionName;
		this.body = body;
		this.sourceLocation = sourceLocation;
	}
	
	public String getName() {
		return this.name;
	}
	
	private String name;
	
	public Statement getBody() {
		return this.body;
	}

	private Statement body;
	
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
	
	private SourceLocation sourceLocation;
}
