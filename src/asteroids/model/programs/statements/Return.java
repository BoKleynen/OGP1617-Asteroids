package asteroids.model.programs.statements;

import asteroids.model.programs.expressions.Expression;

/**
 * Created by Bo on 28/04/2017.
 */
public class Return extends Statement {

    public Return(Expression value) {
        this.value = value;
        value.setStatement(this);
    }

    private Expression value;

    @Override
    public void execute() {
    	
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
