package asteroids.model.programs.statements;

import java.util.Iterator;

import asteroids.model.programs.expressions.Expression;

/**
 * Created by Bo on 28/04/2017.
 */
public class Assignment extends Statement{

    public Assignment(String name, Expression value) {
        this.name = name;
        this.value = value;
        value.setStatement(this);
    }

    private String name;

    public String getName() {
        return name;
    }

    private Expression value;

    public Expression getValue() {
        return value;
    }

    @Override
    public void execute() {
    	getProgram().addGlobalVariable(name, value);
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
