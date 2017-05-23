package asteroids.model.programs.expressions.unaryExpressions.arithmeticExpressions;

import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.unaryExpressions.logicalExpressions.Not;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public class Negate extends UnaryArithmeticExpression {
    public Negate(Expression operand) {
        super(operand);
    }

    @Override
    public Double getValue() {
        return - ((Double)getOperand().getValue());
    }

    @Override
    public Expression<Double> clone() {
        Expression clone = new Negate(getOperand());
        clone.setStatement(getStatement());
        return clone;
    }
}
