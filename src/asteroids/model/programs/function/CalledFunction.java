package asteroids.model.programs.function;

import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.valueExpressions.ValueExpression;
import asteroids.model.util.exceptions.ReturnException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public class CalledFunction {

    public CalledFunction(Function parentFunction, List<Expression> actualArgs) {
        this.parentFunction = parentFunction;
        for (int i = 0; i < actualArgs.size(); i++) {
            arguments.put("$" + (i+1), actualArgs.get(i));
        }
    }

    private Map<String, Expression> arguments = new HashMap<>();

    private Function parentFunction;

    public Function getParentFunction() {
        return parentFunction;
    }

    private Map<String, Expression> localVariables;

    public Expression getLocalVariable(String variableName) {
        if (localVariables.containsKey(variableName))
            return localVariables.get(variableName);

        else
            return getParentFunction().getProgram().readGlobalVariable(variableName);
    }

    public Expression execute() {
        try {
            getParentFunction().getBody().execute();
        } catch (ReturnException rt) {
            return rt.getExpression();
        }
        return new ValueExpression<>(null);
    }


}
