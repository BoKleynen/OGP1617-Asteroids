package asteroids.model.programs.expressions.entityExpressions;

import asteroids.model.Ship;
import asteroids.model.programs.expressions.Expression;

/**
 * Created by Bo on 28/04/2017.
 */
public class SelfExpression extends EntityExpression<Ship> {

    @Override
    public Ship getValue() {
        return getShip();
    }
}
