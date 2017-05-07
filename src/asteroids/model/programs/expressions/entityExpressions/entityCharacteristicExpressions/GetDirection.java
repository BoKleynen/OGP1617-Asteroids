package asteroids.model.programs.expressions.entityExpressions.entityCharacteristicExpressions;

import asteroids.model.Ship;
import asteroids.model.programs.expressions.entityExpressions.SelfExpression;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public class GetDirection extends EntityCharacteristicExpression {

    public GetDirection() {
        super(new SelfExpression());
    }

    @Override
    public Double getValue() {
        return ((Ship) getEntity()).getOrientation();
    }
}
