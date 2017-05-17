package asteroids.model.programs.expressions.entityExpressions.entityCharacteristicExpressions;

import asteroids.model.Ship;
import asteroids.model.programs.expressions.Expression;
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
        return ((Ship) getEntity().getValue()).getOrientation();
    }

    @Override
    public Expression<Double> clone() {
        Expression clone = new GetDirection();
        clone.setStatement(getStatement());
        return clone;
    }
}
