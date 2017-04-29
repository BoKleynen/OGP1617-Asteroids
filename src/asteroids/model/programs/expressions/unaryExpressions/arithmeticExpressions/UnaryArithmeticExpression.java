package asteroids.model.programs.expressions.unaryExpressions.arithmeticExpressions;

import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.unaryExpressions.UnaryExpression;

/**
 * Created by Bo on 28/04/2017.
 */
public abstract class UnaryArithmeticExpression extends UnaryExpression<Double> {
    public UnaryArithmeticExpression(Expression operand) {
        super(operand);
    }
}
