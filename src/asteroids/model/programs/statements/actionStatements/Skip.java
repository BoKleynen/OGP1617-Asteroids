package asteroids.model.programs.statements.actionStatements;

import asteroids.model.exceptions.NotEnoughTimeRemainingException;

/**
 * Created by Bo on 27/04/2017.
 */
public class Skip extends ActionStatement {

    @Override
    public void execute() {
        try {
            getProgram().decrementTimeRemaining(getExecutionTime());
        }catch (NotEnoughTimeRemainingException e) {
            getProgram().pause();
        }
    }
}
