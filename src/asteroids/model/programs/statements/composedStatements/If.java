package asteroids.model.programs.statements.composedStatements;

import asteroids.model.programs.Parent;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.statements.Statement;

import java.util.Iterator;

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

    private Iterator<Statement<T>> bodyIterator;

    @Override
    public void execute() {
        if (condition.getValue()) {
            bodyIterator = ifBody.iterator();
        }

        else if (elseBody != null) {
            bodyIterator = elseBody.iterator();
        }

        while (bodyIterator != null && bodyIterator.hasNext()) {
            bodyIterator.next().execute();
        }
    }

    @Override
    public void setParent(T parent) {
        super.setParent(parent);
        ifBody.setParent(parent);

        if (elseBody != null)
            elseBody.setParent(parent);
    }

    @Override
    public boolean isValidFunctionStatement() {
        return ifBody.isValidFunctionStatement() && (elseBody == null || elseBody.isValidFunctionStatement());
    }

    @Override
    public Statement<T> clone() throws CloneNotSupportedException {
        Statement<T> clone = new If<>(condition.clone(), ifBody.clone(), elseBody == null ? null : elseBody.clone());
        clone.setParent(getParent());
        return clone;
    }
}
