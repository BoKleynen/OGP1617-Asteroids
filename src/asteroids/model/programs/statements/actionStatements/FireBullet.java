package asteroids.model.programs.statements.actionStatements;

import asteroids.model.Program;
import asteroids.model.programs.statements.Statement;
import asteroids.model.util.exceptions.NotEnoughTimeRemainingException;

/**
 * Created by Bo on 27/04/2017.
 */
public class FireBullet extends ActionStatement {

    @Override
    public void execute() {
        getParent().decrementTimeRemaining(getExecutionTime());
        getParent().getShip().fireBullet();
    }

    @Override
    public Statement<Program> clone() throws CloneNotSupportedException {
        return null;
    }
}
