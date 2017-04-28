package asteroids.model.programs.statements;

import asteroids.model.Program;

public abstract class Statement {

    public abstract void execute();

    private Program program;

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }
}
