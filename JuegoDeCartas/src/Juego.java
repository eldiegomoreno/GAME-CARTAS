import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Juego {
    private List<Jugador> jugadores;
    private List<Cartas> cartas;
    private int numeroRondas;

    public Juego(int numeroJugadores, int numeroRondas) {
        this.jugadores = crearJugadores(numeroJugadores);
        this.numeroRondas = numeroRondas;
    }

    private List<Jugador> crearJugadores(int numeroJugadores) {
        List<Jugador> jugadores = new ArrayList<Jugador>();
        for(int i= 0; i < numeroJugadores; i++) {
            jugadores.add(new Jugador("Jugador" + (i + 1), 50)); // Se añadió 1 para evitar Jugador0
        }
        return jugadores;
    }

    public List<Cartas> crearBaraja() {
        List<Cartas> baraja = new ArrayList<>();
        String[] simbolos = {"Corazón", "Diamante", "Trébol", "Espadas"};
        String[] valores = {"As", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        for(String simbolo : simbolos) {
            for(String valor : valores) {
                Cartas carta = new Cartas(valor, simbolo);
                baraja.add(carta);
            }
        }
        return baraja;
    }

    // Revuelve la baraja de cartas
    private void barajar() {
        Collections.shuffle(cartas);
    }

    // Reparte las cartas a los jugadores
    private void repartirCartas() {
        for(Jugador jugador : jugadores) {
            for(int i = 0; i < 5; i++) {
                jugador.agregarCarta(cartas.remove(0));
            }
        }
    }

    // Limpia las cartas de los jugadores para la siguiente ronda
    private void limpiarCartas() {
        for(Jugador jugador : jugadores) {
            jugador.borrarCartas();
        }
    }

    // Juega el juego por un número determinado de rondas
    public void jugar() {
        for (int ronda = 0; ronda < numeroRondas; ronda++) {
            System.out.println("\n--- Ronda: " + (ronda + 1) + " ---");
            this.cartas = crearBaraja();
            barajar();
            repartirCartas();
            mostrarCartas();
            Jugador ganadorRonda = determinarGanadorRonda();
            if (ganadorRonda != null) {
                ganadorRonda.sumarPunto();
                System.out.println("Ganador de la ronda: " + ganadorRonda.getNombre());
            } else {
                System.out.println("Esta ronda es un empate. No se suman puntos.");
            }
            limpiarCartas();
        }
        declararGanadorFinal();
    }

    // Muestra las cartas de cada jugador
    private void mostrarCartas() {
        for(Jugador jugador : jugadores) {
            System.out.println(jugador.getNombre() + ": " + jugador.getCartas());
        }
    }

    // Determina el ganador de la ronda
    private Jugador determinarGanadorRonda() {
        Jugador ganador = null;
        int mayorValor = -1;
        boolean empate = false;

        for(Jugador jugador : jugadores) {
            int valorCarta = jugador.jugarCarta().getValorNumerico();
            System.out.println("Jugador " + jugador.getNombre() + " carta seleccionada: " + jugador.getCartaActual());
            if (valorCarta > mayorValor) {
                mayorValor = valorCarta;
                ganador = jugador;
                empate = false; // No hay empate si encontramos un mayor valor
            } else if (valorCarta == mayorValor) {
                empate = true; // Si hay un empate, se marca como tal
            }
        }

        return empate ? null : ganador; // Si hubo un empate, no hay ganador
    }

    // Declara el ganador final del juego
    private void declararGanadorFinal() {
        Jugador ganadorFinal = null;
        int maxPuntos = -1;
        boolean empate = false;

        for (Jugador jugador : jugadores) {
            System.out.println(jugador.getNombre() + " - Puntos: " + jugador.getPunteo());
            if (jugador.getPunteo() > maxPuntos) {
                maxPuntos = jugador.getPunteo();
                ganadorFinal = jugador;
                empate = false;
            } else if (jugador.getPunteo() == maxPuntos) {
                empate = true;
            }
        }

        if (empate || ganadorFinal == null) {
            System.out.println("\nEl juego terminó en empate.");
        } else {
            System.out.println("\nEl ganador final es: " + ganadorFinal.getNombre() + " con " + maxPuntos + " puntos.");
        }
    }
}
