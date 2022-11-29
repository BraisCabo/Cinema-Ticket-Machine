/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 *
 * @author brais
 */
public class Theater implements Serializable {

    private int number;
    private int price;
    private HashSet<Seat> SeatSet = new HashSet<>();
    private Film filmData = new Film();
    private ArrayList<Session> SessionList = new ArrayList<>();

    /**
     * Lee los ficheros pasados por parámetro y guarda los datos de la película
     * y de la sala
     *
     * @param fs dirección del fichero con la información de la sala
     * @param fp dirección del fichero con la información de la película
     * @throws java.io.FileNotFoundException
     */
    public void Theater(String fs, String fp) throws FileNotFoundException {
        filmData.Film(fp);
        FileReader FicheroPeli = new FileReader(fp);
        Scanner scpeli = new Scanner(FicheroPeli);
        while (scpeli.hasNext()) {
            String line = scpeli.nextLine();
            if (line.startsWith("Theatre")) {
                this.number = Character.getNumericValue(line.charAt(9));
            }
            if (line.startsWith("Sessions")) {
                int h1 = 10;
                int h2 = 12;
                while (line.length() > h2 + 2) {
                    int hora = Integer.parseInt(line.substring(h1, h2));
                    int minutos = Integer.parseInt(line.substring(h1 + 3, h2 + 3));
                    this.addSession(hora, minutos);
                    h1 += 6;
                    h2 += 6;
                }
            }
            if (line.startsWith("Price")) {
                this.price = Integer.parseInt(line.substring(7, 8));
            }
        }
        String fsfinal = fs + number + ".txt";
        this.loadSeats(fsfinal);
        scpeli.close();
    }

    /**
     * Devuelve el número de la sala de la película
     *
     * @return numero de la sala
     */
    public int getNumber() {
        return number;
    }

    /**
     * Devuelve el precio de la entrada de la película
     *
     * @return precio de la entrada de la pelicula
     */
    public int getPrice() {
        return price;
    }

    /**
     * Devuelve el conjunto de los asientos de la sesión de la película
     *
     * @return conjunto de los asientos de la sesión
     */
    public HashSet<Seat> getSeatSet() {
        return SeatSet;
    }

    /**
     * Devuelve la información de la película
     *
     * @return información de la película
     */
    public Film getFilmData() {
        return filmData;
    }

    /**
     * Añade una sesión a la película
     *
     * @param h hora de la sesión
     * @param m minuto de la sesión
     */
    public void addSession(int h, int m) {
        Session sesion = new Session();
        sesion.Sesion(h, m);
        SessionList.add(sesion);
    }

    /**
     * Devuelve la lista de sesiones de la película
     *
     * @return lista de sesiones de la película
     */
    public ArrayList<Session> getSessionList() {
        return this.SessionList;
    }

    /**
     * Devuelve la última fila de la sala de la película
     *
     * @return última fila de la sala de la película
     */
    public int getMaxRows() {
        int row = 0;
        for (Seat s : SeatSet) {
            if (s.getRow() > row) {
                row = s.getRow();
            }
        }
        return row;
    }

    /**
     * Devuelve la última columna de la sala de la película
     *
     * @return última columna de la sala de la película
     */
    public int getMaxRCols() {
        int col = 0;
        for (Seat s : SeatSet) {
            if (s.getCol() > col) {
                col = s.getCol();
            }
        }
        return col;
    }

    /**
     * Carga la información de la sala de la película
     *
     * @param f dirección del fichero donde se encuentra la información de la
     * sala
     * @throws java.io.FileNotFoundException
     */
    public void loadSeats(String f) throws FileNotFoundException {
        FileReader FicheroSala = new FileReader(f);
        Scanner scsala = new Scanner(FicheroSala);
        int fil = 0;
        while (scsala.hasNext()) {
            String lineasala = scsala.nextLine();
            fil++;
            for (int col = 0; col < lineasala.length(); col++) {
                if (lineasala.charAt(col) == '*') {
                    Seat butaca = new Seat(fil, col + 1);
                    SeatSet.add(butaca);
                }
            }
        }
        scsala.close();
    }
}
