import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
class TrabajoSP{
    String nombre;
    int duracion;
    int llegada;
    boolean completado;
    public void Trabajo(String nombre, int duracion, int llegada){
        this.nombre=nombre;
        this.duracion=duracion;
        this.llegada=llegada;
        this.completado=false;
    }
}
class TrabajoCP{
    String nombre;
    int duracion;
    int llegada;
    int prioridad;
    boolean completado;
    int restante;
    boolean enCola;
    public void Trabajo(String nombre, int duracion, int llegada, int prioridad){
        this.nombre=nombre;
        this.duracion=duracion;
        this.llegada=llegada;
        this.prioridad=prioridad;
        this.completado=false;
        this.restante=duracion;
        this.enCola=false;
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
            System.out.printf("%-10s %-10s %-10s %-10s\n", "Nombre", "Llegada", "Rafagas", "Prioridad");
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
            double waitT=0;
            double respT=0;
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
                int tiempoActual = 0;
                int completados = 0;
                int total = trabajosSP.size();

                while (completados < total) {
                    TrabajoSP seleccionado = null;

                    for (TrabajoSP t : trabajosSP) {
                        if (!t.completado && t.llegada <= tiempoActual) {
                            if (seleccionado == null || t.duracion < seleccionado.duracion) {
                                seleccionado = t;
                            }
                        }
                    }

                    if (seleccionado == null) {
                        tiempoActual++;
                        continue;
                    }

                    int inicio = tiempoActual;
                    int fin = inicio + seleccionado.duracion;
                    waitT += inicio - seleccionado.llegada;
                    respT += fin;

                    System.out.printf("Trabajo: %s | Inicio: %d | Fin: %d\n",
                            seleccionado.nombre, inicio, fin);

                    tiempoActual = fin;
                    seleccionado.completado = true;
                    completados++;
                }

                
            } else if (tipoAlgoritmo == 3) {
                int tiempoActual = 0;
                int completados = 0;

                while (completados < trabajosCP.size()) {
                    TrabajoCP seleccionado = null;

                    for (TrabajoCP t : trabajosCP) {
                        if (!t.completado && t.llegada <= tiempoActual) {

                            if (seleccionado == null || t.prioridad < seleccionado.prioridad) {
                                seleccionado = t;
                            }

                            // desempate: si tienen misma prioridad, escoger el que llegó antes
                            else if (seleccionado != null &&
                                    t.prioridad == seleccionado.prioridad &&
                                    t.llegada < seleccionado.llegada) {
                                seleccionado = t;
                            }
                        }
                    }

                    // si no hay ningún trabajo disponible todavía
                    if (seleccionado == null) {
                        tiempoActual++;
                        continue;
                    }

                    int inicio = tiempoActual;
                    int fin = inicio + seleccionado.duracion;

                    int espera = inicio - seleccionado.llegada;
                    int retorno = fin;

                    waitT += espera;
                    respT += retorno;

                    System.out.printf(
                        "Trabajo: %s | Inicio: %d | Fin: %d | Prioridad: %d\n",
                        seleccionado.nombre,
                        inicio,
                        fin,
                        seleccionado.prioridad
                    );

                    tiempoActual = fin;
                    seleccionado.completado = true;
                    completados++;
                }
            } else if (tipoAlgoritmo == 4) {
    System.out.print("\nIngrese el quantum: ");
    int quantum = sc.nextInt();

    int tiempoActual = 0;
    int completados = 0;

    Queue<TrabajoCP> cola = new LinkedList<>();

    trabajosCP.sort((a, b) -> a.llegada - b.llegada);

    while (completados < trabajosCP.size()) {

        // agregar trabajos disponibles al inicio
        for (TrabajoCP t : trabajosCP) {
            if (!t.completado && !t.enCola && t.restante > 0 && t.llegada <= tiempoActual) {
                cola.add(t);
                t.enCola = true;
            }
        }

        if (cola.isEmpty()) {
            tiempoActual++;
            continue;
        }

        TrabajoCP actual = cola.poll();
        actual.enCola = false;

        int inicio = tiempoActual;
        int ejecucion = Math.min(quantum, actual.restante);
        int fin = inicio + ejecucion;

        actual.restante -= ejecucion;
        tiempoActual = fin;

        System.out.printf(
            "Trabajo: %s | Inicio: %d | Fin: %d | Ejecutado: %d | Restante: %d\n",
            actual.nombre,
            inicio,
            fin,
            ejecucion,
            actual.restante
        );

        if (actual.restante == 0) {
            actual.completado = true;
            completados++;

            int retorno = tiempoActual - actual.llegada;
            int espera = retorno - actual.duracion;

            waitT += espera;
            respT += fin;
        }

        // agregar nuevos que llegaron durante la ejecución, EXCEPTO el actual
        for (TrabajoCP t : trabajosCP) {
            if (t != actual && !t.completado && !t.enCola && t.restante > 0 && t.llegada <= tiempoActual) {
                cola.add(t);
                t.enCola = true;
            }
        }

        // si no terminó, vuelve al final
        if (!actual.completado) {
            cola.add(actual);
            actual.enCola = true;
        }
    }
}else {
                System.out.println("Opción no válida.");
            }

            waitT = waitT / cantT;
            respT = respT / cantT;
            System.out.printf("\nTiempo de espera promedio: %.2f\n", (double) waitT,"ut");
            System.out.printf("Tiempo de respuesta promedio: %.2f\n", (double) respT,"ut");  
        }

    }
}

