package asteroids.model.programs.statements.simpleStatements;

import asteroids.model.programs.Parent;
import asteroids.model.programs.statements.Statement;
import asteroids.model.util.exceptions.BreakException;

/**
 * Created by Bo on 28/04/2017.
 */
public class Break<T extends Parent<T>> extends Statement<T> {

    public Break() {

    }

    @Override
    public void execute() {
        throw new BreakException();
    }

    @Override
    public boolean isValidFunctionStatement() {
        return true;
    }

    @Override
    public Statement<T> clone() throws CloneNotSupportedException {
        Statement<T> clone = new Break<>();
        clone.setParent(getParent());
        return clone;
    }
}
