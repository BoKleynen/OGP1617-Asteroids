package asteroids.model.programs.statements;

import java.util.Iterator;
import java.util.List;

import asteroids.model.Program;
import asteroids.model.programs.function.Function;

/**
 * Created by Bo on 28/04/2017.
 */
public class Sequence extends Statement{

    public Sequence(List<Statement> statements) {
        this.statements = statements;
        iterator = iterator();
        resetNext();
    }

    private List<Statement> statements;

    private SequenceIterator iterator;

    @Override
    public void execute() {
        while (iterator.hasNext()) {
            iterator.next().execute();
        }
    }

    public SequenceIterator iterator() {
        return new SequenceIterator() {

            private boolean paused;
            private int lastExecutedStatement = -1;

            @Override
            public boolean hasNext() {
                return lastExecutedStatement < statements.size() && !paused;
            }

            @Override
            public Statement next() {
                return statements.get(lastExecutedStatement++);
            }

            public void pause() {
                paused = true;
            }

            public void resume() {
                paused = false;
            }
        };
    }

    public void pause() {
        iterator.pause();
    }

    public void resume() {
        iterator.resume();
    }

    public List<Statement> getStatements() {
        return statements;
    }
    
    @Override
    public void setProgram(Program P) {
    	for (Statement statement : statements) {
            statement.setProgram(P);
    	}
    }
    
    @Override
    protected void setParentWhile(While parent) {
    	for (Statement statement : statements) {
            statement.setParentWhile(parent);
    	}
    }
    
    @Override
    protected void setParentFunction(Function F) {
    	for (Statement statement : statements) {
            statement.setParentFunction(F);
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
				statements.get(nextCounter).resetNext();
				nextCounter++;
				return this.next();
			}
		}
		return null;
	}

	@Override
	public void resetNext() {
		nextCounter = 0;
	}
	
	int nextCounter;
}
