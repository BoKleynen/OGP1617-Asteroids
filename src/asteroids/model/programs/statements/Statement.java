package asteroids.model.programs.statements;

import asteroids.model.Program;

public abstract class Statement {

    private Program program;

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }
}
