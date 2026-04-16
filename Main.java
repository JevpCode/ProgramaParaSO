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

            ArrayList<TrabajoCP> trabajosCP = new ArrayList<>();
            ArrayList<TrabajoSP> trabajosSP = new ArrayList<>();

            //Pedir tipo de algoritmo
            System.out.print("Ingrese que tipo de algoritmo desea usar: ");
            System.out.println("\n1. FIFO");
            System.out.println("2. SJF");
            System.out.println("3. Prioridad");
            System.out.println("4. Round Robin");
            int tipoAlgoritmo = sc.nextInt();
            sc.nextLine();
 
            //Pedir cantidad de trabajos
            System.out.print("Ingrese la cantida de trabajos: ");
            int cantT= sc.nextInt();
            sc.nextLine();

            for (int i=0;i < cantT; i++){
                System.out.print("\n--- Trabajo "+(i+1)+" ---\n");
                System.out.print("Coloque nombre del trabajo: ");
                String nombre=sc.nextLine();
                System.out.print("Coloque tiempo de llegada del trabajo: ");
                int llegada=sc.nextInt();
                System.out.print("Coloque numero de rafajas usadas: ");
                int duracion=sc.nextInt();
                if (tipoAlgoritmo == 3||tipoAlgoritmo == 4) {
                    System.out.print("Coloque la prioridad del trabajo: ");
                    int prioridad=sc.nextInt();
                    sc.nextLine(); 
                    TrabajoCP trabajo = new TrabajoCP();
                    trabajo.Trabajo(nombre, duracion, llegada, prioridad);
                    trabajosCP.add(trabajo);
                    
                }else{
                    sc.nextLine(); 
                    TrabajoSP trabajo = new TrabajoSP();
                    trabajo.Trabajo(nombre, duracion, llegada);
                    trabajosSP.add(trabajo);
                }
            }

            System.out.println("\n=== TRABAJOS INGRESADOS ===\n");

            // Encabezado
            System.out.printf("%-10s %-10s %-10s %-10s\n", "Nombre", "Llegada", "Duracion", "Prioridad");
            System.out.println("--------------------------------------------------");

            if (tipoAlgoritmo == 3 || tipoAlgoritmo == 4) {
                for (TrabajoCP t : trabajosCP) {
                    System.out.printf("%-10s %-10d %-10d %-10d\n",
                            t.nombre, t.llegada, t.duracion, t.prioridad);
                }
            } else {
                for (TrabajoSP t : trabajosSP) {
                    System.out.printf("%-10s %-10d %-10d %-10s\n",
                            t.nombre, t.llegada, t.duracion, "-");
                }
            }
            
            System.out.println("--------------------------------------------------\n");
            int waitT=0;
            int respT=0;
            if (tipoAlgoritmo == 1) {
                trabajosSP.sort((a, b) -> a.llegada - b.llegada);
                int tiempoActual = 0;
                
                for (TrabajoSP t : trabajosSP) {
                    if (tiempoActual < t.llegada) {
                        tiempoActual = t.llegada;
                    }
                    int inicio = tiempoActual;
                    int fin = inicio + t.duracion;
                    waitT += inicio - t.llegada;
                    respT += fin;
                    System.out.printf("Trabajo: %s | Inicio: %d | Fin: %d\n", t.nombre, inicio, fin);
                    tiempoActual = fin;
                }
            } else if (tipoAlgoritmo == 2) {
                
            } else if (tipoAlgoritmo == 3) {
                
            } else if (tipoAlgoritmo == 4) {
                
            } else {
                System.out.println("Opción no válida.");
            }
        }

    }
}

