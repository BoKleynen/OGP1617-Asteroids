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

    public CalledFunction(Function function, List<Expression> actualArgs) throws CloneNotSupportedException{
        System.out.println("function call");
        for (int i = 0; i < actualArgs.size(); i++) {
            arguments.put("$" + (i+1), new ValueExpression<>(actualArgs.get(i).getValue()));
        }
        setParent(function.getParent());
        body = function.getBody().clone();
        body.setParent(this);
    }

    private Map<String, Expression> arguments = new HashMap<>();

    public Expression getParameter(String paramName) {
    	return new ValueExpression<>(arguments.get(paramName).getValue());

    }

    public Expression execute() {
        try {
            Iterator<Statement<CalledFunction>> bodyIterator = body.iterator();
            while (bodyIterator.hasNext()) {
                bodyIterator.next().execute();
            }
        } catch (ReturnException rt) {
            return rt.getExpression();
        }
        throw new IllegalStateException("no return statement found");
    }

    private Map<String, Expression> localVariables = new HashMap<>();

    private Statement<CalledFunction> body;

    @Override
    public Expression getVariable(String varName) {
        if (localVariables.containsKey(varName))
            return localVariables.get(varName);
        else {
            return new ValueExpression<>(getParent().getVariable(varName).getValue());
        }
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

    @Override
    public double getTimeRemaining() {
        return getParent().getTimeRemaining();
    }
}
