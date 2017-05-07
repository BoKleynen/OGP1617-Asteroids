package asteroids.model.programs.expressions.binaryExpressions.compareExpressions;

import asteroids.model.programs.expressions.binaryExpressions.BinaryExpression;
import asteroids.model.programs.expressions.Expression;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public abstract class CompareExpression<O> extends BinaryExpression<Boolean, O> {
    public CompareExpression(Expression<O> leftOperand, Expression<O> rightOperand) {
        super(leftOperand, rightOperand);
    }
}
