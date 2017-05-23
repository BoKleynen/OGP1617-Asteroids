package asteroids.model.programs.statements;

import asteroids.model.programs.Child;
import asteroids.model.programs.Parent;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public abstract class Statement<T extends Parent<T>> implements Cloneable, Child<T> {

    public abstract void execute();

    private T parent;

    @Override
    public T getParent() {
        return parent;
    }

    @Override
    public void setParent(T parent) {
        this.parent = parent;
    }


    public Iterator<Statement<T>> iterator() {
        return new Iterator<Statement<T>>() {
            boolean returned = false;

            @Override
            public boolean hasNext() {
                return !returned;
            }

            @Override
            public Statement<T> next() {
                if (! hasNext())
                    throw new NoSuchElementException();

                returned = true;
                return Statement.this;
            }
        };
    }

    public abstract boolean isValidFunctionStatement();

    @Override
    public abstract Statement<T> clone() throws CloneNotSupportedException;
}
