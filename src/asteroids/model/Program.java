package asteroids.model;

import asteroids.model.programs.Function;
import asteroids.model.programs.Statement;
import java.util.List;
import java.util.ArrayList;

public class Program {
	
	public Program(List<Function> functions, Statement main) {
		setFunctions(functions);
		setMainStatement(main);
	}

	public List<Object> execute(double time) {
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

}
