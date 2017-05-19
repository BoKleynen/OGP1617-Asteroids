package asteroids.model.programs.expressions;

import asteroids.model.programs.function.CalledFunction;
import asteroids.model.programs.statements.Statement;

import java.util.List;

/**
 * Created by Bo on 08/05/2017.
 */
public class FunctionCall extends Expression {

    public FunctionCall(String functionName, List<Expression> actualArgs) {
        this.functionName = functionName;
        this.actualArgs = actualArgs;
    }

    private String functionName;

    private List<Expression> actualArgs;

    @Override
    public Object getValue() {
        try {
            return new CalledFunction(getStatement().getParent().getFunction(functionName), actualArgs, getStatement()).execute().getValue();
        } catch (CloneNotSupportedException e) { return null;}
    }

    @Override
    public void setStatement(Statement statement) {
        super.setStatement(statement);
        for (Expression expression : actualArgs) {
            expression.setStatement(statement);
        }
    }

    @Override
    public Expression clone() {
        Expression clone = new FunctionCall(functionName, actualArgs);
        clone.setStatement(getStatement());
        return clone;
    }
}
