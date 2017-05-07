package asteroids.model.programs.expressions.entityExpressions;

import asteroids.model.Ship;
import asteroids.model.programs.expressions.Expression;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public class SelfExpression extends EntityExpression<Ship> {

    @Override
    public Ship getValue() {
        return getShip();
    }
}
