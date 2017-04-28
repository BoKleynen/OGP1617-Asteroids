package asteroids.model.programs.expressions.arithmeticExpressions.binaryExpressions;

import asteroids.model.programs.expressions.Expression;

/**
 * Created by Bo on 28/04/2017.
 */
public class Multiplication extends BinaryArithmeticExpression {

    public Multiplication(Expression leftOperand, Expression rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Double getValue() {
        return (Double) getLeftOperand().getValue() * (Double) getRightOperand().getValue();
    }
}
