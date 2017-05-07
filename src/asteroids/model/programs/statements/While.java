package asteroids.model.programs.statements;

import asteroids.model.Program;
import asteroids.model.programs.expressions.Expression;

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
    }

    private Expression<Boolean> condition;;

    private Statement body;

    // Used by break statements to exit a running loop.
    protected boolean loopBreak;
    
    // Used by return statements to exit a running loop.
    protected boolean loopReturn;
    
    
    // @TODO: Implement return call handling.
    @Override
    public void execute() {
    	System.out.println("Entering while loop.");
    	loopBreak = false;
    	loopReturn = false;
    	
    	if ( body instanceof Sequence ) {
    		while (condition.getValue() && !loopBreak && !loopReturn) {
    			for (Statement statement : ((Sequence) body).getStatements()) {
    				if (statement instanceof Break) {
    					loopBreak = true;
    					break;
    				}
    				else if (statement instanceof Return) {
    					loopReturn = true;
    					break;
    				}
    			}
    			if ( loopBreak ) {
					System.out.println("Breaking out of while loop");
					break;
				}
    			if (loopReturn ) {
					System.out.println("Returning out of while loop");
    				break;
    			}
    		}
    	}
    	else if (body instanceof Statement) {
    		while (condition.getValue()) {
    			if (body instanceof Break) {
					break;
				}
				else if (body instanceof Return) {
					loopReturn = true;
					break;
				}
    		}
    		// @TODO: handle return statement value.
    	}
    	System.out.println("End of while loop execution.");
    }
    
    @Override
    public void setProgram(Program P) {
    	super.setProgram(P);
    	body.setProgram(P);
    }
}
