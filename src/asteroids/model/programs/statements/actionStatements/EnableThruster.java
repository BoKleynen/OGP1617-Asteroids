package asteroids.model.programs.statements.actionStatements;

import asteroids.model.Program;
import asteroids.model.programs.statements.Statement;
import asteroids.model.util.exceptions.NotEnoughTimeRemainingException;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public class EnableThruster extends ActionStatement {

    @Override
    public void execute() {
        getParent().decrementTimeRemaining(getExecutionTime());
        getParent().getShip().thrustOn();
    }

    @Override
    public Statement<Program> clone() throws CloneNotSupportedException {
        return null;
    }
}
