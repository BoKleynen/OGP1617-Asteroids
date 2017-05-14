package asteroids.model.programs.statements.actionStatements;

import asteroids.model.Program;
import asteroids.model.programs.statements.SimpleStatement;
import asteroids.model.programs.statements.Statement;
import be.kuleuven.cs.som.annotate.Basic;
import jdk.nashorn.internal.ir.annotations.Immutable;

/**
 * Created by Bo on 27/04/2017.
 */
public abstract class ActionStatement extends SimpleStatement<Program> {

    private static final double executionTime = 0.2;

    @Basic @Immutable
    public static double getExecutionTime() {
        return executionTime;
    }

}
