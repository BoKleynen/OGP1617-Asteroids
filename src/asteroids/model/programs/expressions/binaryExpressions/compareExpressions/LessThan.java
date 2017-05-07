package asteroids.model.programs.expressions.binaryExpressions.compareExpressions;

import asteroids.model.programs.expressions.*;


/**
 * @author  Bo Kleynen & Yrjo Koyen
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
