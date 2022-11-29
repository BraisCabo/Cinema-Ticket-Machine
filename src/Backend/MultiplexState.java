/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author brais
 */
public class MultiplexState implements Serializable {

    private ArrayList<Theater> theaterList = new ArrayList<>();
    private int pos;

    /**
     * Sirve para pasar a la clase theater las direcciones de los ficheros a
     * leer para que inicialice los parámetros a las clases correspondientes
     *
     * @throws java.io.FileNotFoundException
     */
    public void loadMoviesAndSessions() throws FileNotFoundException {
        String cadenas = "sources/Theater";
        String cadenap = "sources/Movie";
        String cadenaf;
        for (int i = 1; i <= 4; i++) {
            Theater sala = new Theater();
            cadenaf = cadenap + i + ".txt";
            sala.Theater(cadenas, cadenaf);
            theaterList.add(sala);
        }
    }

    /**
     * Devuelve el nombre de la película
     *
     * @return película actualmente seleccionada
     */
    public Theater getTheater() {
        return theaterList.get(pos);
    }

    /**
     * Devuelve el array de las películas existentes
     *
     * @return arraylist de las películas actuales con su información
     */
    public ArrayList<Theater> getTheaterList() {
        return theaterList;
    }
}
