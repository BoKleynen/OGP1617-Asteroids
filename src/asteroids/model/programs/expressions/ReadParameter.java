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
        System.out.println(getStatement());
        return ((CalledFunction) getStatement().getParent()).getParameter(paramName).getValue();
    }

    @Override
    public Expression clone() {
        Expression clone = new ReadParameter(paramName);
        clone.setStatement(getStatement());
        return clone;
    }
}
