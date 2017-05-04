package asteroids.model.programs.expressions;

import asteroids.model.Ship;
import asteroids.model.programs.statements.Statement;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * Created by Bo on 28/04/2017.
 */
public abstract class Expression <T> {

    public abstract T getValue();

    @Override
    public String toString() {
        return getValue().toString();
    }

    private Statement statement;

    public Statement getStatement() {
        return statement;
    }

    @Raw
    public void setStatement(Statement statement) {
        this.statement = statement;
    }
}
