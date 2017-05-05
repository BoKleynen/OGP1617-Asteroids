package asteroids.model.programs.function;

import asteroids.model.Program;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.statements.Statement;
import be.kuleuven.cs.som.annotate.Raw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Function {

	public Function(String functionName, Statement body) {
		this.functionName = functionName;
		setBody(body);
	}

	private String functionName;

	public String getFunctionName() {
		return functionName;
	}
	
	private void setBody(Statement body) {
		this.body = body;
	}

	private Statement body;

	private Map<String, Expression> localVariables;

	public Expression getLocalVariable(String variableName) {
		if (localVariables.containsKey(variableName))
			return localVariables.get(variableName);

		else
			return getProgram().readGlobalVariable(variableName);
	}

	private Program program;

	public void setProgram(Program program) {
		this.program = program;
		body.setProgram(program);
	}

	public Program getProgram() {
		return program;
	}
}
