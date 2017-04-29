package asteroids.model.programs.expressions.entityExpressions;

import asteroids.model.Entity;
import asteroids.model.programs.expressions.Expression;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Bo on 28/04/2017.
 */
public class AnyExpression extends Expression<Entity> {

    @Override
    public Entity getValue() {

        ArrayList<Entity> entities = new ArrayList<>(getStatement().getProgram().getShip().getWorld().getAllEntities());
        for (int i = 0; i < entities.size()-2; i++) {
            if (Math.random() <= 1.0 / (entities.size()-1))
                return entities.get(i);
        }

        return entities.get(entities.size()-1);
    }
}
