package asteroids.model.programs.statements.actionStatements;

import asteroids.model.programs.expressions.Expression;
import asteroids.model.util.exceptions.NotEnoughTimeRemainingException;

/**
 * Created by Bo on 27/04/2017.
 */
public class Turn extends ActionStatement {

    public Turn(Expression angle) {

    }
    private double angle;

    public double getAngle() {
        return angle;
    }

    @Override
    public void execute() {
        try {
            getProgram().decrementTimeRemaining(getExecutionTime());
            getProgram().getShip().turn(getAngle());
        } catch (NotEnoughTimeRemainingException e) {
            getProgram().pause();
        }
    }
}
