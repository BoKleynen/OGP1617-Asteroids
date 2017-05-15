package asteroids.model.programs;

import asteroids.model.Ship;

/**
 * Created by Bo on 13/05/2017.
 */
public interface Child<T extends Parent<T>> {

    T getParent();

    void setParent(T parent);

    default Ship getShip() {
        return getParent().getShip();
    }
}
