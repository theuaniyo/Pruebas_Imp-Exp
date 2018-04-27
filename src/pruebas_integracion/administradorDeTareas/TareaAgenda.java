/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas_integracion.administradorDeTareas;


import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author xisko
 */
public class TareaAgenda extends TareaSimple{
    
    private Timestamp fechaFin;
    
    private Timestamp fechaInicio;

    /**
     *
     * @param fechaFin
     * @param fechaInicio
     * @param contexto
     * @param miComplejidad
     * @param anotacion
     * @param nombre
     * @param id
     */
    public TareaAgenda(Timestamp fechaFin, Timestamp fechaInicio, String contexto, Complejidad miComplejidad, String anotacion, String nombre, int id) {
        super(contexto, miComplejidad, anotacion, nombre, id);
        this.fechaFin = fechaFin;
        this.fechaInicio = fechaInicio;
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
     *
     * @return
     */
    public Timestamp getFechaInicio() {
        return fechaInicio;
    }

    /**
     *
     * @param fechaInicio
     */
    public void setFechaInicio(Timestamp fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    
    
    
 

    

   
    
   
   
    
}
