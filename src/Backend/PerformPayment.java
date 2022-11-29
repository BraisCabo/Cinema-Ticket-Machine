/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.CommunicationException;
import sienens.CinemaTicketDispenser;
import urjc.UrjcBankServer;

/**
 *
 * @author brais
 */
public class PerformPayment extends Operation {

    private boolean esSocio;
    private HashSet<Long> listaSocios = new HashSet<>();
    private UrjcBankServer banco = new UrjcBankServer();
    private int Precio;
    private int numero;
    private String nombrepeli;
    private boolean estado;

    /**
     * Función principal de la clase, sirve para enviar las opciones a la
     * máquina para cobrar al usuario
     */
    @Override
    public void doOperation() {
        this.esSocio=false;
        this.estado = true;
        super.cleanDispenser();
        super.getDispenser().setMessageMode();
        super.getDispenser().setTitle(this.getTitle());
        
        String cobrar = String.valueOf(this.numero) + " " + java.util.ResourceBundle.getBundle("Backend/" + this.getMultiplex().getIdiom()).getString("Entradas") + " " + nombrepeli + " " + String.valueOf(this.Precio) + " €";//es una variable auxiliar para poder manejar el string más facilmente
        super.getDispenser().setDescription(cobrar);
        super.getDispenser().setOption(1, java.util.ResourceBundle.getBundle("Backend/" + this.getMultiplex().getIdiom()).getString("Cancelar"));
        if (super.getDispenser().waitEvent(30) != '1') {
            this.estado = false;
        } else {
            super.getDispenser().expelCreditCard(30);
            super.getDispenser().waitEvent(1);//esperar para ver como traga la tarjeta y que así parezca más realista
            int cont = 0;//cuenta el numero de veces que se ha intentado realizar la comunicación, al llegar a un cierto numero cancela el pago y vuelve al menú principal
            while (!banco.comunicationAvaiable() & this.estado == true) {
                super.getDispenser().setDescription(java.util.ResourceBundle.getBundle("Backend/" + this.getMultiplex().getIdiom()).getString("Comunicacion"));
                super.getDispenser().setTitle(java.util.ResourceBundle.getBundle("Backend/" + this.getMultiplex().getIdiom()).getString("Espere"));
                cont++;
                char c = super.getDispenser().waitEvent(2);//sirve para esperar mientras se intenta encontrar la comunicación
                if (cont == 3 | c == 'B') {//si el usuario ha pulsado cancelar o ha pasado un tiempo cancelamos el pago
                    this.estado = false;
                }
            }
            try {
                 if (listaSocios.contains(super.getDispenser().getCardNumber())){
                    Precio *=0.7; 
                    this.esSocio=true;
                    super.getDispenser().setDescription(java.util.ResourceBundle.getBundle("Backend/" + this.getMultiplex().getIdiom()).getString("Socio"));
                    super.getDispenser().waitEvent(2);
                 }
                if (this.estado == true&&banco.doOperation(super.getDispenser().getCardNumber(), Precio)) {
                    this.estado = true;
                    super.cleanDispenser();
                    super.getDispenser().setMessageMode();
                    super.getDispenser().setTitle(java.util.ResourceBundle.getBundle("Backend/" + this.getMultiplex().getIdiom()).getString("Rtarjeta"));
                    super.getDispenser().setDescription(java.util.ResourceBundle.getBundle("Backend/" + this.getMultiplex().getIdiom()).getString("Tarjetaout"));
                } else {
                    this.estado = false;
                    super.getDispenser().setTitle(java.util.ResourceBundle.getBundle("Backend/" + this.getMultiplex().getIdiom()).getString("Rtarjeta"));
                    super.getDispenser().setDescription(java.util.ResourceBundle.getBundle("Backend/" + this.getMultiplex().getIdiom()).getString("Pago"));
                }
            } catch (CommunicationException ex) {
                Logger.getLogger(PerformPayment.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        if (!super.getDispenser().expelCreditCard(30)) {
            super.getDispenser().retainCreditCard(true);
        }
    }

    /**
     * Devuelve el estado de la operación actual
     *
     * @return estado actual, true si se ha podido realizar, false si no
     */
    public boolean getEstado() {
        return estado;
    }

    /**
     * Inicializa la clase padre con los parámetros recibidos
     *
     * @param multi multiplexor para pasar a la clase padre
     * @param dispensador dispensador para pasar a la clase padre
     */
    public void PerformPayment(Multiplex multi, CinemaTicketDispenser dispensador) throws FileNotFoundException {
        super.Operation(dispensador, multi);
        this.loadListaSocios();
    }

    /**
     * Rellena los datos del ticket a imprimir
     *
     * @param p precio de la entrada
     * @param s numero de la sala
     * @param n nombre de la película
     */
    public void rellenarDatosPelicula(int p, int s, String n) {
        this.Precio = p;
        this.numero = s;
        this.nombrepeli = n;
    }

    /**
     * Devuelve el título correspondiente a la opción de la máquina
     */
    @Override
    public String getTitle() {
        return java.util.ResourceBundle.getBundle("Backend/" + this.getMultiplex().getIdiom()).getString("Tarjetain");
    }
    
    public void setListaSocios(HashSet<Long> lista){
        this.listaSocios=lista;
    }
    
        private void loadListaSocios() throws FileNotFoundException{
        String fichero = "sources/FicheroSocios.txt";
        FileReader FicheroSocios = new FileReader(fichero);
        Scanner scsocios = new Scanner(FicheroSocios);
        while (scsocios.hasNext()){
            String line = scsocios.nextLine();
            line=line.replaceAll(" ", "");
            long numero = Long.parseLong(line);
            listaSocios.add(numero);
        }
    }
        public boolean getEssocio(){
            return this.esSocio;
        }
}
