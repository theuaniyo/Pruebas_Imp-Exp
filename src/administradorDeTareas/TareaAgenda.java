/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administradorDeTareas;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author xisko
 */
public class TareaAgenda extends TareaSimple {

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
     */
    public TareaAgenda(Timestamp fechaFin, Timestamp fechaInicio, String contexto, Complejidad miComplejidad, String anotacion, String nombre) {
       super(contexto, miComplejidad, anotacion, nombre);
        if (compruebaFechas(fechaInicio, fechaFin)) {
            this.fechaFin = fechaFin;
            this.fechaInicio = fechaInicio;
        } else {
            throw new IllegalArgumentException("Las fechas no son válidas");
        }
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
    
    private boolean compruebaFechas(Timestamp fechaInicio, Timestamp fechaFin) {
        boolean correcto = false;
        if (fechaFin.after(fechaInicio)) {
            correcto = true;
        } else if (fechaFin.equals(fechaInicio)) {
            correcto = true;
        }
        return correcto;
    }

}
