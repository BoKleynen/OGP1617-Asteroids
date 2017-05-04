package asteroids.model.programs.expressions.entityExpressions;

import asteroids.model.Entity;
import asteroids.model.programs.expressions.Expression;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Bo on 28/04/2017.
 */
public class AnyExpression extends EntityExpression<Entity> {

    @Override @SuppressWarnings("all")
    public Entity getValue() {
        return getWorld()
                .getAllEntities()
                .stream()
                .findAny()
                .get();
    }
}
