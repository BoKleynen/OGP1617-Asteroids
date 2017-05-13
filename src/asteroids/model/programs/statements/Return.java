package asteroids.model.programs.statements;

import asteroids.model.programs.expressions.Expression;
import asteroids.model.util.exceptions.ReturnException;

/**
 * Created by Bo on 28/04/2017.
 */
public class Return extends Statement {

    public Return(Expression value) {
        this.value = value;
        
        // Should value ever even be null? This check does solve some NullPointerExceptions...
        if (value != null) 
        	value.setStatement(this);
    }

    private Expression value;

    @Override
    public void execute() {
		executed = true;
		throw new ReturnException(value);
    }
    
    @Override
	public Statement next() {
		if (!returned) {
			returned = true;
			return this;
		}
		else
			return null;
	}

	@Override
	public void resetExecuted() {
		returned = false;
	}
	
	private boolean returned = false;
}
