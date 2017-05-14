package asteroids.model.programs.statements;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import asteroids.model.Program;
import asteroids.model.programs.Parent;
import asteroids.model.programs.function.Function;

/**
 * Created by Bo on 28/04/2017.
 */
public class Sequence extends Statement {

    public Sequence(List<Statement> statements) {
        this.statements = statements;
    }

    private List<Statement> statements;

    @Override
    public void execute() {
        throw new RuntimeException("something went terribly wrong");
    }

    public List<Statement> getStatements() {
        return statements;
    }

    @Override
    public void setParent(Parent parent) {
        super.setParent(parent);

        for (Statement statement : statements) {
            statement.setParent(parent);
        }
    }

    public Iterator<Statement> iterator() {
        return new Iterator<Statement>() {

            int index = 0;
            Iterator<Statement> subIterator = statements.get(index).iterator();

            @Override
            public boolean hasNext() {
                return subIterator.hasNext() || index + 1 < statements.size();
            }

            @Override
            public Statement<? extends Parent<?>> next() throws NoSuchElementException {
                if (! hasNext())
                    throw new NoSuchElementException();

                if (subIterator.hasNext()) {
                    return subIterator.next();
                }

                else {
                    index++;
                    subIterator = statements.get(index).iterator();
                    return subIterator.next();
                }

            }
        };
    }
}
