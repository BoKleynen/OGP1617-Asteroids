package asteroids.model;

import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.valueExpressions.ValueExpression;
import asteroids.model.util.exceptions.NotEnoughTimeRemainingException;
import asteroids.model.programs.function.Function;
import asteroids.model.programs.statements.Statement;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Program {

	public Program(List<Function> functions, Statement main) {
		setFunctions(functions);
		this.main = main;
		main.setProgram(this);
	}

	public List<Object> execute(double time) {
		incrementTimeRemaining(time);

		return null;
	}

	public void pause() {

	}

	private Map<String, Function> functions = new HashMap<>();

	public void setFunctions(List<Function> functions) {
		for (Function function: functions) {
			this.functions.put(function.getFunctionName(), function);
			function.setProgram(this);
		}
	}

	private Statement main;

	private Map<String, Expression> globalVariables = new HashMap<>();

	public Expression readGlobalVariable(String variableName) {
		return new ValueExpression<>(getGlobalVariable(variableName).getValue());
	}

	public Expression getGlobalVariable(String variableName) {
		return globalVariables.get(variableName);
	}

	public void addGlobalVariable(String variableName, Expression expression) {
		if (globalVariables.containsKey(variableName))
			if (globalVariables.get(variableName).getValue().getClass() == expression.getValue().getClass())
				globalVariables.put(variableName, expression);

		else
			System.out.println("Adding variable");
			globalVariables.put(variableName, expression);
	}

	private void setMainStatement(Statement main) {
		mainStatement.setProgram(this);
		this.mainStatement = main;
	}

	private Statement mainStatement;

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
}
