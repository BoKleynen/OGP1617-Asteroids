package asteroids.model.programs.expressions.entityExpressions;

import asteroids.model.Asteroid;
import asteroids.model.programs.expressions.Expression;

import java.util.Comparator;

/**
 * Created by Bo on 28/04/2017.
 */
public class AsteroidExpression extends Expression<Asteroid> {
    @Override
    public Asteroid getValue() {
        return getShip()
                .getWorld()
                .getAllAsteroids()
                .stream()
                .min(Comparator.comparingDouble(asteroid -> asteroid.getDistanceBetween(getShip())))
                .orElse(null);
    }
}
