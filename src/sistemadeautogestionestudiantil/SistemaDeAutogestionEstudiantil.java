package sistemadeautogestionestudiantil;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;

public class SistemaDeAutogestionEstudiantil {
    static Scanner sc = new Scanner(System.in);
    static Estudiante alumno;
    public static void main(String[] args) {
          
     System.out.println("========================================");
        System.out.println("   SISTEMA DE AUTOGESTIÓN ESTUDIANTIL  ");
        System.out.println("=========================================");
        System.out.println();
        // Datos del estudiante al inicio
        System.out.print("Ingresá tu nombre: ");
        String nombre = sc.nextLine().trim();
        System.out.print("Ingresá tu legajo: ");
        String legajo = sc.nextLine().trim();
        System.out.print("Ingresá tu carrera: ");
        String carrera = sc.nextLine().trim();
        System.out.print("Ingresá el año de ingreso: ");
        int anioIngreso = 0;
        boolean valido = false;
        while (!valido) {
            int input = leerOpcion(sc);
            if (input == -1) {
                System.out.print("Error: ingresá solo números: ");
            } else if (input > 2026) {
                System.out.print("Error: el año no puede ser mayor a 2026: ");
            } else {
                anioIngreso = input;
                valido = true;
            }
        }
         alumno = new Estudiante(carrera, legajo, nombre, anioIngreso);
        System.out.println("\n¡Bienvenido/a, " + alumno.getNombre() + "!");

        menuPrincipal();
    }
    public static void menuPrincipal() {
        int opcion;
        do {
            System.out.println("┌───────────────────────────────────────┐");
            System.out.println("│         MENÚ PRINCIPAL          │");
            System.out.println("├───────────────────────────────────────┤");
            System.out.println("│  1. Ver perfil del estudiante   │");
            System.out.println("│  2. Gestion de materias         │");
            System.out.println("│  3. Registrar asistencia        │");
            System.out.println("│  4. Registrar calificacion      │");
            System.out.println("│  5. Ver reportes                │");
            System.out.println("│  0. Salir                       │");
            System.out.println("└───────────────────────────────────────┘");
            System.out.print("Opción: ");
            opcion = leerOpcion(sc);

            switch (opcion) {
                case 1 -> verPerfil();
                case 2 -> menuMaterias();
                case 3 -> registrarAsistencia();
                case 4 -> menuCalificaciones();
                case 5 -> menuReportes();
                case 0 -> System.out.println("¡Hasta luego!");
                default -> System.out.println("⚠  Opción inválida. Intentá de nuevo.");
            }
        } while (opcion != 0);
    }

    
    //  1. VER PERFIL
    
    public static void verPerfil() {
        System.out.println();
        alumno.mostrarResumen();
    }
     
    // 2. MENU MATERIAS
    public static void menuMaterias(){
     int opcion;
        do {
            System.out.println("\n┌───────────────────────────────────┐");
            System.out.println("  │       GESTIÓN DE MATERIAS     │");
            System.out.println("  ├────────────────────────────────────┤");
            System.out.println("  │  1. Inscribirse a una materia  │");
            System.out.println("  │  2. Darse de baja              │");
            System.out.println("  │  3. Listar materias            │");
            System.out.println("  │  4. Buscar materia             │");
            System.out.println("  │  0. Volver                     │");
            System.out.println("  └─────────────────────────────────────┘");
            System.out.print("Opción: ");
            opcion = leerOpcion(sc);

            switch (opcion) {
                case 1 -> inscribirseMateria();
                case 2 -> bajaMateriaMenu();
                case 3 -> listarMaterias();
                case 4 -> buscarMateriaMenu();
                case 0 -> {}
                default -> System.out.println("⚠  Opción inválida.");
            }
        } while (opcion != 0);
    }

