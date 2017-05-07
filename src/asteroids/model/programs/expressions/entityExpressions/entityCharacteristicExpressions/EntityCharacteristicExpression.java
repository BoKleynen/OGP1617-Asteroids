package asteroids.model.programs.expressions.entityExpressions.entityCharacteristicExpressions;

import asteroids.model.Entity;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.valueExpressions.ValueExpression;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public abstract class EntityCharacteristicExpression extends Expression<Double> {

    public EntityCharacteristicExpression(Expression entity) {
        this.entity = entity;
    }

    private Expression<Entity> entity;

    public void setEntity (Expression<Entity> entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity.getValue();
    }
}
