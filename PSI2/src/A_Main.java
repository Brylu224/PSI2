import java.util.Arrays;

public class A_Main {

    public static void main ( String[] args ) {

        int ilosc_wejsc = 5;        //ilość wejść
        int nol = 10;       //ilość liter
        int counter = 0;    //licznik krokow
        double lr = 0.01;    //tempo uczenia się

        Adaline[] ada = new Adaline[ilosc_wejsc];
        for ( int i = 0; i < ilosc_wejsc; i++ )
            ada[i] = new Adaline( ilosc_wejsc );
        int[] y = new int[nol * 2];     //-1 - duża litera, 1 - mała litera
        Arrays.fill( y, 0, nol, - 1 );
        Arrays.fill( y, nol, nol * 2, 1 );

        int[] wyj = new int[nol * 2];   //tablica przechowująca wyniki testowania adaline
        Arrays.fill( wyj, 0, nol * 2, 0 );

        int proc;

        while ( ! Arrays.equals( y, wyj ) ) {
            proc = 0;

            for ( int i = 0; i < 2; i++ ) {     //-1 - wielkie litery, 1 - małe litery
                for ( int j = 0; j < nol; j++ )
                    learn( ada, ilosc_wejsc, lr, i, j );
            }

            wyj = test( ada, nol, ilosc_wejsc );

            for ( int i = 0; i < nol * 2; i++ )
                if ( wyj[i] != y[i] )
                    proc++;

                    counter++;
                       System.out.format( "%.6f%n", ( double ) proc / ( nol * 2 ) );
        }
               System.out.println();

                              //TESTOWANIE
        for ( int i = 0; i < nol; i++ )     //wyświetlenie liter
            System.out.print((char) (i + 65) + "\t");
        for ( int i = 0; i < nol; i++ )
            System.out.print((char)(i+97) + "\t");
                System.out.println();

                       for ( int i = 0; i < 2; i++ ) {     //wyświetlenie wynków
                       for ( int j = 0; j < nol; j++ )
                           System.out.print( wyj[i * nol + j] + "\t" );

        }
        System.out.println();
               System.out.println( "Ilość kroków do nauczenia się = " + counter );
    }


    private static void learn ( Adaline[] ada, int noi, double lr, int i, int j ) {
        int[] vector; //tablica przechowująca wektor sygnałów wejściowych do uczenia pierwszej warstwy sieci
        vector = Litery.getLetter( i, j );
        format( vector );
        int[] vector_p = new int[noi]; //tablica przechowująca wektor sygnałów wyjściowych pierwszej warstwy sieci
        vector_p[0] = 1; //bias
        int letter_size;
        if ( i == 0 ) letter_size = - 1;
        else letter_size = 1;
        for ( int k = 0; k < noi - 1; k++ ) { //uczenie pierwszej warstwy
            ada[k].learn( vector, letter_size, lr );
            vector_p[k + 1] = ada[k].test( vector ); //pobranie sygnału wyjściowego
        }
        ada[noi - 1].learn( vector_p, letter_size, lr ); //uczenie perceptronu wynikowego
//na podstawie sygnałów wyjściowych pierwszej warstwy
    }
    private static int[] test ( Adaline[] ada, int nol, int noi ) {
        int[] wyj = new int[nol * 2];
        int[] vector; //tablica przechowująca wektor sygnałów wejściowych do testowania pierwszej warstwy sieci
        int[] vector_p = new int[noi]; //tablica przechowująca wektor sygnałów wyjściowych pierwszej warstwy sieci
        vector_p[0] = 1; //bias
        for ( int i = 0; i < 2; i++ ) {
            for ( int j = 0; j < nol; j++ ) {
                vector = Litery.getLetter( i, j );
                format( vector );
                for ( int k = 0; k < noi - 1; k++ )
                    vector_p[k + 1] = ada[k].test( vector );
                wyj[i * nol + j] = ada[noi - 1].test( vector_p );
            }
        }
        return wyj;
    }
    //w przypadku adaline sygnały wejściowe = 0 muszą być zamienione na sygnały -1
    private static void format( int[] vector ){
        for ( int k = 0; k < vector.length; k++ )
            if ( vector[k] == 0 ) vector[k] = -1;
    }
}

