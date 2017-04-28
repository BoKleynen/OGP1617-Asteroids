package asteroids.model.programs.statements;

import asteroids.model.programs.expressions.Expression;

/**
 * Created by Bo on 28/04/2017.
 */
public class Return extends Statement {

    public Return(Expression value) {
        this.value = value;
    }

    private Expression value;

    @Override
    public void execute() {

    }
}
