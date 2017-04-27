package asteroids.model.exceptions;

/**
 * Created by Bo on 27/04/2017.
 */
public class NotEnoughTimeRemainingException extends RuntimeException {

    public NotEnoughTimeRemainingException() {
        super();
    }

    public NotEnoughTimeRemainingException(String string) {
        super(string);
    }
}
