package asteroids.model.programs.statements.simpleStatements;

import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.function.CalledFunction;
import asteroids.model.programs.statements.Statement;
import asteroids.model.util.exceptions.ReturnException;

/**
 * Created by Bo on 28/04/2017.
 */
public class Return extends Statement<CalledFunction> {

    public Return(Expression value) {
        this.value = value;
    }
    private Expression value;

    @Override
    public void execute() {
        value.setStatement(this);
		throw new ReturnException(value);
    }

    @Override
    public boolean isValidFunctionStatement() {
        return true;
    }

    @Override
    public void setParent(CalledFunction parent) {
        super.setParent(parent);
    }

    @Override
    public Statement<CalledFunction> clone() throws CloneNotSupportedException {
        return new Return(value.clone());
    }
}
