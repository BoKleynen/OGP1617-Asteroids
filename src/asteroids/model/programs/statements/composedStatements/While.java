package asteroids.model.programs.statements.composedStatements;

import asteroids.model.programs.Parent;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.statements.Statement;
import asteroids.model.programs.statements.simpleStatements.Break;
import asteroids.model.util.exceptions.BreakException;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Bo on 28/04/2017.
 *
 * TODO: implement expressions so they can be converted to boolean types
 */
public class While<T extends Parent<T>> extends Statement<T> {

    public While(Expression<Boolean> condition, Statement<T> body) {
        this.condition = condition;
        this.body = body;
    }

    private Expression<Boolean> condition;

    private Statement<T> body;

    private Iterator<Statement<T>> bodyIterator = new Iterator<Statement<T>>() {

		Iterator<Statement<T>> bodyIterator;
		Statement<T> statement;

		@Override
		public boolean hasNext() {
			try {
				if (statement != null) {
					return true;
				} else if (bodyIterator != null && bodyIterator.hasNext()) {
					statement = bodyIterator.next();
					return true;
				} else if (condition.getValue()) {
					bodyIterator = body.iterator();
					statement = bodyIterator.next();
					return true;
				} else {
					return false;
				}
			} catch (BreakException br) {
				return false;
			}
		}

		@Override
		public Statement<T> next() {
			if (!hasNext())
				throw new NoSuchElementException();
			Statement<T> next = statement;
			statement = null;
			return next;
		}
	};

	@Override
	public void execute() {
		condition.setStatement(this);
		while (bodyIterator.hasNext()) {
			try {
				bodyIterator.next().execute();
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

	@Override
	public Statement<T> clone() throws CloneNotSupportedException {
		Statement<T> clone = new While<>(condition.clone(), body.clone());
		clone.setParent(getParent());
		return clone;
	}
}
