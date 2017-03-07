package asteroids.model;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of immutable 2-dimensional vectors featuring an x and y value.
 *
 * Created by Bo on 28/02/2017.
 */
public class Vector {

    /**
     * Creates a new vector with a given x and y.
     */
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Compares two vectors, two vectors are equal if both their x- and y-components are equal.
     * 
     * @param other
     * @return	True is and only if the two vectors are equal
     * 			| result == ( (this.getX() == other.getX()) && (this.getY() == other.getY()) );
     */
    public boolean equals(Vector other) {
    	return getX() == other.getX() && getY() == other.getY();
    }

    private final double x;

    /**
     *
     * @return  The x-value of this vector.
     *          | this.x
     */
    @Basic @Immutable
    public double getX() {
        return x;
    }

    private final double y;

    /**
     *
     * @return  The y-value of this vector
     *          | this.y
     */
    @Basic @Immutable
    public double getY() {
        return y;
    }


    public Vector add(Vector u) {
        return new Vector(getX() + u.getX(), getY() + u.getY());
    }

    /**
     *
     * @return  The product of this vector with a given double as a new vector.
     *          | new.getX() == a * this.getX()
     *          | new.getY() == a * this.getY()
     */
    public Vector multiply(double a) {
        return new Vector(a * getX(), a * getY());
    }

    /**
     *
     * @param u
     * @return  The dot product of this vector with a given vector as a double.
     *          | getX() * u.getX() + getY() * u.getY()
     */
    public double dotProduct(Vector u) {
        return getX() * u.getX() + getY() * u.getY();
    }

    /**
     *
     * @return  The magnitude of this vector, computed as the square root of the dot produce of this vector
     *          with itself.
     *          | Math.sqrt(this.dotProduct(this))
     */
    public double getMagnitude() {
        return Math.sqrt(this.dotProduct(this));

    }

    /**
     *
     * @return  A new vector with magnitude 1, with the same orientation as this vector.
     *          | new.getMagnitude() = 1
     *          | v = new.multiply(this.getMagnitude())
     *          |   v.getX() == this.getX()
     *          |   v.getY() == this.getY()
     */
    public Vector normalize() {
        return multiply(1 / getMagnitude());
    }

    public double getDistance(Vector v) {
        return Math.sqrt(Math.pow(getX() - v.getX(), 2) + Math.pow(getY() - v.getY(),2));
    }

    /**
     *
     * @param v
     * @return
     *          | (getX() - v.getX(), getY() - v.getY())
     */
    public Vector getDifference(Vector v) {
        return new Vector(getX() - v.getX(), getY() - v.getY());
    }
}
