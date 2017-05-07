package asteroids.model.programs.expressions.binaryExpressions.arithmeticExpressions;

import asteroids.model.programs.expressions.binaryExpressions.BinaryExpression;
import asteroids.model.programs.expressions.Expression;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public class Addition extends BinaryArithmeticExpression {

    public Addition(Expression<Double> leftOperand, Expression<Double> rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Double getValue() {
        return (Double) getLeftOperand().getValue() + (Double) getRightOperand().getValue();
    }
}
