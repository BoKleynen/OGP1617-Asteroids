package asteroids.model.programs;

import asteroids.model.Ship;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.valueExpressions.ValueExpression;
import asteroids.model.programs.function.Function;

import java.util.Map;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public interface Parent<T> {
    Expression getVariable(String varName);

    void addVariable(String varName, Object value);

    default void addVariableToMap(String varName, Object value, Map<String, Expression> variables) throws IllegalArgumentException {
        if (variables.containsKey(varName))
            if (variables.get(varName).getValue().getClass() == value.getClass()) {
                variables.put(varName, new ValueExpression<>(value));
            }
            else
                throw new IllegalArgumentException("Expected type of " + varName +
                        " is: " + variables.get(varName).getValue().getClass() +
                        " but received: " + value.getClass());
        else
            variables.put(varName, new ValueExpression<>(value));
    }

    void addPrintedObject(Object value);

    Function getFunction(String functionName);

    Ship getShip();

    double getTimeRemaining();

}
