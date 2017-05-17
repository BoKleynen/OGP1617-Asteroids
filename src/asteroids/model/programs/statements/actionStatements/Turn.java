package asteroids.model.programs.statements.actionStatements;

import asteroids.model.Program;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.statements.Statement;
import asteroids.model.util.exceptions.NotEnoughTimeRemainingException;

/**
 * Created by Bo on 27/04/2017.
 */
public class Turn extends ActionStatement {

    public Turn(Expression<Double> angle) {
        this.angle = angle;
    }
    private Expression<Double> angle;

    public double getAngle() {
        return angle.getValue();
    }

    @Override
    public void execute() {
        try {
            getParent().decrementTimeRemaining(getExecutionTime());
            getParent().getShip().turn(getAngle());

        } catch (NotEnoughTimeRemainingException e) {
            getParent().pause(this);
        }
    }

    @Override
    public Statement<Program> clone()  {
        return null;
    }
}
