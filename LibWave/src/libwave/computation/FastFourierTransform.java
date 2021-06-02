package libwave.computation;

public class FastFourierTransform {

    public static Complex[] FFT(double[] samples) {
        Complex[] buffer = new Complex[samples.length];
        Complex[] W = new Complex[samples.length];

        // W_x -> W[W.length / x]

        for (int i = 0; i < samples.length; i++) {
            double theta = 2 * Math.PI * i;
            W[i] = new Complex(Math.cos(theta), Math.sin(theta));
        }

        return buffer;
    }

    public static double[] extractEO(double[] points) {
        // count will always be even because data is 2^n

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

    public static double[] concat(double[] a, double[] b) {
        double[] buf = new double[2 * a.length];
        for (int i = 0; i < a.length; i++) {
            buf[i] = a[i];
            buf[i + a.length] = b[i];
        }
        return buf;
    }
}
