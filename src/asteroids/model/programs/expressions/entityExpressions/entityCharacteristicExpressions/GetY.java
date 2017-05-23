package asteroids.model.programs.expressions.entityExpressions.entityCharacteristicExpressions;

import asteroids.model.programs.expressions.Expression;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public class GetY extends EntityCharacteristicExpression {

    public GetY(Expression entity) {
        super(entity);
    }

    @Override
    public Double getValue() {
        return getEntity().getValue().getPosition().getY();
    }

    @Override
    public Expression<Double> clone() {
        Expression<Double> clone = new GetY(getEntity().clone());
        clone.setStatement(getStatement());
        return clone;
    }
}
