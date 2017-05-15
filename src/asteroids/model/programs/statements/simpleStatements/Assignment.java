package asteroids.model.programs.statements.simpleStatements;

import asteroids.model.programs.expressions.Expression;

/**
 * Created by Bo on 28/04/2017.
 */
public class Assignment extends asteroids.model.programs.statements.Statement {

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
        System.out.println("adding: " + name + " = " + value.getValue());
    	getParent().addVariable(name, value);
    }

    @Override
    public boolean isValidFunctionStatement() {
        return true;
    }
}
