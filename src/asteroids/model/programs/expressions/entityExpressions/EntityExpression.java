package asteroids.model.programs.expressions.entityExpressions;

import asteroids.model.Entity;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.model.programs.expressions.Expression;

/**
 * Created by Bo on 04/05/2017.
 */
public abstract class EntityExpression<T extends Entity> extends Expression<T> {

    public Ship getShip() {
        return getStatement().getProgram().getShip();
    }

    public World getWorld() {
        return getShip().getWorld();
    }
}
