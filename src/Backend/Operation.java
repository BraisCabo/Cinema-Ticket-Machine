/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import sienens.CinemaTicketDispenser;

/**
 *
 * @author brais
 */
public abstract class Operation {

    private CinemaTicketDispenser dispensador;
    private Multiplex multi;

    /**
     * Función abstracta que servirá para que las clases hijas implementen su
     * funcionamiento
     */
    abstract public void doOperation();

    /**
     * inicializa los parámetros correspondientes al dispensdor y al múltiplex
     * de la clase
     *
     * @param dispensador
     * @param multi
     */
    public void Operation(CinemaTicketDispenser dispensador, Multiplex multi) {
        this.dispensador = dispensador;
        this.multi = multi;
    }

    /**
     * Devuelve el dispensador del programa
     *
     * @return dipensador del programa
     */
    public CinemaTicketDispenser getDispenser() {
        return dispensador;
    }

    /**
     * Función abstracta que sirve para que las clases hijas implementen el
     * método para devolver sus títulos
     *
     * @return titulo correspondiente de las clases hijas
     */
    abstract public String getTitle();

    /**
     * Devuelve el múltiplex actual del programa
     *
     * @return multiplex del programa
     */
    public Multiplex getMultiplex() {
        return multi;
    }

    /**
     * Recibe un char y devuelve un integer correspondiente para acceder a las
     * posiciones necesarias del array
     *
     * @param c caracter que será convertido en el ínteger correspondiente
     * @return integer correspondiente al char recibido
     */
    protected int charToInt(char c) {//función que sirve para pasar los char que devuelve el dispensador a integers para acceder a las posiciones de los arrays
        int i = 0;
        switch (c) {
            case 'A':
                i = 0;
                break;
            case 'B':
                i = 1;
                break;
            case 'C':
                i = 2;
                break;
            case 'D':
                i = 3;
                break;
            case 'E':
                i = 4;
                break;
            case 'F':
                i = 5;
                break;
        }
        return i;
    }

    /**
     * Pone todos los recuadros del dispensador en blanco
     */
    void cleanDispenser() {
        for (int i = 0; i <= 5; i++) {
            this.dispensador.setOption(i, "");
        }
        this.dispensador.setImage("");
        this.dispensador.setDescription("");
        this.dispensador.setTitle("");
    }
}
