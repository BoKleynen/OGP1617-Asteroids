package asteroids.model.programs.statements.composedStatements;

import asteroids.model.programs.Parent;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.statements.simpleStatements.SimpleStatement;
import asteroids.model.programs.statements.Statement;
import asteroids.model.util.exceptions.BreakException;

import java.util.Iterator;

/**
 * Created by Bo on 28/04/2017.
 *
 * TODO: implement expressions so they can be converted to boolean types
 */
public class While<T extends Parent<T>> extends SimpleStatement<T> {

    public While(Expression<Boolean> condition, Statement<T> body) {
        this.condition = condition;
        this.body = body;
        condition.setStatement(this);
    }

    private Expression<Boolean> condition;

    private Statement<T> body;

	public Statement getBody() {
		return body;
	}

	@Override
	public void execute() {
		while (condition.getValue()) {
			System.out.println("entering while");
			Iterator<Statement<T>> iterator = body.iterator();
			try {
				while (iterator.hasNext()) {
					iterator.next().execute();
				}
			} catch (BreakException br) {
				break;
			}

		}
	}

	@Override
	public void setParent(T parent) {
		super.setParent(parent);
		body.setParent(parent);
	}

	@Override
	public boolean isValidFunctionStatement() {
		return body.isValidFunctionStatement();
	}
}
