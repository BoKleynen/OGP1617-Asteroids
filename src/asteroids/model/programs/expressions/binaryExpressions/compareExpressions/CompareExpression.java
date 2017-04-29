package asteroids.model.programs.expressions.binaryExpressions.compareExpressions;

import asteroids.model.programs.expressions.binaryExpressions.BinaryExpression;
import asteroids.model.programs.expressions.Expression;

/**
 * Created by Bo on 28/04/2017.
 */
public abstract class CompareExpression extends BinaryExpression<Boolean> {
    public CompareExpression(Expression leftOperand, Expression rightOperand) {
        super(leftOperand, rightOperand);
    }
}
