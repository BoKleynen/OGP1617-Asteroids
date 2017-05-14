package asteroids.model.programs.statements.simpleStatements;

import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.function.CalledFunction;
import asteroids.model.util.exceptions.ReturnException;

/**
 * Created by Bo on 28/04/2017.
 */
public class Return extends SimpleStatement<CalledFunction> {

    public Return(Expression value) {
        this.value = value;
        value.setStatement(this);
    }

    private Expression value;

    @Override
    public void execute() {
		throw new ReturnException(value);
    }

    @Override
    public boolean isValidFunctionStatement() {
        return true;
    }
}
