package Backend;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Brais
 */
public class CinemaDispenser {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     * @throws java.lang.ClassNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        Multiplex multi = new Multiplex();
        multi.start();
    }
}
