package asteroids.model.programs.statements;

import asteroids.model.Program;
import asteroids.model.programs.Child;
import asteroids.model.programs.Parent;
import asteroids.model.programs.function.Function;

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class Statement<T extends Parent<T>> implements Cloneable, Child<T> {

    public abstract void execute();

    T parent;

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
