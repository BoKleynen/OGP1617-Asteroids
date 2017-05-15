package asteroids.model;

import asteroids.model.programs.Parent;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.util.exceptions.NotEnoughTimeRemainingException;
import asteroids.model.programs.function.Function;
import asteroids.model.programs.statements.Statement;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import java.util.*;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public class Program implements Parent<Program> {

	public Program(List<Function> functions, Statement<Program> main) {
		setFunctions(functions);
		main.setParent(this);
		mainIterator = main.iterator();
	}

	private List<Object> printedObjects = new ArrayList<>();

	public void addPrintedObject(Object object) {
		printedObjects.add(object);
	}

	private Statement stashedStatement = null;

	private Iterator<Statement<Program>> mainIterator;

	public List<Object> execute(double time) {
		incrementTimeRemaining(time);

		if (getTimeRemaining() >= 0.2) {
			unPause();

			if (stashedStatement != null) {
				stashedStatement.execute();
				stashedStatement = null;
			}
		}

		while (! isPaused && mainIterator.hasNext()) {
			mainIterator.next().execute();
		}

		return isPaused ? null : printedObjects;
	}

	private boolean isPaused = false;

	public void pause(Statement statement) {
		isPaused = true;
		stashedStatement = statement;
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
			function.setParent(this);
		}
	}

//	private Statement<Program> main;

	private Map<String, Expression> globalVariables = new HashMap<>();

	@Override
	public Expression getVariable(String varName) {
		return globalVariables.get(varName);
	}
	
	public Map<String, Expression> getAllVariables() {
		return globalVariables;
	}

	@Override
	public void addVariable(String varName, Expression value) {
		if (functions.containsKey(varName))
			throw new IllegalArgumentException("function and variables can not hold the same name");
		addVariableToMap(varName, value, globalVariables);
	}

//	private void setMainStatement(Statement<Program> main) {
//		this.main = main;
//		main.setParent(this);
//	}

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

}
