package asteroids.model.programs.function;

import asteroids.model.Program;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.statements.Statement;
import be.kuleuven.cs.som.annotate.Raw;

import java.util.HashMap;
import java.util.Map;

public class Function {

	public Function(String functionName, Statement body) {
		this.name = functionName;
		this.body = body;
	}
	
	public String getName() {
		return this.name;
	}
	
	private String name;
	
	public Statement getBody() {
		return this.body;
	}

	private Statement body;

	private Map<String, Expression> localVariables = new HashMap<>();

	private Program program;

	public Program getProgram() {
		return program;
	}

	@Raw
	public void setProgram(Program program) {
		this.program = program;
	}

	public Expression getVariable(String variableName) {
		if (localVariables.containsKey(variableName))
			return localVariables.get(variableName);

		else
			return getProgram().readVariable(variableName);
	}
}
