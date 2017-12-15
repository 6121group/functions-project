package funcproject.calculation;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;

import java.util.List;

/**
 * @author kudryavtseva
 * @version $Id$
 */
public class ThirdFunction extends Calculation {
    @Override
    public long getTimeParallel(double gamma, int k, int n) {
        long timer = -System.nanoTime();
        JavaRDD<Vector> vectorsRDD = calculateMatrix(gamma, k, n);
        timer += System.nanoTime();

        List<Vector> vectors = vectorsRDD.collect();
        matrix = new double[n][k];
        for(int i = 0; i < vectors.size(); i++) {
            matrix[i] = vectors.get(i).toArray();
        }

        return timer;
    }

    protected double calculateFunction(double gamma, int tau, int k, double[] kArray) {
        return ((k * 2 + 1 - gamma * tau) / k) * kArray[k - 1] - ((k + 1.0) / k) * kArray[k - 2];
    }

    @Override
    public long getTimeSequential(double gamma, int k, int n) {
        long timer = -System.nanoTime();

        double[][] result = new double[n][k];

        for(int i = 0; i < n; i++) {
            double L0 = Math.exp(-gamma * i / 2);
            double L1 = L0 * (3 - gamma * i);

            double[] kArray = new double[k];
            kArray[0] = L0;
            kArray[1] = L1;

            for (int j = 2; j < k; j++)
                kArray[j] = calculateFunction(gamma, i, j, kArray);

            result[i] = kArray;
        }

        timer += System.nanoTime();

        return timer;
    }

    private JavaRDD<Vector> calculateMatrix (double gamma, int k, int n) {
        JavaRDD<Vector> vectorsRDD = tauRDD.filter(x -> x < n).map(tau -> {
            double L0 = Math.exp(-gamma * tau / 2);
            double L1 = L0 * (3 - gamma * tau);

            double[] kArray = new double[k];
            kArray[0] = L0;
            kArray[1] = L1;

            for (int s = 2; s < k; s++) {
                kArray[s] = calculateFunction(gamma, tau, s, kArray);
            }

            return Vectors.dense(kArray);
        });

        return  vectorsRDD;
    }
}
