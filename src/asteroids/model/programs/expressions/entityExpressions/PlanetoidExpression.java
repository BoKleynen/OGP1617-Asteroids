package asteroids.model.programs.expressions.entityExpressions;

import asteroids.model.Planetoid;
import asteroids.model.programs.expressions.Expression;

import java.util.Comparator;

/**
 * Created by Bo on 28/04/2017.
 */
public class PlanetoidExpression extends Expression<Planetoid> {
    @Override
    public Planetoid getValue() {
        return getShip()
                .getWorld()
                .getAllPlanetoids()
                .stream()
                .min(Comparator.comparingDouble(planetoid -> planetoid.getDistanceBetween(getShip())))
                .orElse(null);
    }
}
