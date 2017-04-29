package asteroids.model.programs.expressions.unaryExpressions;

import asteroids.model.Entity;
import asteroids.model.programs.expressions.Expression;

/**
 * Created by Bo on 28/04/2017.
 */
public abstract class UnaryExpression<T> extends Expression<T> {

    public UnaryExpression(Expression operand) {
        this.operand = operand;
    }

    private Expression operand;

    public Expression getOperand() {
        return operand;
    }
}
