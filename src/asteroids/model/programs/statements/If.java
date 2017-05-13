package asteroids.model.programs.statements;

import asteroids.model.Program;
import asteroids.model.programs.Parent;
import asteroids.model.programs.expressions.Expression;

/**
 * Created by Bo on 28/04/2017.
 *
 * TODO: implement expressions so they can be converted to boolean types
 */
public class If extends Statement{

    public If(Expression<Boolean> condition, Statement ifBody, Statement elseBody) {
        this.condition = condition;
        this.ifBody = ifBody;
        this.elseBody = elseBody;
        condition.setStatement(this);
        resetExecuted();
    }

    private Expression<Boolean> condition;
    private Statement ifBody;
    private Statement elseBody;


    @Override
    public void execute() {
        if (condition.getValue())
            ifBody.execute();
        else
            if (elseBody != null)
                elseBody.execute();
        executed = true;
    }

    @Override
    public void setParent(Parent parent) {
        super.setParent(parent);
        ifBody.setParent(parent);

        if (elseBody != null)
            elseBody.setParent(parent);
    }

    @Override
	public Statement next() {
		if (!decidedCondition) {
		    returnIf = condition.getValue();
			decidedCondition = true;
		}
		Statement returnStatement = null;
		
		if (returnIf)
			returnStatement = ifBody.next();

		else if (elseBody != null)
			returnStatement = elseBody.next();
		
		return returnStatement;
	}


	@Override
	public void resetExecuted() {
		decidedCondition = false;
		returnIf = false;
		ifBody.resetExecuted();
		if (elseBody != null)
		    elseBody.resetExecuted();
	}
	
	private boolean decidedCondition;
	private boolean returnIf;
}
