/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import java.io.Serializable;

/**
 *
 * @author brais
 */
public class Seat implements Serializable {

    private int row;
    private int col;

    /**
     * Devuelve un booleano dependiendo de si el asiento pasado por parámetro es
     * igual al actuál
     *
     * @param asiento parametro para comparar los asientos
     * @return true si el asiento pasado por parámetro es igual al asiento de la
     * clase
     */
    public boolean equals(Seat asiento) {
        return (this.row == asiento.getRow()) & (this.col == asiento.getCol());
    }

    /**
     * Devuelve la fila del asiento actual
     *
     * @return entero de la fila del asiento actual
     */
    public int getRow() {
        return row;
    }

    /**
     * Devuelve la columna del asiento actual
     *
     * @return entero de la columna del asiento actual
     */
    public int getCol() {
        return col;
    }

    /**
     * Cambia los datos de la fila y la columna del asiento por los pasados por
     * parámetro
     *
     * @param row fila del asiento
     * @param col columna del asiento
     */
    public Seat(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * devuelve el hashcode del asiento actual
     *
     * @return hashcode del asiento de la clase
     */
    @Override
    public int hashCode() {
        int hash = row + col * 20;
        return hash;
    }

    /**
     * comprueba si dos objetos son iguales y devuelve un booleano
     *
     * @param obj objeto a comparar con el actual
     * @return booleano resultante de la comparación
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Seat other = (Seat) obj;
        if (this.row != other.row) {
            return false;
        }
        return this.col == other.col;
    }

}
