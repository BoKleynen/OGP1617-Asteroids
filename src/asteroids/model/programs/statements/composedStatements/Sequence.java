package asteroids.model.programs.statements.composedStatements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import asteroids.model.Program;
import asteroids.model.programs.Parent;
import asteroids.model.programs.function.Function;
import asteroids.model.programs.statements.Statement;

/**
 * Created by Bo on 28/04/2017.
 */
public class Sequence<T extends Parent<T>> extends Statement<T> {

    public Sequence(List<Statement<T>> statements) {
        this.statements = statements;
    }

    private List<Statement<T>> statements;

    @Override
    public void execute() {
        throw new RuntimeException("something went terribly wrong");
    }

    public List<Statement<T>> getStatements() {
        return statements;
    }

    @Override
    public void setParent(T parent) {
        super.setParent(parent);

        for (Statement<T> statement : statements) {
            statement.setParent(parent);
        }
    }

    @Override
    public Iterator<Statement<T>> iterator() {
        return new Iterator<Statement<T>>() {

            int index = 0;
            Iterator<Statement<T>> subIterator = statements.get(index++).iterator();

            @Override
            public boolean hasNext() {
                if (subIterator.hasNext())
                    return true;

                else {
                    for (int i=index; i<statements.size(); i++) {
                        index++;
                        subIterator = statements.get(i).iterator();
                        if (subIterator.hasNext()) {
                            return true;
                        }
                    }
                    index = statements.size();
                    return false;
                }
            }

            @Override
            public Statement<T> next() throws NoSuchElementException {
                if (! hasNext())
                    throw new NoSuchElementException();

                return subIterator.next();
            }
        };
    }

    @Override
    public boolean isValidFunctionStatement() {
        for (Statement statement : statements) {
            if (! statement.isValidFunctionStatement())
                return false;
        }
        return true;
    }

    @Override
    public Statement<T> clone() throws CloneNotSupportedException {
        List<Statement<T>> statements = new ArrayList<>();
        for (Statement<T> statement : this.statements) {
            statements.add(statement.clone());
        }

        Statement<T> clone = new Sequence<>(statements);
        clone.setParent(getParent());
        return clone;
    }
}
