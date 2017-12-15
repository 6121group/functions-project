package funcproject.calculation;

import Jama.Matrix;

import static funcproject.calculation.MatrixOperations.*;

/**
 * @author kudryavtseva
 * @version $Id$
 */
public class Measurement {

    public static double transposeMeasure(double[][] matrix) {
        Matrix m = new Matrix(matrix);
        long timer = -System.nanoTime();
        transpose(m);
        timer += System.nanoTime();
        return timer;
    }

    public static double inverseMeasure(double[][] matrix) {
        Matrix m = new Matrix(matrix);
        long timer = -System.nanoTime();
        inverse(m);
        timer += System.nanoTime();
        return timer;
    }

    public static double xtxMeasure(double[][] matrix) {
        Matrix m = new Matrix(matrix);
        long timer = -System.nanoTime();
        xtx(m);
        timer += System.nanoTime();
        return timer;
    }
}


