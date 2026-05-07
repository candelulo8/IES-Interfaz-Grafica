package sistemadeautogestionestudiantil;
import java.util.Scanner;
import java.util.ArrayList;

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
        int anioIngreso = sc.nextInt();
         alumno = new Estudiante(carrera, legajo, nombre, anioIngreso);
        System.out.println("\n¡Bienvenido/a, " + alumno.getNombre() + "!");

        menuPrincipal();
    }
    public static void menuPrincipal() {
        int opcion;
        do {
            System.out.println("\n┌─────────────────────────────────┐");
            System.out.println("│         MENÚ PRINCIPAL          │");
            System.out.println("├─────────────────────────────────┤");
            System.out.println("│  1. Ver perfil del estudiante   │");
            System.out.println("│  2. Gestión de materias         │");
            System.out.println("│  3. Registrar asistencia        │");
            System.out.println("│  4. Registrar calificación      │");
            System.out.println("│  5. Ver reportes                │");
            System.out.println("│  0. Salir                       │");
            System.out.println("└─────────────────────────────────┘");
            System.out.print("Opción: ");
            opcion = sc.nextInt();

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
            opcion = sc.nextInt();

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
        sc.nextLine();

        System.out.print("Nombre de la materia: ");
        String nombre = sc.nextLine().trim();

        System.out.print("Código: ");
        String codigo = sc.nextLine().trim().toUpperCase();

        System.out.print("Cuatrimestre (1 o 2): ");
        int cuatrimestre = sc.nextInt();

        System.out.print("Año: ");
        int anio = sc.nextInt();

        Materia m = new Materia(nombre, codigo, cuatrimestre, anio);
        alumno.inscribirse(m);                                       
    }
    
    public static void registrarAsistencia(){
        System.out.println("Ingrese el codigo de la materia: ");
        String codigoMateria = sc.nextLine();
        
        InscripcionMateria inscripcion = alumno.getInscripcion(codigoMateria);
        while (inscripcion == null) {
            System.out.println("Error: no encontramos ese codigo: "+codigoMateria);
            System.out.println("Ingrese nuevamente el codigo de la materia");
            codigoMateria = sc.nextLine();
            inscripcion = alumno.getInscripcion(codigoMateria);
        }
        System.out.println("Estuvo presente?");
        String respuesta =sc.nextLine();
        if (respuesta.equalsIgnoreCase("Si")) {
            inscripcion.registrarAsistencia(true);
        } else {
            inscripcion.registrarAsistencia(false);
        }
        //imprimir porcentaje actualizado:
        double porcentajeActual = inscripcion.getPorcentajeAsistencia(); 
        System.out.println("Tu porcentaje de asistencias actualizado: "+porcentajeActual);
        String condicion = inscripcion.getCondicion();
        //Advertencias
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
            System.out.printf(" %-6s %-20s | Cond: %-25s | Asist: %5.lf%% | Prom: %.2f%n", 
                i.getMateria().getCodigo(),
                i.getMateria().getCodigo(),
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
        
        int opt = sc.nextInt();
        
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
                int cuat = sc.nextInt();
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
    
    static void menuCalificaciones() {
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
        double nota = sc.nextDouble();
        insc.agregarNota(nota);

        // Mostrar promedio actualizado
        if (!insc.getNotas().isEmpty()) {
            System.out.printf("   Promedio actualizado: %.2f%n", insc.getPromedio());
        }
    }
    
    }