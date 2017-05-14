package asteroids.model.programs.statements;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import asteroids.model.Program;
import asteroids.model.programs.Parent;
import asteroids.model.programs.function.Function;

/**
 * Created by Bo on 28/04/2017.
 */
public class Sequence extends Statement {

    public Sequence(List<Statement> statements) {
        this.statements = statements;
        resetExecuted();
    }

    private List<Statement> statements;

    @Override
    public void execute() {
        System.out.println("something went wrong");

//        for (Statement statement : statements) {
//            statement.execute();
//        }
//        executed = true;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    @Override
    public void setParent(Parent parent) {
        super.setParent(parent);

        for (Statement statement : statements) {
            statement.setParent(parent);
        }
    }

    @Override
	public Statement next() {
		if ( nextCounter < statements.size()) {
			Statement returnStatement = statements.get(nextCounter).next();
			if ( returnStatement != null ) {
				return returnStatement;
			}
			else {
				statements.get(nextCounter).resetExecuted();
				nextCounter++;
				return this.next();
			}
		}
		return null;
	}

	@Override
	public void resetExecuted() {
		nextCounter = 0;
	}
	
	int nextCounter;

    public Iterator<Statement<? extends Parent<?>>> iterator() {
        return new Iterator<Statement<? extends Parent<?>>>() {

            int index = 0;
            Iterator<Statement<? extends Parent<?>>> subIterator = statements.get(index).iterator();

            @Override
            public boolean hasNext() {
                return subIterator.hasNext() || index + 1 < statements.size();
            }

            @Override
            public Statement<? extends Parent<?>> next() throws NoSuchElementException {
                if (! hasNext())
                    throw new NoSuchElementException();

                if (subIterator.hasNext())
                    return subIterator.next();

                else {
                    subIterator = statements.get(index++).iterator();
                    return subIterator.next();
                }

            }
        };
    }
}
