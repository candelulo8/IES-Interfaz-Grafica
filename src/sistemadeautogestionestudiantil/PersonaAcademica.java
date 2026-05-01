package sistemadeautogestionestudiantil;

abstract class PersonaAcademica {
    private String nombre;
    private String legajo;
    PersonaAcademica(String nombre, String legajo){
        this.nombre = nombre;
        this.legajo = legajo;
    }
    
    void getNombre(){
        if (nombre.isEmpty() || nombre ==null ) {
            System.out.println("El nombre no puede estar vacío");
        }
    }
    void getLegajo (){
        if (legajo == null) {
            System.out.println("El legajo no puede ser nulo");
        }
    }
    
    abstract void mostrarResumen(); 
}
