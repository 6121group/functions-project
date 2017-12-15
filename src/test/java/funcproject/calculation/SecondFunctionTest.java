package funcproject.calculation;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author kudryavtseva
 * @version $Id$
 */
public class SecondFunctionTest {
    @Test
    public void calculateFunction() throws Exception {
        double gamma = 1;
        int tau = 1;
        int k = 6;

        SecondFunction function = new SecondFunction();
        assertEquals(-0.74721, function.calculateFunction(gamma, k, tau), 0.0001);
    }

}