package libwave.computation;

/**
 * Represents a complex number
 */
public class Complex {

    private double r;
    private double i;

    public Complex(double r, double i) {
        this.r = r;
        this.i = i;
    }

    public double getR() {
        return r;
    }

    public double getI() {
        return i;
    }

    public Complex add(Complex c) {
        return new Complex(this.r + c.r, this.i + c.i);
    }

    public Complex add(double r) {
        return new Complex(this.r + r, this.i);
    }

    public Complex mult(Complex c) {
        return new Complex(this.r * c.r - this.i * c.i, this.i * c.r + this.r * c.i);
    }

    public Complex mult(double r){
        return new Complex(this.r * r, this.i * r);
    }



    @Override
    public String toString() {
        return String.valueOf(r) + " + " + String.valueOf(i) + 'i';
    }
}
