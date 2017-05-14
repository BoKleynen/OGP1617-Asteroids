package asteroids.model.programs.statements.simpleStatements;

import asteroids.model.util.exceptions.BreakException;

/**
 * Created by Bo on 28/04/2017.
 */
public class Break extends SimpleStatement {

    public Break() {

    }

    @Override
    public void execute() {
        throw new BreakException();
    }

    @Override
    public boolean isValidFunctionStatement() {
        return true;
    }
}
