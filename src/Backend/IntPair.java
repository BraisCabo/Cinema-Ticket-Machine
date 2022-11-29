/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import java.io.Serializable;

/**
 * clase que sirve para guardar un par de integers hora y minutos
 *
 * @author brais
 */
public class IntPair implements Serializable {

    private int hora;
    private int minutos;

    /**
     * Recibe por parámetros la hora y los minutos y los guarda como parámetros
     *
     * @param hora para guardar
     * @param minutos para guardar
     */
    public IntPair(int hora, int minutos) {
        this.hora = hora;
        this.minutos = minutos;
    }

    /**
     * Devuelve la hora correspondiente a este par de enteros
     *
     * @return hora correspondiente al par
     */
    public int getHora() {
        return hora;
    }

    /**
     * Devuelve los minutos correspondientes a este par de enteros
     *
     * @return minutos correspondientes al par
     */
    public int getMinutos() {
        return minutos;
    }

    /**
     * Modifica la hora del par de enteros al parámetro recibido
     *
     * @param hora para modificar
     */
    public void setHora(int hora) {
        this.hora = hora;
    }

    /**
     * Modifica los minutos del par de enteros all parámetro recibido
     *
     * @param minutos para modificar
     */
    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

}
