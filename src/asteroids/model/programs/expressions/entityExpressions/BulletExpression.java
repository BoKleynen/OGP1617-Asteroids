package asteroids.model.programs.expressions.entityExpressions;

import asteroids.model.Bullet;
import asteroids.model.programs.expressions.Expression;

import java.util.Comparator;

/**
 * @author  Bo Kleynen & Yrjo Koyen
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
