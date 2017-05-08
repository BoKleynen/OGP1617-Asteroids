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
}
