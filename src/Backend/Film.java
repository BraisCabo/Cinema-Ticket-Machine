/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Serializable;
import java.util.Scanner;

/**
 * Datos de una película
 *
 * @author brais
 */
public class Film implements Serializable {

    private String name;
    private String poster;
    private String description;

    /**
     * Recibe la dirección de un fichero, lo abre, lo lee e inicializa los
     * valores de los parámetros de la película con los datos del fichero
     *
     * @param fichero dirección del fichero con información de las películas
     * @throws java.io.FileNotFoundException
     */
    public void Film(String fichero) throws FileNotFoundException {
        FileReader FicheroPeli = new FileReader(fichero);
        Scanner scpeli = new Scanner(FicheroPeli);
        String line;
        scpeli.nextLine();
        scpeli.nextLine();
        line = scpeli.nextLine();
        name = line.substring(7);
        scpeli.nextLine();
        line = scpeli.nextLine();
        description = line;
        scpeli.nextLine();
        scpeli.nextLine();
        scpeli.nextLine();
        line = scpeli.nextLine();
        poster = line.substring(8);
        scpeli.close();
    }

    /**
     * Devuelve el nombre de la película
     *
     * @return nombre de la película
     */
    public String getName() {
        return name;
    }

    /**
     * Devuelve la dirección del fichero donde se encuentra el poster de la
     * película
     *
     * @return dirección del fichero donde está el poster de la película
     */
    public String getPoster() {
        return poster;
    }

    /**
     * Devuelve la descripcion de la película
     *
     * @return descripción de la película
     */
    public String getDescription() {
        return description;
    }
}
