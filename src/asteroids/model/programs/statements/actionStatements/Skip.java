package asteroids.model.programs.statements.actionStatements;

import asteroids.model.Program;
import asteroids.model.programs.statements.Statement;
import asteroids.model.util.exceptions.NotEnoughTimeRemainingException;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public class Skip extends ActionStatement {

    @Override
    public void execute() {
        getParent().decrementTimeRemaining(getExecutionTime());
    }

    @Override
    public Statement<Program> clone() throws CloneNotSupportedException {
        return null;
    }
}
