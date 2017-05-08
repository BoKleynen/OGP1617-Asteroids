package asteroids.model.programs.statements;

/**
 * Created by Bo on 28/04/2017.
 */
public class Break extends Statement {

    public Break() {

    }

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
