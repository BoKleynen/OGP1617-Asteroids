package asteroids.model.programs.expressions.entityCharacteristicExpressions;

import asteroids.model.Entity;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.valueExpressions.ValueExpression;

/**
 * Created by Bo on 28/04/2017.
 */
public abstract class EntityCharacteristicExpression extends Expression {

    public EntityCharacteristicExpression(Expression entity) {
        this.entity = entity;
    }

    private Expression<Entity> entity;

    public Entity getEntity() {
        return entity.getValue();
    }
}
