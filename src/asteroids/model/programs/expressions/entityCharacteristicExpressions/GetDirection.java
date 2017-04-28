package asteroids.model.programs.expressions.entityCharacteristicExpressions;

import asteroids.model.programs.expressions.Expression;

/**
 * Created by Bo on 28/04/2017.
 */
public class GetDirection extends Expression<Double> {

    @Override
    public Double getValue() {
        return getStatement().getProgram().getShip().getOrientation();
    }
}
