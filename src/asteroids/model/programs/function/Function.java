package asteroids.model.programs.function;

import asteroids.model.Program;
import asteroids.model.programs.Child;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.valueExpressions.ValueExpression;
import asteroids.model.programs.statements.*;
import asteroids.model.programs.statements.actionStatements.ActionStatement;
import asteroids.model.util.exceptions.ReturnException;

import java.util.Map;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public class Function implements Child<Program>{

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

	private Program parent;

	@Override
	public Program getParent() {
		return parent;
	}

	@Override
	public void setParent(Program parent) {
		this.parent = parent;
	}

	public static boolean isValidStatement(Statement statement) {
		if (statement instanceof Sequence) {
			for (Statement s : ((Sequence) statement).getStatements()) {
				if (! isValidStatement(s))
					return false;
			}

			return true;
		}

		else if (statement instanceof If)
			return (isValidStatement( ((If) statement).getIfBody())
					|| (((If) statement).getElseBody() != null && isValidStatement(((If) statement).getElseBody())));


		else if (statement instanceof While)
			return isValidStatement(((While) statement).getBody());

		else
			return !(statement instanceof ActionStatement || statement instanceof Print);
	}
}
