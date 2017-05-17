package asteroids.model.programs.expressions.binaryExpressions.arithmeticExpressions;

import asteroids.model.programs.expressions.binaryExpressions.BinaryExpression;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.entityExpressions.entityCharacteristicExpressions.GetVX;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public class Addition extends BinaryArithmeticExpression {

    public Addition(Expression<Double> leftOperand, Expression<Double> rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Double getValue() {
        return getLeftOperand().getValue() + getRightOperand().getValue();
    }

    @Override
    public Expression<Double> clone() {
        Expression<Double> clone = new Addition(getLeftOperand().clone(), getRightOperand().clone());
        clone.setStatement(getStatement());
        return clone;
    }
}
