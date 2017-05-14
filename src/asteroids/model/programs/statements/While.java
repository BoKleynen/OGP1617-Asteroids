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
        resetExecuted();
    }

    private Expression<Boolean> condition;;

    private Statement body;

    @Override
	public void execute() {
    	System.out.println("something went wrong");
//    	while (condition.getValue()) {
//    		try {
//    			body.execute();
//			} catch (BreakException br) {
//				brokenOutOfLoop = true;
//    			break;
//			}
//		}
//		executed = true;
	}

	@Override
	public void setParent(Parent parent) {
		super.setParent(parent);
		body.setParent(parent);
	}

	@Override
    public Statement next() {
    	
    	// @TODO: Implement Break functionality
    	if (brokenOutOfLoop) {
    		return null;
    	}
    	
    	if (!conditionChecked) {
    		conditionAtCheck = condition.getValue();
    		body.resetExecuted();
    		conditionChecked = true;
    	}

    	if (conditionAtCheck) {
    		Statement returnStatement = body.next();
    		
    		if (returnStatement == null) {
    			body.resetExecuted();
    			conditionChecked = false;
    			return next();
    		}
    		else {
    			return returnStatement;
    		}
    	}
    	else
    		return null;
    }
    
    
    @Override
    public void resetExecuted() {
    	conditionChecked = false;
    	conditionAtCheck = false;
    	brokenOutOfLoop = false;
    }
    
    private boolean conditionChecked;
    private boolean conditionAtCheck;  
    private boolean brokenOutOfLoop;

	@Override
	public Iterator<Statement<? extends Parent<?>>> iterator() {
		return new Iterator<Statement<? extends Parent<?>>>() {

			Iterator<Statement<? extends Parent<?>>> bodyIterator = body.iterator();

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
