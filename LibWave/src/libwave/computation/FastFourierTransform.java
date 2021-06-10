package libwave.computation;

public class FastFourierTransform {

    // TODO: <<REFACTOR>> Make WAVE_CACHE more independent by moving it to ComplexMatrixUtil
    /**
     * Buffer which stores all needed powers of complex values.
     * This tries to eliminate the need for excess use of trigonometric functions.
     * <p>
     * Note: not entirely sure if I'm using or generating this array correctly.
     */
    protected static Complex[] WAVE_CACHE;


    public static Complex[] FFT(double[] samples) {


        // W_x -> W[W.length / x]
        Complex[] buffer = new Complex[samples.length];


        // Check cache sufficiency...
        // Value count (below) is temporarily a variable just in case I mess something up.
        int cmplx_count = samples.length;

        if (WAVE_CACHE == null) {

            WAVE_CACHE = new Complex[cmplx_count];

            // this is the part where I'm not really sure I'm doing this correctly...
            // the goal with this segment is to generate all needed values of omega (w)

            for (int i = 0; i < cmplx_count; i++) {
                double theta = 2 * Math.PI * i; // This does not seem helpful, but it's what I had initially–so I'll leave it for now.

                WAVE_CACHE[i] = new Complex(Math.cos(theta), Math.sin(theta));
            }

        } else if (WAVE_CACHE.length < cmplx_count) {

            Complex[] buf = new Complex[cmplx_count];
            System.arraycopy(WAVE_CACHE, 0, buf, 0, WAVE_CACHE.length);

            for (int i = WAVE_CACHE.length; i < cmplx_count; i++) {

                // TODO: Reevaluate whether 2*pi is a useful initial value for theta...

                /*
                 Again, the usage of 2*Pi really doesn't seem like it would be helpful in this situation.
                 The value of the complex number remains the same through each iteration; any integer multiple of 2Pi
                 Edit: I tested it; hypothesis confirmed.
                */
                double theta = 2 * Math.PI * i;
                buf[i] = new Complex(Math.cos(theta), Math.sin(theta)); // As you would probably expect, the
            }

            WAVE_CACHE = buf;
        }

        return buffer;
    }

    /**
     * Sorts a given array, recursively separating values at even and odd indices.
     *
     * @param points audio samples
     * @return the concatenated result
     */
    public static double[] extractEO(double[] points) {

        // TODO: There is probably a more efficient way of achieving the same output this method produces (mathematically obtain index transformation).

        // Count will always be even because data is 2^n, n > 0.
        if (points.length > 3) {
            double[] ebuf = new double[points.length / 2];
            double[] obuf = new double[points.length / 2];

            for (int i = 0; i < points.length; i += 2) {
                ebuf[i / 2] = points[i];
                obuf[i / 2] = points[i + 1];
            }

            return concat(extractEO(ebuf), extractEO(obuf));
        }

        return points;
    }

    /**
     * Performs symmetric array concatenation.
     *
     * @param a an array
     * @param b an array with the same length as a
     * @return
     */
    public static double[] concat(double[] a, double[] b) {
        double[] buf = new double[2 * a.length];
        for (int i = 0; i < a.length; i++) {
            buf[i] = a[i];
            buf[i + a.length] = b[i];
        }
        return buf;
    }

}
