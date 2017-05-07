package asteroids.model.programs.expressions.unaryExpressions.arithmeticExpressions;

import asteroids.model.programs.expressions.Expression;

/**
 * @author  Bo Kleynen & Yrjo Koyen
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
