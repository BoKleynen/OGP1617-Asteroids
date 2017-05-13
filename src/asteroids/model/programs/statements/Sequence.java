package asteroids.model.programs.statements;

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
        resetExecuted();
    }

    private List<Statement> statements;

    private SequenceIterator iterator;

    @Override
    public void execute() {
//        while (iterator.hasNext()) {
//            iterator.next().execute();
//        }
        for (Statement statement : statements) {
            statement.execute();
        }
        executed = true;
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
    public void setProgram(Program program) {
    	super.setProgram(program);

        for (Statement statement : statements) {
            statement.setProgram(program);
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
}
