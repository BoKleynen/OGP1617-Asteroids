package asteroids.model.programs.statements;

import asteroids.model.programs.expressions.Expression;

/**
 * Created by Bo on 28/04/2017.
 *
 * TODO: implement expressions so they can be converted to boolean types
 */
public class If extends Statement{

    public If(Expression condition, Statement ifBody, Statement elseBody) {
        this.condition = condition;
        this.ifBody = ifBody;
        this.elseBody = elseBody;
        condition.setStatement(this);
    }

    private Expression<Boolean> condition;
    private Statement ifBody;
    private Statement elseBody;


    @Override
    public void execute() {
        if (condition.getValue())
            ifBody.execute();
        else
            elseBody.execute();
    }
}
