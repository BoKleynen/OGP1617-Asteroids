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
}
