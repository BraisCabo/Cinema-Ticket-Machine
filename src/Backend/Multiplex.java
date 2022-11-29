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
public class Multiplex {

    private String idiom = "es";

    /**
     * Inicializa el multiplex y el menu del programa
     * @throws java.io.FileNotFoundException
     * @throws java.lang.ClassNotFoundException
     * @throws java,lang.IOException
     */
    public void start() throws FileNotFoundException, IOException, ClassNotFoundException {
        CinemaTicketDispenser dispenser = new CinemaTicketDispenser();
        MainMenu menu = new MainMenu();
        menu.MainMenu(this, dispenser);
        menu.doOperation();
    }

    /**
     * Devuelve el idioma actual del sistema del múltiplex
     * @return idioma actual del múltiplex
     */
    public String getIdiom() {
        return idiom;
    }

    /**
     * Cambia el idioma actual del múltiplex por el del parámetro de entrada
     * @param value idioma a cambiar
     */
    public void setIdiom(String value) {
        idiom = value;
    }
}
