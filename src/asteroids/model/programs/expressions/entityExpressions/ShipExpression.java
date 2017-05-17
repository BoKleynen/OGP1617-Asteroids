package asteroids.model.programs.expressions.entityExpressions;

import asteroids.model.Ship;
import asteroids.model.programs.expressions.Expression;

import java.util.Comparator;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public class ShipExpression extends EntityExpression<Ship> {

    @Override
    public Ship getValue() {
        return getShip()
                .getWorld()
                .getAllShips()
                .stream()
                .filter(ship -> ship != getShip())
                .min(Comparator.comparingDouble(ship -> ship.getDistanceBetween(getShip())))
                .orElse(null);
    }

    @Override
    public Expression<Ship> clone() {
        Expression clone = new ShipExpression();
        clone.setStatement(getStatement());
        return clone;
    }
}
