package asteroids.model.programs.expressions.unaryExpressions.logicalExpressions;

import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.unaryExpressions.UnaryExpression;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public class Not extends UnaryExpression<Boolean> {

    public Not(Expression operand) {
        super(operand);
    }

    @Override
    public Boolean getValue() {
        return ! (Boolean) getOperand().getValue();
    }
}
