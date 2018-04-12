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
public class Proyecto {

    private String titulo;
    private String descripcion;
    private ArrayList<Tarea> listaTareas;

    public Proyecto(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.listaTareas = new ArrayList<>();
    }

    public void addTarea(Tarea unaTarea) {
        listaTareas.add(unaTarea);
        unaTarea.setProyecto(this);
    }

    public void borrarTarea(int indice) {
        listaTareas.remove(indice);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ArrayList<Tarea> getListaTareas() {
        return listaTareas;
    }
    
}
