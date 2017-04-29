package asteroids.model.programs.expressions.unaryExpressions.arithmeticExpressions;

import asteroids.model.programs.expressions.Expression;

/**
 * Created by Bo on 28/04/2017.
 */
public class Sqrt extends UnaryArithmeticExpression {
    public Sqrt(Expression operand) {
        super(operand);
    }

    @Override
    public Double getValue() {
        return Math.sqrt((Double) getOperand().getValue());
    }

}
