package asteroids.model.programs.statements;

import asteroids.model.Program;
import asteroids.model.programs.Parent;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.util.exceptions.BreakException;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Bo on 28/04/2017.
 *
 * TODO: implement expressions so they can be converted to boolean types
 */
public class While extends Statement {

    public While(Expression<Boolean> condition, Statement body) {
        this.condition = condition;
        this.body = body;
        condition.setStatement(this);
    }

    private Expression<Boolean> condition;

    private Statement body;

    @Override
	public void execute() {
    	throw new RuntimeException("something went terribly wrong");
	}

	@Override
	public void setParent(Parent parent) {
		super.setParent(parent);
		body.setParent(parent);
	}

	@Override
	public Iterator<Statement> iterator() {
		return new Iterator<Statement>() {

			Iterator<Statement> bodyIterator = body.iterator();

			@Override
			public boolean hasNext() {
				return condition.getValue() && bodyIterator.hasNext();
			}

			@Override
			public Statement<? extends Parent<?>> next() throws NoSuchElementException {
				if (! hasNext())
					throw new NoSuchElementException();

				return bodyIterator.next();
			}
		};
	}
}
