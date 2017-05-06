package asteroids.model.programs.expressions.entityExpressions.entityCharacteristicExpressions;

import asteroids.model.Ship;
import asteroids.model.programs.expressions.entityExpressions.SelfExpression;

/**
 * Created by Bo on 28/04/2017.
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
