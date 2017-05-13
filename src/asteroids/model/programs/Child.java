package asteroids.model.programs;

/**
 * Created by Bo on 13/05/2017.
 */
public interface Child<T extends Parent<T>> {

    T getParent();

    void setParent(T parent);
}
