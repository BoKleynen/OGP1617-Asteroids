package asteroids.model.programs.expressions.entityExpressions;

import asteroids.model.Bullet;
import asteroids.model.programs.expressions.Expression;

import java.util.Comparator;

/**
 * Created by Bo on 28/04/2017.
 */
public class BulletExpression extends EntityExpression<Bullet> {

    @Override
    public Bullet getValue() {
        return getWorld()
                .getAllBullets()
                .stream()
                .min(Comparator.comparingDouble(bullet -> bullet.getDistanceBetween(getShip())))
                .orElse(null);
    }
}
