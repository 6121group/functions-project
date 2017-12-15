package funcproject.calculation;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kudryavtseva
 * @version $Id$
 */
public abstract class Calculation implements Serializable{
    protected static JavaSparkContext context;
    protected double[][] matrix;
    protected Util util;

    protected JavaRDD<Integer> kRDD;
    protected JavaRDD<Integer> tauRDD;

    public double[][] getMatrix() {
        return matrix;
    }

    public Calculation() {
        if (context == null) {
            SparkConf sparkConf = new SparkConf();
            sparkConf.setAppName("Orthogonal functions");
            sparkConf.setMaster("local");
            context = new JavaSparkContext(sparkConf);

            util = Util.getInstance();
            kRDD = context.parallelize(util.getkParallelList());
            tauRDD = context.parallelize(util.getnParallelList());

        }  else {
            util = Util.getInstance();
            kRDD = context.parallelize(util.getkParallelList());
            tauRDD = context.parallelize(util.getnParallelList());
        }
    }

    public abstract long getTimeParallel(double gamma, int k, int n);

    public abstract long getTimeSequential(double gamma, int k, int n);
}
