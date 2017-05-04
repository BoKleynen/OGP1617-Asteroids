package asteroids.model.programs.expressions.entityExpressions;

import asteroids.model.Ship;
import asteroids.model.programs.expressions.Expression;

import java.util.Comparator;

/**
 * Created by Bo on 28/04/2017.
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

}
