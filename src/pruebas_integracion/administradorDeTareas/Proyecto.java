/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas_integracion.administradorDeTareas;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author xisko
 */
public class Proyecto {

    private String nombreP;
    private ArrayList<TareaProyecto> listaTareasProyecto;
    private Timestamp fechaFin;
    //Añadido atributo id -Juan J. Luque
    private int id;

    /**
     *
     * @param nombreP
     * @param fechaFin
     * @param id
     */
    public Proyecto(String nombreP, Timestamp fechaFin, int id) {
        this.nombreP = nombreP;
        listaTareasProyecto = new ArrayList<>();
        this.fechaFin = fechaFin;
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getNombreP() {
        return nombreP;
    }

    /**
     *
     * @param nombreP
     */
    public void setNombreP(String nombreP) {
        this.nombreP = nombreP;
    }

    /**
     *
     * @return
     */
    public ArrayList<TareaProyecto> getListaTareasProyecto() {
        return listaTareasProyecto;
    }

    /**
     *
     * @param listaTareasProyecto
     */
    public void setListaTareasProyecto(ArrayList<TareaProyecto> listaTareasProyecto) {
        this.listaTareasProyecto = listaTareasProyecto;
    }

    /**
     *
     * @return
     */
    public Timestamp getFechaFin() {
        return fechaFin;
    }

    /**
     *
     * @param fechaFin
     */
    public void setFechaFin(Timestamp fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @author Juan J. Luque
     * @return un entero con el id del proyecto
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id entero que representa el id del proyecto
     */
    public void setId(int id) {
        this.id = id;
    }

    public void insertarTareaProyecto(TareaProyecto miTarea) {

        listaTareasProyecto.add(miTarea);
    }

    public void quitarTareaProyecto(TareaProyecto miTarea) {

        listaTareasProyecto.remove(miTarea);
    }

}
