package funcproject.calculation;

import Jama.Matrix;

/**
 * @author kudryavtseva
 * @version $Id$
 */
public class MatrixOperations {
    public static Matrix transpose(Matrix matrix) {
        return matrix.transpose();
    }

    public static Matrix inverse(Matrix matrix) {
        return matrix.inverse();
    }

    public static Matrix xtx(Matrix matrix) {
        return matrix.transpose().times(matrix).inverse();
    }
}
