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
        resetExecuted();
    }

    private List<Statement> statements;

    @Override
    public void execute() {
        for (Statement statement : statements) {
            statement.execute();
        }
        executed = true;
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
