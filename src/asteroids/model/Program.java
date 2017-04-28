package asteroids.model;

import asteroids.model.util.exceptions.NotEnoughTimeRemainingException;
import asteroids.model.programs.Function;
import asteroids.model.programs.statements.Statement;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

import java.util.List;
import java.util.ArrayList;

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
		this.functions = functions;
	}
	
	private List<Function> functions;
	
	public Statement getMainStatement() {
		return this.mainStatement;
	}
	
	private void setMainStatement(Statement main) {
		this.mainStatement = main;
	}
	
	private Statement mainStatement;

	public void pause() {

	}

}
