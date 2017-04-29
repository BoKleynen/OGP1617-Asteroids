package asteroids.model.programs.expressions.arithmeticExpressions.unaryExpressions;

import asteroids.model.programs.expressions.Expression;

/**
 * Created by Bo on 28/04/2017.
 */
public class Negate extends UnaryArithmeticExpression {
    public Negate(Expression operand) {
        super(operand);
    }

    @Override
    public Double getValue() {
        return null;
    }
}
