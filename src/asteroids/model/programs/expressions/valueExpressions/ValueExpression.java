package asteroids.model.programs.expressions.valueExpressions;

import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.ReadVariableExpression;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public class ValueExpression<T> extends Expression<T> {

    public ValueExpression(T value) {
        this.value = value;
    }

    private T value;

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public Expression<T> clone() {
        Expression clone = new ValueExpression<>(value);
        clone.setStatement(getStatement());
        return clone;
    }
}
