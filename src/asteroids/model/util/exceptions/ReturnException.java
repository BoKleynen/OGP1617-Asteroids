package asteroids.model.util.exceptions;

import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.valueExpressions.ValueExpression;

/**
 * Created by Bo on 08/05/2017.
 */
public class ReturnException extends RuntimeException {
    public ReturnException(Expression value) {
        this.expression = new ValueExpression<>(value.getValue());
    }

    private Expression expression;

    public Expression getExpression() {
        return expression;
    }
}
