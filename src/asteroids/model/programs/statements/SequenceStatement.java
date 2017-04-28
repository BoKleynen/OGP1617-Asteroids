package asteroids.model.programs.statements;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Bo on 28/04/2017.
 */
public class SequenceStatement extends Statement{
    public SequenceStatement(List<Statement> statements) {
        this.statements = statements;
    }

    private List<Statement> statements;

    @Override
    public void execute() {
        for (Statement statement : statements) {
            statement.execute();
        }
    }

}
