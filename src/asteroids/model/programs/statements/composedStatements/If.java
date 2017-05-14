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
public class If extends Statement {

    public If(Expression<Boolean> condition, Statement ifBody, Statement elseBody) {
        this.condition = condition;
        this.ifBody = ifBody;
        this.elseBody = elseBody;
        condition.setStatement(this);
    }

    private Expression<Boolean> condition;
    private Statement ifBody;
    private Statement elseBody;

    public Statement getIfBody() {
        return ifBody;
    }

    public Statement getElseBody() {
        return elseBody;
    }

    @Override
    public void execute() {
        throw new RuntimeException("something went terribly wrong");
    }

    @Override
    public void setParent(Parent parent) {
        super.setParent(parent);
        ifBody.setParent(parent);

        if (elseBody != null)
            elseBody.setParent(parent);
    }

    @Override
    public Iterator<Statement> iterator() {
        return new Iterator<Statement>() {

            Iterator<Statement> bodyIterator = condition.getValue() ? ifBody.iterator()
                    : (elseBody != null ? elseBody.iterator() : null);

            @Override
            public boolean hasNext() {
                return bodyIterator != null && bodyIterator.hasNext();
            }

            @Override
            public Statement next() {
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
