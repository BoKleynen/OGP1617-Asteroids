package asteroids.model.programs.function;

import asteroids.model.Program;
import asteroids.model.programs.Child;
import asteroids.model.programs.statements.*;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public class Function implements Child<Program>{

	public Function(String functionName, Statement<CalledFunction> body) {
		this.functionName = functionName;
		setBody(body);
	}

	private String functionName;

	public String getFunctionName() {
		return functionName;
	}
	
	private void setBody(Statement<CalledFunction> body) {
		if (! body.isValidFunctionStatement())
			throw new IllegalArgumentException();

		this.body = body;
	}

	private Statement<CalledFunction> body;

	public Statement<CalledFunction> getBody() {
		return body;
	}

	private Program parent;

	@Override
	public Program getParent() {
		return parent;
	}

	@Override
	public void setParent(Program parent) {
		this.parent = parent;
	}
}
