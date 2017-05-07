package asteroids.model.programs.expressions.entityExpressions;

import asteroids.model.Entity;
import asteroids.model.programs.expressions.Expression;

import java.util.ArrayList;
import java.util.Set;

/**
 * @author  Bo Kleynen & Yrjo Koyen
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
