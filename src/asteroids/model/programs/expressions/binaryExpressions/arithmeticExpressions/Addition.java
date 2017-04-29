package asteroids.model.programs.expressions.binaryExpressions.arithmeticExpressions;

import asteroids.model.programs.expressions.binaryExpressions.BinaryExpression;
import asteroids.model.programs.expressions.Expression;

/**
 * Created by Bo on 28/04/2017.
 */
public class Addition extends BinaryExpression {

    public Addition(Expression leftOperand, Expression rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Double getValue() {
        return (Double) getLeftOperand().getValue() + (Double) getRightOperand().getValue();
    }
}
