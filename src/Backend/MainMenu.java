/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import java.io.FileNotFoundException;
import java.io.IOException;
import sienens.CinemaTicketDispenser;

/**
 *
 * @author brais
 */
public class MainMenu extends Operation {

    IdiomSelection idiom = new IdiomSelection();
    MovieTicketSale sale = new MovieTicketSale();

    /**
     * Menú principal del programa, pasa opciones a la máquina y una vez
     * devuelta la opción la usa para pasar al menú correspondiente, es un bucle
     * infinito de manera que solo se saldrá cuando se apague la máquina
     */
    @Override
    public void doOperation() {
        while (true) {//buble infinito para que siempre se muestre el menú hasta que se apague la máquina 
            super.cleanDispenser();
            this.presentMenu();
            char c = super.getDispenser().waitEvent(30);
            super.cleanDispenser();
            if (c == 'B') {
                sale.doOperation();
            } else {
                if (c == 'A') {
                    idiom.doOperation();
                }
            }
        }

    }

    /**
     * Recibe como parámetros la clase multiplex y el dispensador y los pasa a
     * la clase padre operación para inicializarse, así como a la selección de
     * idioma y al movieticket sale
     *
     * @param multi multiplex del sistema
     * @param dispensador dispensador del sistema
     * @throws java.io.FileNotFoundException
     * @throws java.lang.ClassNotFoundException
     */
    public void MainMenu(Multiplex multi, CinemaTicketDispenser dispensador) throws FileNotFoundException, IOException, ClassNotFoundException {
        super.Operation(dispensador, multi);
        idiom.IdiomSelection(dispensador, multi);
        sale.MovieTicketSale(multi, dispensador);
    }

    /**
     * Pasa a la máquina las opciones para que muestre el menú principal
     */
    private void presentMenu() {
        super.getDispenser().setMenuMode();
        super.getDispenser().setTitle(this.getTitle());
        super.getDispenser().setOption(0, idiom.getTitle());
        super.getDispenser().setOption(1, sale.getTitle());
    }

    /**
     * Devuelve el título correspondiente al menú principal
     *
     * @return titulo
     */
    @Override
    public String getTitle() {
        return java.util.ResourceBundle.getBundle("Practica/" + this.getMultiplex().getIdiom()).getString("Menu");
    }
}
