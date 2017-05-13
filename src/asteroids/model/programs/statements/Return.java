package asteroids.model.programs.statements;

import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.function.CalledFunction;
import asteroids.model.util.exceptions.ReturnException;

/**
 * Created by Bo on 28/04/2017.
 */
public class Return extends Statement<CalledFunction> {

    public Return(Expression value) {
        this.value = value;
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
