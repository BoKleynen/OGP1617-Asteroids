package asteroids.model.programs.expressions.unaryExpressions;

import asteroids.model.Entity;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.statements.Statement;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public abstract class UnaryExpression<T> extends Expression<T> {

    public UnaryExpression(Expression operand) {
        this.operand = operand;
    }

    private Expression operand;

    public Expression getOperand() {
        return operand;
    }

    @Override
    public void setStatement(Statement statement) {
        super.setStatement(statement);
        operand.setStatement(statement);
    }
}
