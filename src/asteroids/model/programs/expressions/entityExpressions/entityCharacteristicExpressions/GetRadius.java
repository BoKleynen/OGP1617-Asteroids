package asteroids.model.programs.expressions.entityExpressions.entityCharacteristicExpressions;

import asteroids.model.programs.expressions.Expression;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public class GetRadius extends EntityCharacteristicExpression {

    public GetRadius(Expression entity) {
        super(entity);
    }

    @Override
    public Double getValue() {
        return getEntity().getValue().getRadius();
    }

    @Override
    public Expression<Double> clone() {
        Expression clone = new GetRadius(getEntity().clone());
        clone.setStatement(getStatement());
        return clone;
    }
}
