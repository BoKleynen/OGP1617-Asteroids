package asteroids.model.programs.expressions.entityExpressions.entityCharacteristicExpressions;

import asteroids.model.Entity;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.valueExpressions.ValueExpression;
import asteroids.model.programs.statements.Statement;

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

    public Expression<Entity> getEntity() {
        return entity;
    }

    @Override
    public void setStatement(Statement statement) {
        super.setStatement(statement);
        entity.setStatement(statement);
    }
}
