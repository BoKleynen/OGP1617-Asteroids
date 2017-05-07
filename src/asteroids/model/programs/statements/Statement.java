package asteroids.model.programs.statements;

import java.lang.instrument.IllegalClassFormatException;

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
    
    /*
     * Only while statements can be parent statements.
     */
    protected void setParentWhile(While parent) {
    	this.parentWhile = parent;
    }
    
    protected While parentWhile = null;
}
