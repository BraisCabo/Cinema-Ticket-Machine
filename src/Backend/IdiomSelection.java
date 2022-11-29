/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import sienens.CinemaTicketDispenser;

/**
 * Sirve para cambiar los idiomas del múltiplex
 *
 * @author brais
 */
public class IdiomSelection extends Operation {

    private String idiom;

    /**
     * Establece las opciones de selección del idioma en la máquina y las guarda
     * para pasárselas posteriormente al múltiplex
     */
    @Override
    public void doOperation() {
        super.cleanDispenser();
        super.getDispenser().setTitle(java.util.ResourceBundle.getBundle("Practica/" + this.getMultiplex().getIdiom()).getString("Idioma2"));
        super.getDispenser().setOption(0, java.util.ResourceBundle.getBundle("Practica/" + this.getMultiplex().getIdiom()).getString("Español"));
        super.getDispenser().setOption(1, java.util.ResourceBundle.getBundle("Practica/" + this.getMultiplex().getIdiom()).getString("Ingles"));
        super.getDispenser().setOption(2, java.util.ResourceBundle.getBundle("Practica/" + this.getMultiplex().getIdiom()).getString("Euskera"));
        super.getDispenser().setOption(3, java.util.ResourceBundle.getBundle("Practica/" + this.getMultiplex().getIdiom()).getString("Catalan"));
        super.getDispenser().setOption(4, java.util.ResourceBundle.getBundle("Practica/" + this.getMultiplex().getIdiom()).getString("Cancelar"));
        switch (super.getDispenser().waitEvent(30)) {
            case 'A':
                idiom = "es";
                break;
            case 'B':
                idiom = "en";
                break;
            case 'C':
                idiom = "eu";
                break;
            case 'D':
                idiom = "ca";
                break;
            case 'E':
                idiom = super.getMultiplex().getIdiom();
                break;
            case 0:
                idiom = super.getMultiplex().getIdiom();
                break;
        }
        super.getMultiplex().setIdiom(idiom);
    }

    /**
     * Inicializa los parámetros de la clase padre Operation
     *
     * @param dispensador dispensador de la clase urjccinemadispenser
     * @param multi multiplex de la máquina
     */
    public void IdiomSelection(CinemaTicketDispenser dispensador, Multiplex multi) {
        super.Operation(dispensador, multi);
    }

    /**
     * Devuelve el título apropiado para poner en la máquina a la opción
     * correspondiente
     *
     * @return titulo
     */
    @Override
    public String getTitle() {
        return java.util.ResourceBundle.getBundle("Practica/" + this.getMultiplex().getIdiom()).getString("Idioma");
    }
}
