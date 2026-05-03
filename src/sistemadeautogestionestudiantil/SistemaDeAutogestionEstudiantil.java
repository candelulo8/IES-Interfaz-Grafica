package sistemadeautogestionestudiantil;

import java.util.ArrayList;

public class SistemaDeAutogestionEstudiantil {
    public static void main(String[] args) {
        // PRUEBA: MATERIA 
        System.out.println("===== PRUEBA MATERIA ======");
        Materia interfaz = new Materia("Interfaz", "INT3",1,2026);
        Materia arqui = new Materia("Arquitectura","INT3",3,2026);
        
        //trae los valores
        System.out.println(interfaz.getCodigo());
        System.out.println(interfaz.getNombre());
        System.out.println(interfaz.getCuatrimestre());
        System.out.println(interfaz.getAnio());
        
        // modifica 
        interfaz.setCuatrimestre(3);
        
        System.out.println("==================");
        System.out.println(arqui.getCodigo());
        System.out.println(arqui.getNombre());
        System.out.println(arqui.getCuatrimestre());
        System.out.println(arqui.getAnio());
        
         //modifica 
        arqui.setCodigo("INT3");
        arqui.setCuatrimestre(4);
        
        // PRUEBA: INSCRIPCION MATERIA 
        System.out.println("===== PRUEBA INSCRIPCION MATERIA ======");
        InscripcionMateria inscInterfaz = new InscripcionMateria(interfaz);
        
        // asistencias
        inscInterfaz.registrarAsistencia(true);
        inscInterfaz.registrarAsistencia(true);
        inscInterfaz.registrarAsistencia(false);
        inscInterfaz.registrarAsistencia(true);

        System.out.println("Materia: " + inscInterfaz.getMateria().getNombre()); // Interfaz
        System.out.println("Porcentaje asistencia: " + inscInterfaz.getPorcentajeAsistencia()); // 75.0
        System.out.println("Condición: " + inscInterfaz.getCondicion()); // Regular
        System.out.println("");
        
         // Agregar notas
        inscInterfaz.agregarNota(8.0);
        inscInterfaz.agregarNota(7.5);
        inscInterfaz.agregarNota(11.0); // inválida, debe mostrar error

        System.out.println("Promedio: " + inscInterfaz.getPromedio()); // 7.75
        System.out.println("¿Aprobada? " + inscInterfaz.estaAprobada()); // true
        
        // ESTADO ACADEMICO VIENE DE EVALUABLE
        inscInterfaz.mostrarEstadoAcademico();
        
        // Crear inscripción con constructor completo (ya con datos de asistencia)
        System.out.println("\n--- Inscripción con asistencia baja ---");
        InscripcionMateria inscArqui = new InscripcionMateria(arqui, 10, 6); // 60% → Libre

        System.out.println("Condición: " + inscArqui.getCondicion()); // Libre
        inscArqui.agregarNota(9.0);
        System.out.println("Promedio: " + inscArqui.getPromedio()); // 9.0
        System.out.println("¿Aprobada? " + inscArqui.estaAprobada()); // false (Libre aunque promedio alto)
        inscArqui.mostrarEstadoAcademico();

        // Constructor con valores inválidos (debe lanzar excepción)
        System.out.println("\n--- Prueba constructor inválido ---");
        try {
            InscripcionMateria invalida = new InscripcionMateria(interfaz, 5, 10); // asistidas > total
        } catch (IllegalArgumentException e) {
            System.out.println("Error capturado: " + e.getMessage());
        }
        // ===== PRUEBA ESTUDIANTE + INSCRIPCION MATERIA =====
        System.out.println("\n===== PRUEBA ESTUDIANTE ======");

        // Crear estudiante
        Estudiante alumna = new Estudiante("Analista de sistemas l", "22001", "Ana Garcia", 2023);
        alumna.mostrarResumen(); // muestra perfil (0 materias)

        // Crear materias
        Materia matematica = new Materia("Matematica", "MAT1", 1, 2026);
        Materia programacion = new Materia("Programacion", "PRG1", 1, 2026);

        // Inscribirse
        alumna.inscribirse(matematica);
        alumna.inscribirse(programacion);
        alumna.inscribirse(matematica); // Error: ya inscripta
        
        // Buscar inscripción y registrar asistencias
        InscripcionMateria inscMat = alumna.getInscripcion("MAT1");
        inscMat.registrarAsistencia(true);
        inscMat.registrarAsistencia(true);
        inscMat.registrarAsistencia(false);
        inscMat.registrarAsistencia(true);
        inscMat.agregarNota(8.0);
        inscMat.agregarNota(6.5);

        InscripcionMateria inscProg = alumna.getInscripcion("PRG1");
        inscProg.registrarAsistencia(true);
        inscProg.registrarAsistencia(false);
        inscProg.registrarAsistencia(false);
        inscProg.registrarAsistencia(false); // 25% → Libre
        inscProg.agregarNota(9.0);
        
        // Ver estado de cada materia
        System.out.println("\n--- Estado Matematica ---");
        inscMat.mostrarEstadoAcademico();

        System.out.println("\n--- Estado Programacion ---");
        inscProg.mostrarEstadoAcademico(); // Libre aunque tiene nota alta

        // Promedio general
        System.out.println("\nPromedio general: " + alumna.getPromedioGeneral());

        // Materias críticas (asistencia entre 75% y 85%)
        System.out.println("Materias críticas: " + alumna.getMateriasCriticas().size());

        // Dar de baja
        alumna.darDeBaja("PRG1");    // OK
        alumna.darDeBaja("PRG1");    // Error: no existe

        // Perfil final
        alumna.mostrarResumen(); // ahora muestra 1 materia
        // Probar polimorfismo con Consultable
        System.out.println("\n===== PRUEBA POLIMORFISMO CONSULTABLE =====");
        ArrayList<Consultable> consultables = new ArrayList<>();
        consultables.add(alumna);       // Estudiante implements Consultable
        consultables.add(matematica);   // Materia implements Consultable (falta agregar esto)

        for (Consultable c : consultables) {
            c.mostrarResumen(); // llama al mostrarResumen() de cada clase
            System.out.println("---");
        }
    }
}
