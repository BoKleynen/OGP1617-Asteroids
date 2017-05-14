package asteroids.model.programs.statements.actionStatements;

import asteroids.model.util.exceptions.NotEnoughTimeRemainingException;

/**
 * Created by Bo on 27/04/2017.
 */
public class FireBullet extends ActionStatement {

    @Override
    public void execute() {
        try {
            getParent().decrementTimeRemaining(getExecutionTime());
            getParent().getShip().fireBullet();
        } catch (NotEnoughTimeRemainingException e) {
            getParent().pause(this);
        }
    }
}
