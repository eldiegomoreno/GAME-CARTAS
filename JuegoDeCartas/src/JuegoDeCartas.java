import java.util.Scanner;

public class JuegoDeCartas {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //Ingreso de Numero de Jugadores
        System.out.println("Ingresa número de jugadores");
        int n = sc.nextInt();
        //Ingresar Número de Partidas
        System.out.println("Ingrese el Número de partidas o ronda");
        int p = sc.nextInt();

        Juego juego =new Juego(n,p);
                juego.jugar();

    }
}
