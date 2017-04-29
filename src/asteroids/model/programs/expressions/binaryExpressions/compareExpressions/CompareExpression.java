package asteroids.model.programs.expressions.binaryExpressions.compareExpressions;

import asteroids.model.programs.expressions.binaryExpressions.BinaryExpression;
import asteroids.model.programs.expressions.Expression;

/**
 * Created by Bo on 28/04/2017.
 */
public abstract class CompareExpression<O> extends BinaryExpression<Boolean, O> {
    public CompareExpression(Expression<O> leftOperand, Expression<O> rightOperand) {
        super(leftOperand, rightOperand);
    }
}
