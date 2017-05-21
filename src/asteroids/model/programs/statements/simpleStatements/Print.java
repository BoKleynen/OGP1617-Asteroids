package asteroids.model.programs.statements.simpleStatements;

import asteroids.model.programs.Parent;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.statements.Statement;

/**
 * Created by Bo on 28/04/2017.
 */
public class Print<T extends Parent<T>> extends Statement<T> {

    public Print(Expression expression) {
        this.expression = expression;
    }

    private Expression expression;

    @Override
    public void execute() {
        expression.setStatement(this);
        getParent().addPrintedObject(expression.getValue());
        System.out.println(expression.toString());
    }

    @Override
    public boolean isValidFunctionStatement() {
        return false;
    }

    @Override
    public Statement<T> clone() throws CloneNotSupportedException {
        return new Print<>(expression.clone());
    }
}
