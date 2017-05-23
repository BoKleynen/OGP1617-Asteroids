package asteroids.model.programs;

import asteroids.model.Ship;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public interface Child<T extends Parent<T>> {

    T getParent();

    void setParent(T parent);

    default Ship getShip() {
        return getParent().getShip();
    }
}