    public static void inscribirseMateria() {

        System.out.print("Nombre de la materia: ");
        String nombre = sc.nextLine().trim();
        System.out.print("Código: ");
        String codigo = sc.nextLine().trim().toUpperCase();
        System.out.print("Cuatrimestre (1 o 2): ");
        int cuatrimestre = 0;
        while (cuatrimestre != 1 && cuatrimestre != 2) {
            cuatrimestre = leerOpcion(sc);
            if (cuatrimestre != 1 && cuatrimestre != 2) {
                System.out.print("Error: ingresá 1 o 2: ");
            }
        }
        System.out.print("Año: ");
        int anio = leerOpcion(sc);
        Materia m = new Materia(nombre, codigo, cuatrimestre, anio);
        alumno.inscribirse(m);                                       
        System.out.println("¡¡¡ Te inscribiste correctamente a "+ m.getNombre()+" !!!");
    }
    
    public static void registrarAsistencia(){
        System.out.print("Ingrese el codigo de la materia: ");
        String codigoMateria = sc.nextLine().trim().toUpperCase();

        InscripcionMateria inscripcion = alumno.getInscripcion(codigoMateria);
        while (inscripcion == null) {
            System.out.println("Error: no encontramos ese codigo: "+codigoMateria);
            System.out.print("Ingrese nuevamente el codigo de la materia: ");
            codigoMateria = sc.nextLine().trim().toUpperCase();
            inscripcion = alumno.getInscripcion(codigoMateria);
        }
        System.out.print("Estuvo presente? (Si/No/0 para volver): ");
        String respuesta = sc.nextLine();
        while (!respuesta.equalsIgnoreCase("Si") && !respuesta.equalsIgnoreCase("No") && !respuesta.equals("0")) {
            System.out.print("Error: ingresá Si, No o 0 para volver: ");
            respuesta = sc.nextLine();
        }
        if (respuesta.equals("0")) return;

        if (respuesta.equalsIgnoreCase("Si")) {
            inscripcion.registrarAsistencia(true);
        } else {
            inscripcion.registrarAsistencia(false);
        }

        double porcentajeActual = inscripcion.getPorcentajeAsistencia(); 
        System.out.println("Tu porcentaje de asistencias actualizado: "+porcentajeActual);
        String condicion = inscripcion.getCondicion();
        if (porcentajeActual>=75 && porcentajeActual<=80) {
            System.out.println("Su condicion es: "+condicion +"\nSe encuentra en una zona de riesgo por inasistencias, no falte más :)");
        } else if(porcentajeActual<75){
            System.out.println("Se encuentra en perdida de regularidad por ende su condición es "+ condicion);
        }
    }
    
    public static void bajaMateriaMenu(){  
       if (alumno.getMaterias().isEmpty()){System.out.println("   No tenes materias incriptas."); return;}
           System.out.println("\nCodigo de materia a dar de baja: ");
           String codigo = sc.nextLine().trim();
           alumno.darDeBaja(codigo);
    }
 
    public static void listarMaterias(){
    ArrayList<InscripcionMateria> lista = alumno.getMaterias();
    if (lista.isEmpty()){ System.out.println(" No tenes materias inscriptas."); return;}
        System.out.println("\n---Materias inscriptas---");
        for(InscripcionMateria i : lista){
            System.out.printf(" %-6s %-20s | Cond: %-25s | Asist: %.1f%%  | Prom: %.2f%n", 
                i.getMateria().getCodigo(),
                i.getMateria().getNombre(),
                i.getCondicion(),
                i.getPorcentajeAsistencia(),
                i.getPromedio());
           }
       }
    
