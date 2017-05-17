package asteroids.model.programs.expressions.binaryExpressions.arithmeticExpressions;

import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.entityExpressions.entityCharacteristicExpressions.GetVX;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public class Multiplication extends BinaryArithmeticExpression {

    public Multiplication(Expression<Double> leftOperand, Expression<Double> rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Double getValue() {
        return getLeftOperand().getValue() * getRightOperand().getValue();
    }

    @Override
    public Expression<Double> clone() {
        Expression<Double> clone = new Multiplication(getLeftOperand().clone(), getRightOperand().clone());
        clone.setStatement(getStatement());
        return clone;
    }
}
