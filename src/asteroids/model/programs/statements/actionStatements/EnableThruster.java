package asteroids.model.programs.statements.actionStatements;

import asteroids.model.Ship;
import asteroids.model.exceptions.NotEnoughTimeRemainingException;

/**
 * Created by Bo on 27/04/2017.
 */
public class EnableThruster extends ActionStatement {

    @Override
    public void execute() {
        try {
            getProgram().decrementTimeRemaining(getExecutionTime());
            getProgram().getShip().thrusterOn();
        } catch (NotEnoughTimeRemainingException e) {
            getProgram().pause();
        }
    }
}
