package asteroids.model.programs.statements.actionStatements;

import asteroids.model.Program;
import asteroids.model.programs.statements.Statement;

/**
 * @author  Bo Kleynen & Yrjo Koyen
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
