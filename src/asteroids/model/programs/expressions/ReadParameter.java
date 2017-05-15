package asteroids.model.programs.expressions;


import asteroids.model.programs.function.CalledFunction;

/**
 * Created by Bo on 13/05/2017.
 */
public class ReadParameter extends Expression {
    public ReadParameter(String paramName) {
        this.paramName = paramName;
    }

    private String paramName;

    @Override
    public Object getValue() {
        return ((CalledFunction) getStatement().getParent()).getParameter(paramName).getValue();
    }
}
