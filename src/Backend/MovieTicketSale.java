/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import sienens.CinemaTicketDispenser;

/**
 *
 * @author brais
 */
public class MovieTicketSale extends Operation {
    
    private PerformPayment payment = new PerformPayment();
    private HashSet<Seat> SellSeat = new HashSet<>();
    MultiplexState state = new MultiplexState();
    private boolean cancelar;
    Theater sala = new Theater();
    private Session sesion;

    /**
     * Maneja el menú principal de la venta de entradas y va recorriendo las
     * distintas opciones
     */
    @Override
    public void doOperation() {
        cancelar = false;
        super.cleanDispenser();
        SellSeat.clear();
        super.getDispenser().setMenuMode();
        if (!cancelar) {
            sala = this.selectTheater();
            if (!cancelar) {
                sesion = this.SelectSession(sala);
                if (!cancelar) {
                    this.selectSeats(sala, sesion);
                    if (!cancelar) {
                        try {
                            this.performPayment();
                        } catch (IOException ex) {
                            Logger.getLogger(MovieTicketSale.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
    }

    /**
     * Pide por parámetros el multiplexor y el dispensador y los pasa a la clase
     * padre para inicializarlos, además, es el encargado de manejar el primer
     * menú donde se pregunta si se quiere realizar una carga limpia o un backup
     *
     * @param multi multiplex del programa
     * @param dispensador urjccinematicketdispenser del programa
     * @throws java.io.FileNotFoundException
     * @throws java.lang.ClassNotFoundException
     */
    public void MovieTicketSale(Multiplex multi, CinemaTicketDispenser dispensador) throws FileNotFoundException, IOException, ClassNotFoundException {
        super.Operation(dispensador, multi);
        payment.PerformPayment(multi, dispensador);
        super.getDispenser().setTitle("Nueva carga o backup?");
        super.getDispenser().setMessageMode();
        super.getDispenser().setOption(0, "Nueva carga");
        super.getDispenser().setOption(1, "Fichero de resguardo");
        char c = super.getDispenser().waitEvent(30);
        if (c == 'A') {
            state.loadMoviesAndSessions();
        } else if (c == 'B') {
            try {
                this.deserializeMultiplexState();
            } catch (FileNotFoundException ex) {
                state.loadMoviesAndSessions();
            }
        }
    }

    /**
     * Sirve para pasar al dispensador que películas hay disponibles y devuelve
     * la opción seleccionada por el usuario
     *
     * @return película seleccionada por el usuario
     */
    private Theater selectTheater() {
        super.getDispenser().setMenuMode();
        int cont = 0;
        super.cleanDispenser();
        super.getDispenser().setTitle(java.util.ResourceBundle.getBundle("Backend/" + this.getMultiplex().getIdiom()).getString("Pelicula"));
        for (Theater sala : state.getTheaterList()) {
            super.getDispenser().setOption(cont, sala.getFilmData().getName());
            cont++;
        }
        super.getDispenser().setOption(cont, java.util.ResourceBundle.getBundle("Backend/" + this.getMultiplex().getIdiom()).getString("Cancelar"));
        char devuelto = super.getDispenser().waitEvent(30);//devuelto sirve para guardar el char que retorna el dispensador para despues convertirlo a un numero
        if ((devuelto == 0) | (super.charToInt(devuelto) == cont)) {
            cancelar = true;
            return state.getTheaterList().get(0);
        } else {
            return state.getTheaterList().get(super.charToInt(devuelto));
        }
    }

    /**
     * Sirve para seleccionar los asientos que se han escojido por el usuario y
     * los guarda en un conjunto para despues ocuparlos cuando se haga el pago
     *
     * @param sala información de la película
     * @param sesion información de la sesión de la película
     */
    private void selectSeats(Theater sala, Session sesion) {
        super.getDispenser().setTheaterMode(sala.getMaxRows(), sala.getMaxRCols());
        super.getDispenser().setTitle(java.util.ResourceBundle.getBundle("Backend/" + this.getMultiplex().getIdiom()).getString("Butacas"));
        this.presentSeats();
        super.getDispenser().setOption(0, java.util.ResourceBundle.getBundle("Backend/" + this.getMultiplex().getIdiom()).getString("Cancelar"));
        super.getDispenser().setOption(1, java.util.ResourceBundle.getBundle("Backend/" + this.getMultiplex().getIdiom()).getString("Siguiente"));
        char c = ' ';
        int cont = 0;
        while ((c != 'A') & (c != 'B') & (cont < 4) & (c != 0)) {
            c = super.getDispenser().waitEvent(30);
            if ((c != 'A') & (c != 'B') & (c != 0)) {
                byte col = (byte) (c & 0xFF);
                byte row = (byte) ((c & 0xFF00) >> 8);
                if ((!sesion.isOccupied(row, col)) & (!SellSeat.contains(new Seat(row, col)))) {
                    super.getDispenser().markSeat(row, col, 3);
                    SellSeat.add(new Seat(row, col));
                    cont++;
                    super.getDispenser().setTitle(java.util.ResourceBundle.getBundle("Backend/" + this.getMultiplex().getIdiom()).getString("Fila") + row + " " + java.util.ResourceBundle.getBundle("Backend/" + this.getMultiplex().getIdiom()).getString("Asiento") + col);
                } else if ((!sesion.isOccupied(row, col)) & (SellSeat.contains(new Seat(row, col)))) {
                    super.getDispenser().markSeat(row, col, 2);
                    SellSeat.remove(new Seat(row, col));
                    cont--;
                }

            }
        }
        cancelar = !((c == 'B') | (cont == 4));
    }

    /**
     * Pasa a la máquina las opciones de selección de sesión y devuelve una
     * variable Session con toda la información de la película
     *
     * @param sala sala en la que hay que comprobar las sesiones
     * @return sesion elejida por el usuario
     */
    private Session SelectSession(Theater sala) {
        super.cleanDispenser();
        super.getDispenser().setTitle(java.util.ResourceBundle.getBundle("Backend/" + this.getMultiplex().getIdiom()).getString("Sesion"));
        super.getDispenser().setImage(sala.getFilmData().getPoster());
        super.getDispenser().setDescription(sala.getFilmData().getDescription());
        int cont = 0;
        for (Session sesion : sala.getSessionList()) {
            String hora = sesion.getHour();
            super.getDispenser().setOption(cont, hora);
            cont++;
        }
        super.getDispenser().setOption(cont, java.util.ResourceBundle.getBundle("Backend/" + this.getMultiplex().getIdiom()).getString("Cancelar"));
        char devuelto = super.getDispenser().waitEvent(30);
        if ((devuelto == 0) | (super.charToInt(devuelto) == cont)) {
            cancelar = true;
            return sala.getSessionList().get(0);
        } else {
            return sala.getSessionList().get(super.charToInt(devuelto));
        }
    }

    /**
     * Pasa a la clase encargada de realizar el pago los datos de la película
     * además del precio a cobrar. Una vez pagado ocupa las butacas vendidas y
     * serializa el estado del multiplex para hacer el fichero backup e imprime
     * los tickets
     */
    private void performPayment() throws IOException {
        payment.rellenarDatosPelicula(this.computePrice(), SellSeat.size(), this.sala.getFilmData().getName());
        payment.doOperation();
        if (payment.getEstado()) {
            for (Seat butaca : SellSeat) {
                sesion.occupiesSeat(butaca.getRow(), butaca.getCol());
            }
            this.serializeMultiplexState();
            for (int i = 0; i < this.datosTicket().size(); i++) {
                super.getDispenser().print(this.datosTicket().get(i));
            }
        }
    }

    /**
     * Devuelve la lista de tickets rellenada par imprimir
     *
     * @return Lista de los tickets correspondientes a la compra
     */
    private ArrayList<ArrayList<String>> datosTicket() {
        ArrayList<ArrayList<String>> listaTickets = new ArrayList<>();
        for (Seat butaca : SellSeat) {
            ArrayList<String> text = new ArrayList<>();
            Film peli = sala.getFilmData();
            text.add("   " + java.util.ResourceBundle.getBundle("Backend/" + this.getMultiplex().getIdiom()).getString("Entrada") + " " + peli.getName());
            text.add("   ===================");
            text.add("   " + java.util.ResourceBundle.getBundle("Backend/" + this.getMultiplex().getIdiom()).getString("Sala") + " " + sala.getNumber());
            text.add("   " + java.util.ResourceBundle.getBundle("Backend/" + this.getMultiplex().getIdiom()).getString("Hora") + " " + sesion.getHour());
            text.add("   " + java.util.ResourceBundle.getBundle("Backend/" + this.getMultiplex().getIdiom()).getString("Fila") + " " + butaca.getRow());
            text.add("   " + java.util.ResourceBundle.getBundle("Backend/" + this.getMultiplex().getIdiom()).getString("Asiento") + " " + butaca.getCol());
            if (payment.getEssocio()){
                text.add("   " + java.util.ResourceBundle.getBundle("Backend/" + this.getMultiplex().getIdiom()).getString("Precio") + " " + sala.getPrice()*0.7 + "€");
                text.add("   " + java.util.ResourceBundle.getBundle("Backend/" + this.getMultiplex().getIdiom()).getString("Socio") + " ");
            }else{
                text.add("   " + java.util.ResourceBundle.getBundle("Backend/" + this.getMultiplex().getIdiom()).getString("Precio") + " " + sala.getPrice() + "€");
            }          
            listaTickets.add(text);
        }
        return listaTickets;
    }

    /**
     * Pasa al dispensador el estado actual de la sala para que este los pinte
     * por pantalla
     */
    private void presentSeats() {
        for (int f = 1; f <= sala.getMaxRows(); f++) {
            for (int c = 1; c <= sala.getMaxRCols(); c++) {
                Seat asiento = new Seat(f, c);
                if (sala.getSeatSet().contains(asiento)) {
                    if (sesion.isOccupied(f, c)) {
                        super.getDispenser().markSeat(f, c, 1);
                    } else {
                        super.getDispenser().markSeat(f, c, 2);
                    }
                } else {
                    super.getDispenser().markSeat(f, c, 0);
                }
            }
        }

    }

    /**
     * Calcula el precio a pagar por el usuario y lo devuelve
     *
     * @return precio que tiene que pagar el usuario
     */
    private int computePrice() {
        int precio = SellSeat.size() * sala.getPrice();
        return precio;
    }

    /**
     * Devuelve el nombre de la película
     *
     * @return titulo que corresponde a la opción de la compra de películas
     */
    @Override
    public String getTitle() {
        return java.util.ResourceBundle.getBundle("Backend/" + this.getMultiplex().getIdiom()).getString("Comprar");
    }

    /**
     * Serializa el estado del múltiplex
     *
     * @throws java.io.FileNotFoundException
     */
    private void serializeMultiplexState() throws FileNotFoundException, IOException {
        ObjectOutputStream backUp = new ObjectOutputStream(new FileOutputStream("serializador.bin"));
        backUp.writeObject(this.state);
        backUp.close();
    }

    /**
     * Deserializa el estado del múltiplex
     */
    private void deserializeMultiplexState() throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fileStream = new FileInputStream("serializador.bin");
        ObjectInputStream objectStream = new ObjectInputStream(fileStream);
        this.state = (MultiplexState) objectStream.readObject();
    }

}
