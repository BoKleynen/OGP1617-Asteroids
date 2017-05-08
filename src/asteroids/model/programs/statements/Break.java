package asteroids.model.programs.statements;

import asteroids.model.util.exceptions.BreakException;

/**
 * Created by Bo on 28/04/2017.
 */
public class Break extends Statement {

    public Break() {

    }

    @Override
    public void execute() {
    	throw new BreakException();
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
	public void resetNext() {
		returned = false;
	}
	
	private boolean returned = false;
}
