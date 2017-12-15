package funcproject.calculation;

import org.apache.commons.math3.special.Gamma;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kudryavtseva
 * @version $Id$
 */
public class FirstFunction extends NonrecursiveFunctions {
    @Override
     protected double calculateFunction(double gamma, int k, int tau) {
        double Lk = 0;

        switch (k) {
            case 0: {
                Lk = Math.exp(-gamma * tau / 2);
                break;
            }
            case 1: {
                Lk = Math.exp(-gamma * tau / 2) * (3 - gamma * tau);
                break;
            }
            case 2: {
                Lk = Math.exp(-gamma * tau / 2) * (Math.pow(gamma * tau, 2) - 8 * gamma * tau + 12) / 2;
                break;
            }
            case 3: {
                Lk = Math.exp(-gamma * tau / 2) * (60 - 60 * gamma * tau + 15 * Math.pow(gamma * tau, 2) - Math.pow(gamma * tau, 3)) / 6;
                break;
            }
            case 4: {
                Lk = Math.exp(-gamma * tau / 2) * (Math.pow(gamma * tau, 4) - 24 * Math.pow(gamma * tau, 3) +
                        180 * Math.pow(gamma * tau, 2 - 480 * gamma * tau + 360)) / 24;
                break;
            }
            case 5: {
                Lk = Math.exp(-gamma * tau / 2) * (2520 - 4200 * gamma * tau + 2100 * Math.pow(gamma * tau, 2) -
                        420 * Math.pow(gamma * tau, 3) + 35 * Math.pow(gamma * tau, 4) - Math.pow(gamma * tau, 5)) / 120;
                break;
            }
            default: {
                for (int s = 0; s < k; s++) {
                    Lk += (Gamma.gamma(k + 3) * Math.pow((-gamma * tau), s) * Math.exp(-gamma * tau / 2)) / (Gamma.gamma(k - s + 1) * Gamma.gamma(s + 3) * Gamma.gamma(s + 1));
                }
                break;
            }
        }
        return Lk;
    }
}

