package funcproject.calculation;

import org.apache.commons.math3.special.Gamma;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import scala.Tuple2;

import java.util.Iterator;
import java.util.List;

/**
 * @author kudryavtseva
 * @version $Id$
 */
public abstract class NonrecursiveFunctions extends Calculation {
    @Override
    public long getTimeParallel(double gamma, int k, int n) {
        long timer = -System.nanoTime();
        JavaRDD<Vector> vectorsRDD =  calculateMatrix(gamma, n, k);
        timer += System.nanoTime();

        List<Vector> vectors = vectorsRDD.collect();
        matrix = new double[n][k];
        for(int i = 0; i < vectors.size(); i++) {
           matrix[i] = vectors.get(i).toArray();
        }

        return timer;
    }

    @Override
    public long getTimeSequential(double gamma, int k, int n) {
        long timer = -System.nanoTime();

        double[][] result = new double[n][k];
        for(int i = 0; i < n; i++)
            for (int j = 0; j < k; j++)
                result[i][j] = calculateFunction(gamma, j, i);

        timer += System.nanoTime();

        return timer;
    }

    private JavaRDD<Vector> calculateMatrix (double gamma,  int n,  int k) {

        JavaRDD<Vector> vectorsRDD = tauRDD.filter(x -> x < n).cartesian(kRDD.filter(x -> x < k)).mapToPair(tuple -> {
            int tau = tuple._1;
            int kCurrent = tuple._2;
            Tuple2<Integer, Double> tuple2 = new Tuple2<>(tau, calculateFunction(gamma, kCurrent, tau));
            return tuple2;
        }).groupByKey().sortByKey().map(tuple -> {
            Iterator<Double> iterator = tuple._2.iterator();
            double[] kArray = new double[k];
            for (int i = 0; iterator.hasNext(); i++) {
                kArray[i] = iterator.next();
            }
            return Vectors.dense(kArray);
        });

        return  vectorsRDD;
    }


    protected abstract double calculateFunction(double gamma, int k, int n);
}
