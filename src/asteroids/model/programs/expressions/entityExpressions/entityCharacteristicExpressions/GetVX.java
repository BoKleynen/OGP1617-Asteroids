package asteroids.model.programs.expressions.entityExpressions.entityCharacteristicExpressions;

import asteroids.model.programs.expressions.Expression;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public class GetVX extends EntityCharacteristicExpression {

    public GetVX(Expression entity) {
        super(entity);
    }

    @Override
    public Double getValue() {
        return getEntity().getVelocity().getX();
    }
}
