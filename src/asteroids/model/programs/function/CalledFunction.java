package asteroids.model.programs.function;

import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.programs.Child;
import asteroids.model.programs.Parent;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.valueExpressions.ValueExpression;
import asteroids.model.programs.statements.Statement;
import asteroids.model.util.exceptions.BreakException;
import asteroids.model.util.exceptions.ReturnException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public class CalledFunction implements Parent<CalledFunction>, Child<Program> {

    public CalledFunction(Function function, List<Expression> actualArgs) {
        oldParent = function.getBody().getParent();
        body = function.getBody();
        body.setParent(this);
        setParent(function.getParent());

        for (int i = 0; i < actualArgs.size(); i++) {
            arguments.put("$" + (i+1), actualArgs.get(i));
        }
    }

    private CalledFunction oldParent;

    private Map<String, Expression> arguments = new HashMap<>();

    public Expression getParameter(String paramName) {
    	Expression returnExpresion = new ValueExpression<>(arguments.get(paramName).getValue());
    	returnExpresion.setStatement(arguments.get(paramName).getStatement());
        return returnExpresion;
    }

    public Expression execute() {
        try {
            Iterator<Statement<CalledFunction>> bodyIterator = body.iterator();
            while (bodyIterator.hasNext()) {
                bodyIterator.next().execute();
            }
        } catch (ReturnException rt) {
            body.setParent(oldParent);
            return rt.getExpression();
        }
        body.setParent(oldParent);
        throw new IllegalStateException("no return statement found");
    }

    private Map<String, Expression> localVariables = new HashMap<>();

    private Statement<CalledFunction> body;

    @Override
    public Expression getVariable(String varName) {
        if (localVariables.containsKey(varName))
            return localVariables.get(varName);

        else
            return new ValueExpression<>(getParent().getVariable(varName).getValue());
    }

    @Override
    public void addVariable(String varName, Expression value) {
        addVariableToMap(varName, value, localVariables);
    }

    private Program parent;

    @Override
    public Program getParent() {
        return parent;
    }

    @Override
    public void setParent(Program parent) {
        this.parent = parent;
    }

    @Override
    public void addPrintedObject(Object value) {
        getParent().addPrintedObject(value);
    }

    @Override
    public Function getFunction(String functionName) {
        return getParent().getFunction(functionName);
    }

    @Override
    public Ship getShip() {
        return getParent().getShip();
    }
}
