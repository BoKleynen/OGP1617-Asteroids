package asteroids.model.programs.expressions.unaryExpressions.arithmeticExpressions;

import asteroids.model.programs.expressions.Expression;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public class Sqrt extends UnaryArithmeticExpression {
    public Sqrt(Expression operand) {
        super(operand);
    }

    @Override
    public Double getValue() {
        return Math.sqrt((Double) getOperand().getValue());
    }

}
