package asteroids.model;

import asteroids.model.programs.Variable;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.valueExpressions.ValueExpression;
import asteroids.model.util.exceptions.NotEnoughTimeRemainingException;
import asteroids.model.programs.function.Function;
import asteroids.model.programs.statements.Statement;
import asteroids.model.programs.statements.actionStatements.ActionStatement;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import java.util.*;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public class Program implements Variable{

	public Program(List<Function> functions, Statement main) {
		setFunctions(functions);
		setMainStatement(main);
	}

	private List<Object> printedObjects = new ArrayList<>();

	public void addPrintedObject(Object object) {
		printedObjects.add(object);
	}

	public List<Object> execute(double time) {
		incrementTimeRemaining(time);
		printedObjects = new ArrayList<Object>();
		
		if (getTimeRemaining() > 0.2 )
			unPause();
		
		while (!isPaused) {
			Statement next = getNextStatement();
			
			// If main statement is completed then break;
			if (next == null)
				break;
			else
				next.execute();
		}
		return isPaused ? null : printedObjects;
	}
	
	private Statement getNextStatement() {
		Statement nextStatement = main.next();
		if ( nextStatement == null ) {
			main.resetNext();
			return null;
		}
		else
			return nextStatement;
	}
	
	public void pause() {
		isPaused = true;
	}
	
	public void unPause() {
		isPaused = false;
	}

	private Map<String, Function> functions = new HashMap<>();

	public Function getFunction(String functionName) {
		return functions.get(functionName);
	}

	public void setFunctions(List<Function> functions) {
		for (Function function: functions) {
			this.functions.put(function.getFunctionName(), function);
			function.setProgram(this);
		}
	}

	private Statement main;

	private Map<String, Expression> globalVariables = new HashMap<>();

	@Override
	public Expression getVariable(String varName) {
		return globalVariables.get(varName);
	}

	@Override
	public void addVariable(String varName, Expression value) {
		addVariableToMap(varName, value, globalVariables);
	}

	private void setMainStatement(Statement main) {
		this.main = main;
		main.setProgram(this);
	}

	private Ship ship;

	@Basic
	public Ship getShip() {
		return ship;
	}

	@Basic @Raw
	public void setShip(Ship ship) {
		this.ship = ship;
	}

	private double timeRemaining = 0;

	@Basic
	public double getTimeRemaining() {
		return timeRemaining;
	}

	public void incrementTimeRemaining(double time) {
		timeRemaining += time;
	}

	public void decrementTimeRemaining(double time) {
		double newTime = timeRemaining - time;

		if (newTime < 0)
			throw new NotEnoughTimeRemainingException();

		timeRemaining = newTime;
	}

	private boolean isPaused = false;
}
