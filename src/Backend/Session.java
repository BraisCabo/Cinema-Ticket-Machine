/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import java.io.Serializable;
import java.util.HashSet;

/**
 *
 * @author brais
 */
public class Session implements Serializable {

    private IntPair pair = new IntPair(0, 0);

    private HashSet<Seat> occupiedSeatSet = new HashSet<>();

    /**
     * Incializa la hora y el minuto de la sesión
     *
     * @param hora hora del par
     * @param minuto minuto del par
     */
    public void Sesion(int hora, int minuto) {
        pair = new IntPair(hora, minuto);
    }

    /**
     * Devuelve un booleano dependiendo de si el asiento está ocupado
     *
     * @param fila fila del asiento a consultar
     * @param columna columna del asiento a consultar
     * @return booleano resultante de la consulta
     */
    public boolean isOccupied(int fila, int columna) {
        return occupiedSeatSet.contains(new Seat(fila, columna));
    }

    /**
     * Ocupa el sitio correspondiente a los parámetros pasados
     *
     * @param fila fila del asiento
     * @param columna columna del asiento
     */
    public void occupiesSeat(int fila, int columna) {
        occupiedSeatSet.add(new Seat(fila, columna));
    }

    /**
     * Devuelve un string con la hora de la sesión
     *
     * @return hora de la sesión
     */
    public String getHour() {
        String hora = String.valueOf(pair.getHora());
        if (pair.getHora() < 10) {
            hora = "0" + hora;
        }
        String minuto = String.valueOf(pair.getMinutos());
        if (pair.getMinutos() < 10) {
            minuto = "0" + minuto;
        }
        hora = hora + ":" + minuto;
        return hora;
    }
}
