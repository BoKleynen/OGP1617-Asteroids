package asteroids.model.programs.expressions.binaryExpressions.compareExpressions;

import asteroids.model.programs.expressions.*;

/**
 * Created by Bo on 28/04/2017.
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
