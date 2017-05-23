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

    @Override
    public Expression<Ship> clone() {
        Expression clone = new SelfExpression();
        clone.setStatement(getStatement());
        return clone;
    }
}
