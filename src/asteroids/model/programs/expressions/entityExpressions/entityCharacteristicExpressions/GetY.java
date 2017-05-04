package asteroids.model.programs.expressions.entityExpressions.entityCharacteristicExpressions;

import asteroids.model.programs.expressions.Expression;

/**
 * Created by Bo on 28/04/2017.
 */
public class GetY extends EntityCharacteristicExpression {

    public GetY(Expression entity) {
        super(entity);
    }

    @Override
    public Double getValue() {
        return getEntity().getPosition().getY();
    }
}
