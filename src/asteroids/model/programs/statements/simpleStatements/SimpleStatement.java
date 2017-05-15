package asteroids.model.programs.statements.simpleStatements;

import asteroids.model.programs.Parent;
import asteroids.model.programs.statements.Statement;

import java.util.Iterator;

/**
 * Created by Bo on 14/05/2017.
 */
public abstract class SimpleStatement<T extends Parent<T>> extends Statement<T> {

    public Iterator<Statement<T>> iterator() {
        return new Iterator<Statement<T>>() {
            boolean returned = false;

            @Override
            public boolean hasNext() {
                return ! returned;
            }

            @Override
            public Statement<T> next() {
                returned = true;
                return getStatement();
            }
        };
    }

    private Statement<T> getStatement() {
        return this;
    }
}
