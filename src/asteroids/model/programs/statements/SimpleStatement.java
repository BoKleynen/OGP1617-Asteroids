package asteroids.model.programs.statements;

import asteroids.model.programs.Parent;

import java.util.Iterator;

/**
 * Created by Bo on 14/05/2017.
 */
public abstract class SimpleStatement<T extends Parent<T>> extends Statement<T> {

    public Iterator<Statement> iterator() {
        return new Iterator<Statement>() {
            boolean returned = false;

            @Override
            public boolean hasNext() {
                return ! returned;
            }

            @Override
            public Statement next() {
                returned = true;
                return getStatement();
            }
        };
    }

    private Statement getStatement() {
        return this;
    }
}
