package sistemadeautogestionestudiantil;

import java.util.ArrayList;

public class InscripcionMateria implements Evaluable{
    private Materia materia;
    private int totalClases;
    private int clasesAsistidas;
    private ArrayList<Double> notas = new ArrayList<>();
    
    // constructor
    public InscripcionMateria(Materia materia, int totalClases,  int clasesAsistidas){
        if (totalClases < 0 || clasesAsistidas < 0 || clasesAsistidas > totalClases) {
            throw new IllegalArgumentException("Valores de asistencia inválidos");
        }
        this.materia = materia;
        this.totalClases = totalClases;
        this.clasesAsistidas = clasesAsistidas;
    }
    
    public InscripcionMateria(Materia materia){
        this.materia = materia;
        this.totalClases = 0;
        this.clasesAsistidas = 0;
    }
    
    // get para Obtener la materia 
    public Materia getMateria(){
        return materia;
    }
    
    public double getPorcentajeAsistencia(){
        if (totalClases == 0) return 0;
        return (double)clasesAsistidas*100/totalClases;
    }
    
    @Override
    public String getCondicion(){
        double porcentaje = getPorcentajeAsistencia();
        String condicion;
        if (porcentaje >= 75) {
            condicion = "Regular";
        } else {
           condicion = "Libre por inasistencias";
        }
        return condicion;
    }
    
    @Override
    public double getPromedio(){
        double acumulador = 0;
        if (notas.isEmpty()) {
            return 0;
        }
        for (int i = 0; i < notas.size(); i++) {
                acumulador += notas.get(i);
        }
        return acumulador/notas.size();
    }
    
    public void registrarAsistencia(boolean presente){
        totalClases++;
        if (presente) {
            clasesAsistidas++;
        }
    }
    
    public void agregarNota(double nota){
        if (nota >=0 && nota<=10) {
            notas.add(nota);
        } else{
            System.out.println("La nota debe estar entre 0 y 10");
        }
    }
    
    @Override
    public boolean estaAprobada(){
        double promedio = getPromedio();
        String condicion = getCondicion();
        
        if (promedio >= 6 && condicion.equals("Regular")) {
            return true;
        } else{ return false;}
    }

}
