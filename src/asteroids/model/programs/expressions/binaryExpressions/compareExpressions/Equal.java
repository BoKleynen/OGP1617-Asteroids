package asteroids.model.programs.expressions.binaryExpressions.compareExpressions;

import asteroids.model.programs.expressions.*;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public class Equal extends CompareExpression<Object> {
    public Equal(Expression<Object> left, Expression<Object> right) {
        super(left, right);
    }

    @Override
    public Boolean getValue() {
        return getLeftOperand().getValue() == getRightOperand().getValue();
    }

    @Override
    public Expression<Boolean> clone() {
        Expression clone = new Equal(getLeftOperand(), getRightOperand());
        clone.setStatement(getStatement());
        return clone;
    }
}
