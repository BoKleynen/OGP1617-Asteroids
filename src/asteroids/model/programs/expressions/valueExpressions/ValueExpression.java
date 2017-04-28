package asteroids.model.programs.expressions.valueExpressions;

import asteroids.model.programs.expressions.Expression;

/**
 * Created by Bo on 28/04/2017.
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
}
