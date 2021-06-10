package libwave.computation;

public class ComplexMatrixUtil {

    // NOTE: if an element in a complex matrix is null, assume it to be zero.
    // This will save memory!
    // Edit: This left a sour feeling in my stomach, and I don't know if it's justified or not.

    public static Complex[][] constructIdentityMtx(int size) {
        Complex[][] buffer = new Complex[size][size];

        for (int i = 0; i < size; i++) {
            buffer[i][i] = new Complex(1, 0);
        }

        return buffer;
    }

    /**
     * @param size
     * @return
     */
    public static Complex[][] constructDiagonalMtx(int size) {

        Complex[][] buffer = new Complex[size][size];

        for (int i = 0; i < size; i++) {
            if (i == 0) {
                buffer[i][i] = new Complex(1, 0);
                continue;
            }
            buffer[i][i] = FastFourierTransform.WAVE_CACHE[i];
        }

        return buffer;
    }


    /**
     * Merges parameters according to the below diagram
     * ⌈ A | B ⌉
     * |–––|–––|
     * ⌊ C | D ⌋
     *
     * @param a
     * @param b
     * @param c
     * @param d
     * @return
     */
    public static Complex[][] quadMerge(Complex[][] a, Complex[][] b, Complex[][] c, Complex[][] d) {

        Complex[][] buffer = new Complex[a.length + c.length][a[0].length + b[0].length];

        for (int row = 0; row < a.length; row++) {
            System.arraycopy(a[row], 0, buffer[row], 0, a[row].length);
            System.arraycopy(b[row], 0, buffer[row], a[0].length, b[row].length);
        }

        for (int row = a.length; row < c.length + c.length; row++) {
            System.arraycopy(c[row], 0, buffer[row], 0, c[row].length);
            System.arraycopy(d[row], 0, buffer[row], c[0].length, d[row].length);
        }

        return buffer;
    }


    /**
     * Produces the "omega matrix" using a given size.
     *
     * @param size
     * @return
     */
    public static Complex[][] constructDFTMtx(int size) {

        Complex[][] buffer = new Complex[size][size];

        for (int row = 0; row < size; row++) {

            for (int column = 0; column < size; column++) {

                // An index of zero in either dimension results in a real value of 1...
                if (row == 0 || column == 0) {
                    buffer[row][column] = new Complex(1, 0);
                    continue;
                }

                //FIXME: row * column is complete guesswork and of course, does not work. (ArrayIndexOutOfBounds)
                buffer[row][column] = FastFourierTransform.WAVE_CACHE[(row * column)];

            }
        }

        return buffer;
    }


    /**
     * HEADS UP: This method has not been tested.
     * Multiplies matrix a * matrix b
     *
     * @param a
     * @param b
     * @return
     * @throws Exception
     */
    public static Complex[][] mtxProduct(Complex[][] a, Complex[][] b) throws Exception {
        if (a[0].length != b.length || a.length != b[0].length) {
            throw new Exception("Matrix product cannot be determined: dA = " + a[0].length + "x" + a.length + ", dB = " + b[0].length + "x" + b.length);
        }

        Complex[][] buffer = new Complex[a.length][b[0].length];

        for (int i = 0; i < a.length; i++) {

            for (int j = 0; j < b[0].length; j++) {

                for (int k = 0; k < a[0].length; k++) {

                    buffer[i][j] = a[i][k].mult(b[k][j]);
                }
            }
        }

        return buffer;
    }

    /**
     * Supposed to dump
     *
     * @param mtx
     */
    public static void printMtx(Complex[][] mtx) {
        for (Complex[] v : mtx) {
            for (Complex x : v) {
                System.out.print(x + " ");
            }
            System.out.println();
        }
    }

}
