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
    
    
    // @TODO: Implement return calls 
    @Override
    public void execute() {
    	System.out.println("Entering while loop.");
    	loopBreak = false;
    	loopReturn = false;
    	
    	if ( body instanceof Sequence ) {
    		while (condition.getValue() && !loopBreak && !loopReturn) {
    			for (Statement statement : ((Sequence) body).getStatements()) {
    				statement.execute();
    				if ( loopBreak ) {
    					System.out.println("Breaking out of for loop");
    					break;
    				}
    				if ( loopReturn ) {
    					// something
    					break;
    				}
    			}
    			if ( loopBreak ) {
					System.out.println("Breaking out of while loop");
					break;
				}
    			if (loopReturn ) {
    				break;
    			}
    		}
    	}
    	else if (body instanceof Statement) {
    		while (condition.getValue() && !loopBreak && !loopReturn) {
    			body.execute();
    			if ( loopBreak ) {
					System.out.println("Breaking out of while loop");
					break;
				}
    			if (loopReturn ) {
    				break;
    			}
    		}
    	}
    	System.out.println("End of while loop execution.");
    }
    
    protected void executeBreak() {
    	loopBreak = true;
    }
    
    protected void executeReturn(Expression returnValue) {
    	this.loopReturn = true;
    }
    
    @Override
    public void setProgram(Program P) {
    	super.setProgram(P);
    	body.setProgram(P);
    }
}
