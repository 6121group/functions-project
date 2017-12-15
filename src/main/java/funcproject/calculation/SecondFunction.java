package funcproject.calculation;

/**
 * @author kudryavtseva
 * @version $Id$
 */
public class SecondFunction extends NonrecursiveFunctions {
    protected double calculateFunction(double gamma, int k, int tau) {
        int segmentsCount = 2000;
        double b = Math.PI / 2;
        double a = 0;
        double h = (b - a) / segmentsCount;

        double integral = 0;
        for (int i = 0; i < segmentsCount; i++) {
            double xi = a + i * h + h / 2;
            integral += h * Math.cos((2 * k + 3) * xi) * Math.cos(xi) * Math.cos(tau * gamma / 2 * Math.tan(xi));
        }

        return (8 * Math.pow(-1, k) * (k + 1) * (k + 2)) / (Math.PI * Math.pow(tau * gamma, 2)) * integral;
    }
}
