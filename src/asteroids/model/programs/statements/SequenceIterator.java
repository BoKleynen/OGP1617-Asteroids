package asteroids.model.programs.statements;

import java.util.Iterator;

/**
 * Created by Bo on 06/05/2017.
 */
public interface SequenceIterator extends Iterator<Statement> {
    void pause();

    void resume();
}
