package asteroids.model.programs.expressions;

import asteroids.model.programs.expressions.Expression;

/**
 * Created by Bo on 13/05/2017.
 */
public class ReadParameter extends Expression {
    public ReadParameter(String paramName) {
        this.paramName = paramName;
    }

    String paramName;

    @Override
    public Object getValue() {
        return null;
    }
}
