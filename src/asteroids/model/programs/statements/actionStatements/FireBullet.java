package asteroids.model.programs.statements.actionStatements;

import asteroids.model.util.exceptions.NotEnoughTimeRemainingException;

/**
 * Created by Bo on 27/04/2017.
 */
public class FireBullet extends ActionStatement {

    @Override
    public void execute() {
        try {
            getProgram().decrementTimeRemaining(getExecutionTime());
            getProgram().getShip().fireBullet();
        } catch (NotEnoughTimeRemainingException e) {
            getProgram().pause();
        }
    }
}
