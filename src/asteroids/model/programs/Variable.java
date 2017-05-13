package asteroids.model.programs;

import asteroids.model.programs.expressions.Expression;

import java.util.Map;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public interface Variable {
    Expression getVariable(String varName);

    void addVariable(String varName, Expression value);

    default void addVariableToMap(String varName, Expression value, Map<String, Expression> variables) {
        if (variables.containsKey(varName))
            if (variables.get(varName).getValue().getClass() == value.getValue().getClass())
                variables.put(varName, value);
            else
                throw new IllegalArgumentException("Expected type of " + varName +
                        " is: " + variables.get(varName).getValue().getClass().toString() +
                        " but received: " + value.getValue().getClass().toString());
        else
            variables.put(varName, value);
    }
}
