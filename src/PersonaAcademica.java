abstract class PersonaAcademica {
    private String nombre;
    private String legajo;
    
    PersonaAcademica(String nombre, String legajo){
        this.nombre=nombre;
        this.legajo=legajo;
    }
    
    abstract void mostrarResumen();
}
