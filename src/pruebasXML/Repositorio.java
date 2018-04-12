/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebasXML;

import java.util.ArrayList;

/**
 *
 * @author Juanjo Luque
 */
public class Repositorio {

    ArrayList<Tarea> listadoTareas;
    ArrayList<Proyecto> listadoProyectos;

    private Repositorio() {
        listadoTareas = new ArrayList<>();
        listadoProyectos = new ArrayList<>();
    }

    public static Repositorio getInstance() {
        return RepositorioHolder.INSTANCE;
    }

    private static class RepositorioHolder {

        private static final Repositorio INSTANCE = new Repositorio();
    }

    public ArrayList<Tarea> getListadoTareas() {
        return listadoTareas;
    }

    public ArrayList<Proyecto> getListadoProyectos() {
        return listadoProyectos;
    }

    public void guardarProyecto(Proyecto unProyecto) {
        listadoProyectos.add(unProyecto);
    }

    public boolean buscarProyecto(String titulo) {
        for(Proyecto p:listadoProyectos){
            if (p.getTitulo().equals(titulo)){
                return true;
            }
        }
        return false;
    }

    public void borrarProyecto(Proyecto unProyecto) {
        if (buscarProyecto(unProyecto.getTitulo())){
            listadoProyectos.remove(unProyecto);
        }
    }
    
    public void guardarTarea(Tarea unaTarea){
        listadoTareas.add(unaTarea);
    }
    
    public boolean buscarTarea(String titulo){
        for(Tarea t : listadoTareas){
            if (t.getTitulo().equals(titulo)){
                return true;
            }
        }
        return false;
    }
    
    public void borrarTarea(Tarea unaTarea){
        if (buscarTarea(unaTarea.getTitulo())){
            listadoTareas.remove(unaTarea);
        }
    }
}
