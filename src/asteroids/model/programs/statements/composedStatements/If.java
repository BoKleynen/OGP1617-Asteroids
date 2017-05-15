package asteroids.model.programs.statements.composedStatements;

import asteroids.model.Program;
import asteroids.model.programs.Parent;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.statements.Statement;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Bo on 28/04/2017.
 *
 * TODO: implement expressions so they can be converted to boolean types
 */
public class If<T extends Parent<T>> extends Statement<T> {

    public If(Expression<Boolean> condition, Statement<T> ifBody, Statement<T> elseBody) {
        this.condition = condition;
        this.ifBody = ifBody;
        this.elseBody = elseBody;
        condition.setStatement(this);
    }

    private Expression<Boolean> condition;
    private Statement<T> ifBody;
    private Statement<T> elseBody;

    public Statement<T> getIfBody() {
        return ifBody;
    }

    public Statement<T> getElseBody() {
        return elseBody;
    }

    @Override
    public void execute() {
        throw new RuntimeException("something went terribly wrong");
    }

    @Override
    public void setParent(T parent) {
        super.setParent(parent);
        ifBody.setParent(parent);

        if (elseBody != null)
            elseBody.setParent(parent);
    }

    @Override
    public Iterator<Statement<T>> iterator() {
        return new Iterator<Statement<T>>() {

            Iterator<Statement<T>> bodyIterator = condition.getValue() ? ifBody.iterator()
                    : (elseBody != null ? elseBody.iterator() : null);

            @Override
            public boolean hasNext() {
                return bodyIterator != null && bodyIterator.hasNext();
            }

            @Override
            public Statement<T> next() {
                if (! hasNext())
                    throw new NoSuchElementException();

                return bodyIterator.next();
            }
        };
    }

    @Override
    public boolean isValidFunctionStatement() {
        return ifBody.isValidFunctionStatement() && (elseBody == null || elseBody.isValidFunctionStatement());
    }
}
