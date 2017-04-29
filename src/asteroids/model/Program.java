package asteroids.model;

import asteroids.model.programs.expressions.Expression;
import asteroids.model.util.exceptions.NotEnoughTimeRemainingException;
import asteroids.model.programs.function.Function;
import asteroids.model.programs.statements.Statement;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class Program {
	
	public Program(List<Function> functions, Statement main) {
		setFunctions(functions);
		setMainStatement(main);
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

		if (newTime <= 0)
			throw new NotEnoughTimeRemainingException();

		timeRemaining = newTime;
	}

	public List<Object> execute(double time) {
		incrementTimeRemaining(time);
		return new ArrayList<>();
	}
	
	public List<Function> getFunctions() {
		return functions;
	}
	
	private void setFunctions(List<Function> functions) {
		for (Function function : functions) {
			function.setProgram(this);
		}
		this.functions = functions;
	}
	
	private List<Function> functions;
	
	public Statement getMainStatement() {
		return this.mainStatement;
	}
	
	private void setMainStatement(Statement main) {
		mainStatement.setProgram(this);
		this.mainStatement = main;
	}
	
	private Statement mainStatement;

	public void pause() {

	}

	private Map<String, Expression> variables = new HashMap<>();

	//TODO: enforce read only characteristics
	public Expression readVariable(String variableName) {
		return getVariable(variableName);
	}

	private Expression getVariable(String variableName) {
		return variables.get(variableName);
	}

	private void addVariable(String variableName, Expression expression) {
		if (variables.containsKey(variableName))
			if (variables.get(variableName).getValue().getClass() == expression.getValue().getClass())
				variables.put(variableName, expression);

		else
			variables.put(variableName, expression);
	}
}