    public static void buscarMateriaMenu(){
        System.out.println("\n--Buscar materia--");
        System.out.println("  1. Por codigo exacto");
        System.out.println("  2. Por nombre");
        System.out.println("  3. Por cuatrimestre");
        System.out.println("Opcion:");
        
        int opt = leerOpcion(sc);
        
        
        switch (opt){
            case 1 -> {
                System.out.println("Codigo: ");
                String cod = sc.nextLine().trim();
                InscripcionMateria insc = alumno.getInscripcion(cod);
                if (insc != null) imprimirInscripcion(insc);
                else System.out.println("  No se encontro.");
                }
            case 2 -> {
                System.out.println("Nombre(o fragmento):");
                String frag = sc.nextLine().trim();
                ArrayList<InscripcionMateria> res = alumno.buscarPorNombre(frag);
                if (res.isEmpty()) System.out.println("   No se encontraron resultados.");
                else res.forEach(SistemaDeAutogestionEstudiantil::imprimirInscripcion);
               }
            case 3 -> {
                System.out.println("Cuatrimestre (1 o 2): ");
                int cuat = leerOpcion(sc);
                ArrayList<InscripcionMateria> res = alumno.getInscripcion(cuat);
                if (res.isEmpty()) System.out.println("No se encontraron materias en ese cuatrimestre:");
                else res.forEach(SistemaDeAutogestionEstudiantil::imprimirInscripcion);
               }
            default -> System.out.println(" Opcion invalida.");
           }
       }
    
    public static void imprimirInscripcion(InscripcionMateria i){
        System.out.printf("  [%s] %s — %s — Asist: %.1f%% — Prom: %.2f%n",
            i.getMateria().getCodigo(),
            i.getMateria().getNombre(),
            i.getCondicion(),
            i.getPorcentajeAsistencia(),
            i.getPromedio());
       }
    
    public static void menuCalificaciones() {
        if (alumno.getMaterias().isEmpty()) { System.out.println("   No tenés materias inscriptas."); return; }
        listarMaterias();
        System.out.print("\nCódigo de la materia: ");
        String codigo = sc.nextLine().trim();
        InscripcionMateria insc = alumno.getInscripcion(codigo);
        if (insc == null) { System.out.println("   Materia no encontrada."); return; }

        // Mostrar notas actuales
        ArrayList<Double> notas = insc.getNotas();
        if (!notas.isEmpty()) {
            System.out.print("   Notas actuales: ");
            for (int i = 0; i < notas.size(); i++) {
                System.out.printf("%.1f%s", notas.get(i), i < notas.size() - 1 ? ", " : "\n");
            }
            System.out.printf("   Promedio actual: %.2f%n", insc.getPromedio());
        }

        System.out.print("Nueva nota (0-10): ");
        double nota = Double.parseDouble(sc.nextLine().trim());
        insc.agregarNota(nota);

        // Mostrar promedio actualizado
        if (!insc.getNotas().isEmpty()) {
            System.out.printf("   Promedio actualizado: %.2f%n", insc.getPromedio());
        }
    }
    
