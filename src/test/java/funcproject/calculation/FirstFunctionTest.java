package funcproject.calculation;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author kudryavtseva
 * @version $Id$
 */
public class FirstFunctionTest {
    @Test
    public void calculateFunction() throws Exception {
        double gamma = 1;
        int tau = 1;
        int k = 6;

        FirstFunction function = new FirstFunction();
        assertEquals(0.17185, function.calculateFunction(gamma, k - 1, tau), 0.0001);
    }

}