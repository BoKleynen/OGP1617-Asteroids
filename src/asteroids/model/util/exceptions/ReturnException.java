package asteroids.model.util.exceptions;

import asteroids.model.programs.expressions.Expression;

/**
 * Created by Bo on 08/05/2017.
 */
public class ReturnException extends RuntimeException {
    public ReturnException(Expression value) {
        this.expression = value;
    }

    private Expression expression;

    public Expression getExpression() {
        return expression;
    }
}
