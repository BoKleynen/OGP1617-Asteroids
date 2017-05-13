package asteroids.model.programs.statements;

import asteroids.model.Program;
import asteroids.model.programs.Child;
import asteroids.model.programs.Parent;
import asteroids.model.programs.function.Function;

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

    public Statement next() {
		if (!executed) {
			return this;
		}
		else
			return null;
	}

	public void resetExecuted() {
		executed = false;
	}

    protected boolean executed = false;
}
