import java.util.Scanner;
import java.util.ArrayList;
class TrabajoSP{
    String nombre;
    int duracion;
    int llegada;
    public void Trabajo(String nombre, int duracion, int llegada){
        this.nombre=nombre;
        this.duracion=duracion;
        this.llegada=llegada;
    }
}
class TrabajoCP{
    String nombre;
    int duracion;
    int llegada;
    int prioridad;
    public void Trabajo(String nombre, int duracion, int llegada, int prioridad){
        this.nombre=nombre;
        this.duracion=duracion;
        this.llegada=llegada;
        this.prioridad=prioridad;
    }
}
public class Main{
    public static void main (String[] args){
        try (Scanner sc = new Scanner(System.in)) {
            ArrayList<TrabajoSP> trabajos = new ArrayList<>();
            System.out.print("Ingrese que tipo de ");

            //Pedir cantidad de trabajos
            System.out.print("Ingrese la cantida de trabajos: ");
            int cantT= sc.nextInt();
            sc.nextLine();

            for (int i=0;i < cantT; i++){
                System.out.print("\n--- Trabajo "+(i+1)+" ---");
                System.out.print("Coloque nombre del trabajo: ");
                String nombre=sc.nextLine();
                System.out.print("Coloque tiempo de llegada del trabajo: ");
                int llegada=sc.nextInt();
                System.out.print("Coloque numero de rafajas usadas: ");
                int duracion=sc.nextInt();
            }
        }

    }
}

