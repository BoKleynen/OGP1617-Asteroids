package asteroids.model.programs.function;

import asteroids.model.Program;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.statements.Return;
import asteroids.model.programs.statements.Sequence;
import asteroids.model.programs.statements.Statement;
import asteroids.model.programs.statements.While;
import asteroids.model.programs.statements.actionStatements.ActionStatement;
import asteroids.model.programs.statements.actionStatements.FireBullet;
import be.kuleuven.cs.som.annotate.Raw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
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
		if (! isValidStatement(body))
			throw new IllegalArgumentException();

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

	public static boolean isValidStatement(Statement statement) {
		if (statement instanceof Sequence) {
			for (Statement s : ((Sequence) statement).getStatements()) {
				if (! isValidStatement(s))
					return false;
			}

			return true;
		}

		else
			return (statement instanceof ActionStatement);
	}
	
	public void execute() {
		if ( body instanceof Sequence ) {
			for (Statement statement : ((Sequence)body).getStatements()) {
				if ( statement instanceof Return ) {
					// @TODO: actually return something
					break;
				}
				else {
					statement.execute();
				}
			}
		}
		else if ( body instanceof Statement ) {
			if ( body instanceof Return ) {
				// @TODO: actually return something
			}
			else {
				body.execute();
			}	
		}
	}
}
