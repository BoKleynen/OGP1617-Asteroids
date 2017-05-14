package asteroids.model.programs.statements;

import asteroids.model.programs.expressions.Expression;

/**
 * Created by Bo on 28/04/2017.
 */
public class Print extends SimpleStatement {

    public Print(Expression expression) {
        this.expression = expression;
        expression.setStatement(this);
    }

    private Expression expression;

    @Override
    public void execute() {
        getParent().addPrintedObject(expression.getValue());
        System.out.println(expression.toString());
        executed = true;
    }

}
