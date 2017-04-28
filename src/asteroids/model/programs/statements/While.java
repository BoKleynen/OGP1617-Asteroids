package asteroids.model.programs.statements;

import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.statements.Statement;

/**
 * Created by Bo on 28/04/2017.
 *
 * TODO: implement expressions so they can be converted to boolean types
 */
public class While extends Statement {

    public While(Expression condition, Statement body) {
        this.body = body;
    }

    private boolean condition;

    private Statement body;

    @Override
    public void execute() {
        while (condition) {
            body.execute();
        }
    }
}
