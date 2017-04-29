package asteroids.model.programs.expressions.arithmeticExpressions.binaryExpressions;

import asteroids.model.programs.expressions.BinaryExpression;
import asteroids.model.programs.expressions.Expression;

/**
 * Created by Bo on 28/04/2017.
 */
public abstract class BinaryArithmeticExpression extends BinaryExpression<Double> {

    public BinaryArithmeticExpression(Expression leftOperand, Expression rightOperand) {
        super(leftOperand, rightOperand);
    }
}
