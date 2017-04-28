package asteroids.model.programs.statements;

import asteroids.model.programs.expressions.Expression;

/**
 * Created by Bo on 28/04/2017.
 *
 * TODO: implement expressions so they can be converted to boolean types
 */
public class IfStatement extends Statement{

    public IfStatement(Expression condition, Statement ifBody, Statement elseBody) {

        this.ifBody = ifBody;
        this.elseBody = elseBody;
    }

    private boolean condition;
    private Statement ifBody;
    private Statement elseBody;


    @Override
    public void execute() {
        if (condition)
            ifBody.execute();
        else
            elseBody.execute();
    }
}
