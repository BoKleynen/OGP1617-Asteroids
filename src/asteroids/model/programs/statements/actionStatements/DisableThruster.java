package asteroids.model.programs.statements.actionStatements;

import asteroids.model.util.exceptions.NotEnoughTimeRemainingException;

/**
 * Created by Bo on 27/04/2017.
 */
public class DisableThruster extends ActionStatement {

    @Override
    public void execute() {

        try {
            getParent().decrementTimeRemaining(getExecutionTime());
            getParent().getShip().thrustOff();
            executed = true;
        } catch (NotEnoughTimeRemainingException e) {
            getParent().pause();
        }
    }
}
