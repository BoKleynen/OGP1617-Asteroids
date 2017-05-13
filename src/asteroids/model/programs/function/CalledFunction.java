package asteroids.model.programs.function;

import asteroids.model.programs.Variable;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.valueExpressions.ValueExpression;
import asteroids.model.util.exceptions.ReturnException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public class CalledFunction implements Variable {

    public CalledFunction(Function parentFunction, List<Expression> actualArgs) {
        this.parentFunction = parentFunction;
        for (int i = 0; i < actualArgs.size(); i++) {
            arguments.put("$" + (i+1), actualArgs.get(i));
        }
    }

    private Map<String, Expression> arguments = new HashMap<>();

    public Expression getArgument(String argName) {
        return new ValueExpression<>(arguments.get(argName));
    }

    private Function parentFunction;

    public Function getParentFunction() {
        return parentFunction;
    }

    public Expression execute() {
        try {
            getParentFunction().getBody().execute();
        } catch (ReturnException rt) {
            return rt.getExpression();
        }
        return new ValueExpression<>(null);
    }

    private Map<String, Expression> localVariables = new HashMap<>();

    @Override
    public Expression getVariable(String varName) {
        if (localVariables.containsKey(varName))
            return localVariables.get(varName);

        else
            return new ValueExpression<>(getParentFunction().getProgram().getVariable(varName).getValue());
    }

    @Override
    public void addVariable(String varName, Expression value) {
        addVariableToMap(varName, value, localVariables);
    }
}
