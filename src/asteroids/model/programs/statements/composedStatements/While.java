package asteroids.model.programs.statements.composedStatements;

import asteroids.model.programs.Parent;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.statements.simpleStatements.Break;
import asteroids.model.programs.statements.simpleStatements.SimpleStatement;
import asteroids.model.programs.statements.Statement;
import asteroids.model.util.exceptions.BreakException;

import java.util.Iterator;

/**
 * Created by Bo on 28/04/2017.
 *
 * TODO: implement expressions so they can be converted to boolean types
 */
public class While<T extends Parent<T>> extends Statement<T> {

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
		throw new IllegalStateException("something went terribly wrong");
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

	@Override
	public Iterator<Statement<T>> iterator() {
		return new Iterator<Statement<T>>() {

			boolean brokenOutOfLoop = false;
			Iterator<Statement<T>> subIterator;

			@Override
			public boolean hasNext() {
				return brokenOutOfLoop;
			}

			@Override
			public Statement<T> next() {
				Statement<T> nextStatement = null;

				if (subIterator != null && subIterator.hasNext())
					nextStatement = subIterator.next();

				else if (condition.getValue()){
					subIterator = body.iterator();
					nextStatement = subIterator.next();
				}

				if (nextStatement instanceof Break)
					brokenOutOfLoop = true;

				return nextStatement;
			}
		};
	}
}
