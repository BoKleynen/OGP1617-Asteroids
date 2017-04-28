package asteroids.model.programs.expressions.entityCharacteristicExpressions;

import asteroids.model.programs.expressions.Expression;

/**
 * Created by Bo on 28/04/2017.
 */
public class GetVX extends EntityCharacteristicExpression {

    public GetVX(Expression entity) {
        super(entity);
    }

    @Override
    public Object getValue() {
        return getEntity().getVelocity().getX();
    }
}
