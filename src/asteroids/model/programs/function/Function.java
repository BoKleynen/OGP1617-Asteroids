package asteroids.model.programs.function;

import asteroids.model.Program;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.valueExpressions.ValueExpression;
import asteroids.model.programs.statements.Sequence;
import asteroids.model.programs.statements.Statement;
import asteroids.model.programs.statements.actionStatements.ActionStatement;
import asteroids.model.util.exceptions.ReturnException;

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

	public Statement getBody() {
		return body;
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
			return !(statement instanceof ActionStatement);
	}
}
