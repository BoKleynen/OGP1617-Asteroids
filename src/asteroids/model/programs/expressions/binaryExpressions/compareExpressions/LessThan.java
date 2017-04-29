package asteroids.model.programs.expressions.binaryExpressions.compareExpressions;

import asteroids.model.programs.expressions.*;


/**
 * Created by Bo on 28/04/2017.
 */
public class LessThan extends CompareExpression<Double> {

    public LessThan(Expression<Double> left, Expression<Double> right) {
        super(left, right);
    }

    @Override
    public Boolean getValue() {
        return (Double)getLeftOperand().getValue() < (Double)getRightOperand().getValue();
    }
}
