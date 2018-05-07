/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administradorDeTareas;

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
        private int id;

    /**
     *
     * @param nombreP
     * 
     * @param fechaFin
     */
    public Proyecto(String nombreP, Timestamp fechaFin) {
        this.nombreP = nombreP;
        listaTareasProyecto= new ArrayList<>();
        this.fechaFin = fechaFin;
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
    
    public void insertarTareaProyecto(TareaProyecto miTarea){
        
        listaTareasProyecto.add(miTarea);
    }
    
    public void quitarTareaProyecto(TareaProyecto miTarea){
        
        listaTareasProyecto.remove(miTarea);
    }
}
