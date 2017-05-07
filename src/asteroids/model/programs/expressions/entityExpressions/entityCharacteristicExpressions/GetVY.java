package asteroids.model.programs.expressions.entityExpressions.entityCharacteristicExpressions;

import asteroids.model.programs.expressions.Expression;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public class GetVY extends EntityCharacteristicExpression {

    public GetVY(Expression entity) {
        super(entity);
    }

    @Override
    public Double getValue() {
        return getEntity().getVelocity().getY();
    }
}
