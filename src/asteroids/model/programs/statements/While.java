package asteroids.model.programs.statements;

import asteroids.model.programs.expressions.Expression;

/**
 * Created by Bo on 28/04/2017.
 *
 * TODO: implement expressions so they can be converted to boolean types
 */
public class While extends Statement {

    public While(Expression condition, Statement body) {
        this.condition = condition;
        this.body = body;
    }

    private Expression<Boolean> condition;;

    private Statement body;

    @Override
    public void execute() {
        while (condition.getValue()) {
            body.execute();
        }
    }
}
