package funcproject.calculation;

import org.apache.spark.api.java.JavaRDD;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kudryavtseva
 * @version $Id$
 */
public class Util implements Serializable{
    private static Util instance;
    protected static List<Integer> kParallelList;
    protected static List<Integer> nParallelList;

    public List<Integer> getkParallelList() {
        return kParallelList;
    }

    public List<Integer> getnParallelList() {
        return nParallelList;
    }

    public Util() {
    }

    public static Util getInstance() {
        if(instance == null) {
            instance = new Util();

            int maxSize = 600;
            kParallelList = new ArrayList<>(maxSize);
            nParallelList = new ArrayList<>(maxSize);
            for(int i = 0; i < maxSize; i++) {
                kParallelList.add(i, i);
                nParallelList.add(i, i);
            }
        }
        return instance;
    }
}