    public static void menuReportes(){
        int opcion;
        do {
            System.out.println("\n┌─────────────────────────────────────────┐");
            System.out.println("  │           MENU DE REPORTES          │");
            System.out.println("  ├──────────────────────────────────────────┤");
            System.out.println("  │  1. Reporte de situacion general   │");
            System.out.println("  │  2. Reporte de materias en riesgo  │");
            System.out.println("  │  3. Reporte de materias logradas   │");
            System.out.println("  │  0. Volver                     │");
            System.out.println("  └─────────────────────────────────────┘");
            System.out.print("Opción: ");
            opcion = leerOpcion(sc);

            switch (opcion) {
                case 1 -> {
                    int contRegular = 0;
                    int contLibre = 0;
                    List<InscripcionMateria> lista = alumno.getMaterias();

                    System.out.println("\n╔══════════════════════════════════════════════╗");
                    System.out.println("║         REPORTE DE SITUACION GENERAL        ║");
                    System.out.println("╚══════════════════════════════════════════════╝");

                    for (InscripcionMateria inscripcion : lista) {
                        String estado;
                        if (inscripcion.estaAprobada()) {
                            estado = "Aprobada";
                        } else if (inscripcion.getCondicion().equals("Regular")) {
                            estado = "En curso";
                            contRegular++;
                        } else {
                            estado = "Libre";
                            contLibre++;
                        }
                        System.out.println("┌──────────────────────────────────────────────┐");
                        System.out.printf("│ Materia   : %-32s│%n", inscripcion.getMateria().getNombre());
                        System.out.printf("│ Condicion : %-32s│%n", inscripcion.getCondicion());
                        System.out.printf("│ Asistencia: %-31.1f%%│%n", inscripcion.getPorcentajeAsistencia());
                        System.out.printf("│ Promedio  : %-32.2f│%n", inscripcion.getPromedio());
                        System.out.printf("│ Estado    : %-32s│%n", estado);
                        System.out.println("└──────────────────────────────────────────────┘");
                    }

                    System.out.println("╔══════════════════════════════════════════════╗");
                    System.out.printf("║ Promedio general  : %-24.2f║%n", alumno.getPromedioGeneral());
                    System.out.printf("║ Materias regulares: %-24d║%n", contRegular);
                    System.out.printf("║ Materias en riesgo: %-24d║%n", alumno.getMateriasCriticas().size());
                    System.out.printf("║ Materias libres   : %-24d║%n", contLibre);
                    System.out.println("╚══════════════════════════════════════════════╝");
                }
                
                case 2 -> {
                    System.out.println("\n╔══════════════════════════════════════════════╗");
                    System.out.println("║    ⚠  REPORTE DE MATERIAS EN RIESGO  ⚠     ║");
                    System.out.println("╚══════════════════════════════════════════════╝");

                    ArrayList<InscripcionMateria> enRiesgo = alumno.getMateriasCriticas();
                    Collections.sort(enRiesgo, (a, b) ->
                        Double.compare(a.getPorcentajeAsistencia(), b.getPorcentajeAsistencia()));

                    if (enRiesgo.isEmpty()) {
                        System.out.println("  No hay materias en riesgo.");
                    } else {
                        for (InscripcionMateria materia : enRiesgo) {
                            System.out.println("┌──────────────────────────────────────────────┐");
                            System.out.printf("│ Materia    : %-31s│%n", materia.getMateria().getNombre());
                            System.out.printf("│ Asistencia : %-30.1f%%│%n", materia.getPorcentajeAsistencia());
                            System.out.println("└──────────────────────────────────────────────┘");
                        }
                    }
                }
                case 3 -> {
                            System.out.println("\n╔══════════════════════════════════════════════╗");
                            System.out.println("║      💪 REPORTE DE MATERIAS LOGRADAS 💪     ║");
                            System.out.println("╚══════════════════════════════════════════════╝");

                            ArrayList<Double> todasLasNotas = new ArrayList<>();
                            for (InscripcionMateria inscripcion : alumno.getMaterias()) {
                                if (inscripcion.estaAprobada()) {
                                    System.out.println("┌──────────────────────────────────────────────┐");
                                    System.out.printf("│ Materia  : %-33s│%n", inscripcion.getMateria().getNombre());
                                    System.out.printf("│ Promedio : %-33.2f│%n", inscripcion.getPromedio());
                                    System.out.println("└──────────────────────────────────────────────┘");
                                    todasLasNotas.addAll(inscripcion.getNotas());
                                }
                            }

                            if (!todasLasNotas.isEmpty()) {
                                double suma = 0;
                                for (double nota : todasLasNotas) { suma += nota; }
                                System.out.println("╔══════════════════════════════════════════════╗");
                                System.out.printf("║ Nota maxima          : %-19.1f║%n", Collections.max(todasLasNotas));
                                System.out.printf("║ Nota minima          : %-19.1f║%n", Collections.min(todasLasNotas));
                                System.out.printf("║ Promedio del conjunto: %-19.2f║%n", suma / todasLasNotas.size());
                                System.out.println("╚══════════════════════════════════════════════╝");
                            } else {
                                System.out.println("  No hay materias aprobadas.");
                            }

                    
                }
                case 0 -> {}
                default -> System.out.println("⚠  Opción inválida.");
            }
        } while (opcion != 0);

    }
            private static int leerOpcion(Scanner scanner) {
                try {
                    return Integer.parseInt(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    return -1; // valor inválido que el switch/if no va a matchear
                }
            }
    }