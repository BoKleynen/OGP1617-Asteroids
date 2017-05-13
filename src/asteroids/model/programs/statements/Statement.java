package asteroids.model.programs.statements;

import asteroids.model.Program;
import asteroids.model.programs.function.Function;

public abstract class Statement {

    public abstract void execute();

    private Program program;

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }
    
    protected boolean hasParentWhile() {
    	return (parentWhile != null);
    }
    
    protected While getParentWhile() {
    	return parentWhile;
    }
    
    
    protected void setParentWhile(While parent) {
    	this.parentWhile = parent;
    }
    
    protected While parentWhile = null;
    
    protected boolean hasParentFunction() {
    	return (parentFunction != null);
    }
    
    protected Function getParentFunction() {
    	return parentFunction;
    }
  
    protected void setParentFunction(Function parent) {
    	this.parentFunction = parent;
    }
    
    protected Function parentFunction = null;
    
    public Statement next() {
		if (!executed) {
			return this;
		}
		else
			return null;
	}

	public void resetExecuted() {
		executed = false;
	}

    protected boolean executed = false;
}
