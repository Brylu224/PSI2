
import java.util.Random;

public class Perceptron {

    private int ilosc_wejsc;
    private double[] waga;
    public Perceptron ( int numbers_of_inputs ) {
        ilosc_wejsc = numbers_of_inputs;
        waga= new double[ilosc_wejsc];
        for ( int i = 0; i < ilosc_wejsc; i++ )
            waga[i] = new Random().nextDouble();
    }

    private int active ( double y_p ) {
        return y_p < 0 ? 0 : 1;
    }

    public int suma ( int[] x ) {
        double y_p = 0;
        for ( int i = 0; i < ilosc_wejsc; i++ )
            y_p += x[i] * waga[i];
        return active( y_p );
    }

    public void learn ( int[] x, double y, double lr ) {
        double y_p = suma( x );
        for ( int i = 0; i < ilosc_wejsc; i++ )
            waga[i] += ( y - y_p ) * lr * x[i];
    }
}
