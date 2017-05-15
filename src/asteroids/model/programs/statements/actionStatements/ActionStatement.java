package asteroids.model.programs.statements.actionStatements;

import asteroids.model.Program;
import be.kuleuven.cs.som.annotate.Basic;
import jdk.nashorn.internal.ir.annotations.Immutable;

/**
 * Created by Bo on 27/04/2017.
 */
public abstract class ActionStatement extends asteroids.model.programs.statements.Statement<Program> {

    private static final double executionTime = 0.2;

    @Basic @Immutable
    public static double getExecutionTime() {
        return executionTime;
    }

    @Override
    public boolean isValidFunctionStatement() {
        return false;
    }
}
