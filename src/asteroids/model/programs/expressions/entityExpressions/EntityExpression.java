package asteroids.model.programs.expressions.entityExpressions;

import asteroids.model.Entity;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.model.programs.expressions.Expression;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public abstract class EntityExpression<T extends Entity> extends Expression<T> {

    public Ship getShip() {
        return getStatement().getProgram().getShip();
    }

    public World getWorld() {
        return getShip().getWorld();
    }
}
