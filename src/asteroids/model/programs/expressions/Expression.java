package asteroids.model.programs.expressions;

import asteroids.model.Ship;
import asteroids.model.programs.statements.Statement;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public abstract class Expression <T> implements Cloneable {

    public abstract T getValue();

    @Override
    public String toString() {
        Object value = getValue();
        return value == null ? null : getValue().toString();
    }

    private Statement statement;

    public Statement getStatement() {
        return statement;
    }

    @Raw
    public void setStatement(Statement statement) {
        System.out.println("setting statement");
        this.statement = statement;
    }

    @Override
    public abstract Expression<T> clone();
}
