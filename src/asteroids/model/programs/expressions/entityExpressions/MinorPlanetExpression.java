package asteroids.model.programs.expressions.entityExpressions;

import asteroids.model.MinorPlanet;
import asteroids.model.programs.expressions.Expression;

import java.util.Comparator;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public class MinorPlanetExpression extends EntityExpression<MinorPlanet> {
    @Override
    public MinorPlanet getValue() {
        return getWorld()
                .getAllMinorPlanets()
                .stream()
                .min(Comparator.comparingDouble(minorPlanet -> minorPlanet.getDistanceBetween(getShip())))
                .orElse(null);
    }
}
