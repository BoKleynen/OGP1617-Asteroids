package asteroids.model.programs.expressions.unaryExpressions.arithmeticExpressions;

import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.unaryExpressions.UnaryExpression;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public abstract class UnaryArithmeticExpression extends UnaryExpression<Double> {
    public UnaryArithmeticExpression(Expression operand) {
        super(operand);
    }
}
