package asteroids.model.programs.expressions.entityExpressions;

import asteroids.model.Planetoid;
import asteroids.model.programs.expressions.Expression;

import java.util.Comparator;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public class PlanetoidExpression extends EntityExpression<Planetoid> {
    @Override
    public Planetoid getValue() {
        return getWorld()
                .getAllPlanetoids()
                .stream()
                .min(Comparator.comparingDouble(planetoid -> planetoid.getDistanceBetween(getShip())))
                .orElse(null);
    }
}
