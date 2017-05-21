package asteroids.model.programs.expressions.binaryExpressions.compareExpressions;

import asteroids.model.programs.expressions.*;
import asteroids.model.programs.statements.Statement;


/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public class LessThan extends CompareExpression<Double> {

    public LessThan(Expression<Double> left, Expression<Double> right) {
        super(left, right);
    }

    @Override
    public Boolean getValue() {
        return getLeftOperand().getValue() < getRightOperand().getValue();
    }

    @Override
    public Expression<Boolean> clone() {
        return new LessThan(getLeftOperand().clone(), getRightOperand().clone());
    }
}
