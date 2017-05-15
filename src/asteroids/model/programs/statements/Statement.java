package asteroids.model.programs.statements;

import asteroids.model.Program;
import asteroids.model.programs.Child;
import asteroids.model.programs.Parent;
import asteroids.model.programs.function.Function;

import java.util.Iterator;

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


    public abstract Iterator<Statement<T>> iterator();

    public abstract boolean isValidFunctionStatement();

//    @Override
//    protected abstract Statement<T> clone() throws CloneNotSupportedException;
}
