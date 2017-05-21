package asteroids.model.programs.statements.simpleStatements;

import asteroids.model.programs.Parent;
import asteroids.model.programs.statements.Statement;
import asteroids.model.util.exceptions.BreakException;

import java.util.Iterator;

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
        return this;
    }

    @Override
    public Iterator<Statement<T>> iterator() {
        return new Iterator<Statement<T>>() {
            boolean returned = false;

            @Override
            public boolean hasNext() {
                if (returned)
                    return false;

                else {
                    returned = true;
                    throw new BreakException();
                }
            }

            @Override
            public Statement<T> next() {
                return null;
            }
        };
    }
}
