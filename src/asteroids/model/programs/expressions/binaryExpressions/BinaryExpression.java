package asteroids.model.programs.expressions.binaryExpressions;

import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.statements.Statement;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public abstract class BinaryExpression<T, O> extends Expression<T> {

    public BinaryExpression(Expression<O> leftOperand, Expression<O> rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    private Expression<O> leftOperand;

    protected Expression<O> getLeftOperand() {
        return leftOperand;
    }

    private Expression<O> rightOperand;

    protected Expression<O> getRightOperand() {
        return rightOperand;
    }

    @Override
    public void setStatement(Statement statement) {
        super.setStatement(statement);
        leftOperand.setStatement(statement);
        rightOperand.setStatement(statement);
    }
}
