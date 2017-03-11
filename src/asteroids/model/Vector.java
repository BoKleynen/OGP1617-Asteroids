package asteroids.model;

import be.kuleuven.cs.som.annotate.*;

import java.util.Objects;

/**
 * A class of immutable 2-dimensional vectors featuring an x and y component of type double.
 *
 * Created by Bo on 28/02/2017.
 */
public class Vector extends Object{

    /**
     * Creates a new vector with a given x and y.
     *
     * @Post    The x and y value of the new Vector are set to x and y respectively
     *          |new.x == x
     *          |new.y == y
     */
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Compares two vectors, two vectors are equal if and only if both their x- and y-components are equal.
     *
     * @param other The Vector with which this Vector has to be compared.
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


    /**
     * Computes the sum of this Vector and the specified Vector and returns the result as a new Vector.
     *
     * @param u The Vector which has to be added to this Vector
     * @return  The sum of this vector with u
     *          | result.getX() == this.getX() + u.getX()
     *          | result.getY() == this.getY() + u.getY()
     */
    public Vector add(Vector u) {
        return new Vector(getX() + u.getX(), getY() + u.getY());
    }

    /**
     * Computes the product of this Vector with the given double value and returns the result as a new Vector
     *
     * @param a The value with which this Vector has to be multiplied.
     * @return  The product of this vector with a given double as a new vector.
     *          | new.getX() == a * this.getX()
     *          | new.getY() == a * this.getY()
     */
    public Vector multiply(double a) {
        return new Vector(a * getX(), a * getY());
    }

    /**
     * Computes the dot product of this Vector with the given Vector u and returns the result as a double.
     *
     * @param u
     * @return  The dot product of this vector with a given vector as a double.
     *          | getX() * u.getX() + getY() * u.getY()
     */
    public double dotProduct(Vector u) {
        return getX() * u.getX() + getY() * u.getY();
    }

    /**
     * Computes the magnitude of this Vector and returns the result as a double.
     *
     * @return  The magnitude of this vector, computed as the square root of the dot product of this vector
     *          with itself.
     *          | result == Math.sqrt(this.dotProduct(this))
     */
    public double getMagnitude() {
        return Math.sqrt(this.dotProduct(this));

    }

    /**
     * Normalizes this Vector and returns the result as a new Vector. This new Vector is a Vector with magnitude 1
     * and with the same orientation as this Vector.
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

    /**
     * Computes the Distance between this Vector and the given Vector.
     *
     * @param v The Vector between which and this the distance has to be computed
     * @return  The distance between this Vector and the given Vector.
     *          | result == Math.sqrt(Math.pow(getX() - v.getX(), 2) + Math.pow(getY() - v.getY(),2))
     */
    public double getDistance(Vector v) {
        return Math.sqrt(Math.pow(getX() - v.getX(), 2) + Math.pow(getY() - v.getY(),2));
    }

    /**
     * Computes the difference of the respective components of this Vector and the given Vector
     *
     * @return  The difference of the x coordinates of this Vector and the given Vector and the difference of the
     *          y coordinates of this Vector and the given Vector
     *          | (getX() - v.getX(), getY() - v.getY())
     */
    public Vector getDifference(Vector v) {
        return new Vector(getX() - v.getX(), getY() - v.getY());
    }
}
