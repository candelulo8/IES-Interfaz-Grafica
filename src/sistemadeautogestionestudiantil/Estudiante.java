
package sistemadeautogestionestudiantil;

import java.util.ArrayList;


public class Estudiante extends PersonaAcademica implements Consultable {
   
   private String carrera;
   private int anioIngreso;
   private ArrayList<InscripcionMateria> materias;
   
   Estudiante(String carrera,String legajo, String nombre, int anioIngreso){
       super(nombre, legajo);
       this.carrera = carrera;
       this.anioIngreso = anioIngreso;
       this.materias = new ArrayList<>();
   }
   


   //Iscribirse
   public void inscribirse(Materia m){
      if (getInscripcion(m.getCodigo())== null){
       materias.add(new InscripcionMateria(m));
       }
      else{
       System.out.println("Ya estas inscripto a esa materia. ");
       }
    }
   


   //Dar de baja la materia
   public void darDeBaja(String codigoMateria){
       InscripcionMateria insc = getInscripcion(codigoMateria);
     if (insc != null){
       materias.remove(insc);
       System.out.println("Materia eliminada. ");
     }
      else{
       System.out.println("No se encontro ninguna materia. ");  
      }
    }
  
  //Buscar Inscripcion
     public InscripcionMateria getInscripcion(String codigoMateria){
       
       for(InscripcionMateria i : materias){
       
       if (i.getMateria().getCodigo().equalsIgnoreCase(codigoMateria)) {
       return i;
       }
      }
       return null;
    }
     
    
    
     //promedio
     public double getPromedioGeneral(){
     if (materias.isEmpty()) {
     return 0;
     }

      double suma = 0;
      int cont = 0;

     // recorro todas las materias
     for (InscripcionMateria i : materias) {

         double prom = i.getPromedio();

         // solo tomo las que tienen promedio
         if (prom > 0) {
         suma = suma + prom;
         cont++;
        }
    }
     // evito dividir por 0
      if (cont == 0) {
      return 0;
     }
      return suma / cont;
    }

     
    // Materias en riesgo (75% a 85%)
    public ArrayList<InscripcionMateria> getMateriasCriticas() {
     
       ArrayList<InscripcionMateria> criticas = new ArrayList<>();

       for (InscripcionMateria i : materias) {
           double asistencia = i.getPorcentajeAsistencia();
           if (asistencia >= 75 && asistencia <= 85) {
               criticas.add(i);
           }
        } 
         return criticas;
    }
    // Mostrar resumen (override obligatorio)
   @Override
    public void mostrarResumen() {
       System.out.println("=== PERFIL DEL ESTUDIANTE ===");
       System.out.println("Nombre: " + getNombre());
       System.out.println("Legajo: " + getLegajo());
       System.out.println("Carrera: " + carrera);
       System.out.println("Año ingreso: " + anioIngreso);
       System.out.println("Cantidad de materias: " + materias.size());
   }   
}


