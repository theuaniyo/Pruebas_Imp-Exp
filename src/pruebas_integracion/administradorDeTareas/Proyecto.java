/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administradorDeTareas;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author xisko
 */
public class Proyecto {
    
    private String nombreP;
	private ArrayList<TareaProyecto> listaTareasProyecto;
	private Date fechaFin;

    /**
     *
     * @param NombreP
     * @param listaTareasProyecto
     * @param fechaFin
     */
    public Proyecto(String NombreP, ArrayList<TareaProyecto> listaTareasProyecto, Date fechaFin) {
        this.nombreP = NombreP;
        this.listaTareasProyecto = listaTareasProyecto;
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
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     *
     * @param fechaFin
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
    
}
