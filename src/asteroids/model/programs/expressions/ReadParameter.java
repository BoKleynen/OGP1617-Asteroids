package asteroids.model.programs.expressions;


import asteroids.model.programs.function.CalledFunction;

/**
 * Created by Bo on 13/05/2017.
 */
public class ReadParameter extends Expression {
    public ReadParameter(String paramName) {
        value = ((CalledFunction) getStatement().getParent()).getParameter(paramName).getValue();
    }

    private Object value;

    @Override
    public Object getValue() {
        return value;
    }
}
