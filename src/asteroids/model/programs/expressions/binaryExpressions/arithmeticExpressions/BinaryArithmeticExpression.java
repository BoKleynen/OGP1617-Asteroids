package asteroids.model.programs.expressions.binaryExpressions.arithmeticExpressions;

import asteroids.model.programs.expressions.binaryExpressions.BinaryExpression;
import asteroids.model.programs.expressions.Expression;

/**
 * Created by Bo on 28/04/2017.
 */
public abstract class BinaryArithmeticExpression extends BinaryExpression<Double> {

    public BinaryArithmeticExpression(Expression leftOperand, Expression rightOperand) {
        super(leftOperand, rightOperand);
    }
}
