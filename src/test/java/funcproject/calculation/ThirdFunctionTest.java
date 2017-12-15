package funcproject.calculation;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author kudryavtseva
 * @version $Id$
 */
public class ThirdFunctionTest {
    @Test
    public void calculateFunction() throws Exception {
        ThirdFunction function = new ThirdFunction();
        double gamma = 1;
        int tau = 1;
        int k = 6;

        double L0 = Math.exp(-gamma * tau / 2);
        double L1 = L0 * (3 - gamma * tau);

        double[] kArray = new double[k];
        kArray[0] = L0;
        kArray[1] = L1;

        for (int s = 2; s < k; s++) {
            kArray[s] = function.calculateFunction(gamma, tau, s, kArray);
        }

        assertEquals(0.17185, kArray[k - 1], 0.0001);
    }

}