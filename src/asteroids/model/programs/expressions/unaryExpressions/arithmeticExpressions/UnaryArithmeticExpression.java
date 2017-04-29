package asteroids.model.programs.expressions.arithmeticExpressions.unaryExpressions;

import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.UnaryExpression;

/**
 * Created by Bo on 28/04/2017.
 */
public abstract class UnaryArithmeticExpression extends UnaryExpression<Double> {
    public UnaryArithmeticExpression(Expression operand) {
        super(operand);
    }
}
