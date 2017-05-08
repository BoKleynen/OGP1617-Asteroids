package asteroids.model.programs.statements;

import asteroids.model.Program;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.util.exceptions.BreakException;

/**
 * Created by Bo on 28/04/2017.
 *
 * TODO: implement expressions so they can be converted to boolean types
 */
public class While extends Statement {

    public While(Expression condition, Statement body) {
        this.condition = condition;
        this.body = body;
        condition.setStatement(this);
        body.setParentWhile((While) this);
        resetNext();
    }

    private Expression<Boolean> condition;;

    private Statement body;

    @Override
	public void execute() {
    	while (condition.getValue()) {
    		try {
    			body.execute();
			} catch (BreakException br) {
    			break;
			}
		}
	}
    
    @Override
    public void setProgram(Program P) {
    	super.setProgram(P);
    	body.setProgram(P);
    }
    
    @Override
    public Statement next() {
    	
    	// @TODO: Implement Break functionality
    	
    	if (!conditionChecked) {
    		conditionAtCheck = condition.getValue();
    		conditionChecked = true;
    	}
    	

    	if (conditionAtCheck) {
    		Statement returnStatement = body.next();
    		
    		if (returnStatement == null) {
    			body.resetNext();
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
    public void resetNext() {
    	conditionChecked = false;
    	conditionAtCheck = false;
    }
    
    private boolean conditionChecked;
    private boolean conditionAtCheck;   
}
