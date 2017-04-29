package asteroids.model.programs.expressions.binaryExpressions;

import asteroids.model.programs.expressions.Expression;

/**
 * Created by Bo on 28/04/2017.
 */
public abstract class BinaryExpression<T, O> extends Expression<T> {

    public BinaryExpression(Expression<O> leftOperand, Expression<O> rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    private Expression leftOperand;

    public Expression getLeftOperand() {
        return leftOperand;
    }

    private Expression rightOperand;

    public Expression getRightOperand() {
        return rightOperand;
    }
}
