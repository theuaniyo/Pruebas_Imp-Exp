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
public class TareaAgenda extends TareaSimple{
    
    private Date fechaFin;
    
    private Date recordatorio;

    /**
     *
     * @param fechaFin
     * @param contexto
     * @param anotacion
     * @param requisitos
     * @param miComplejidad
     * @param Nombre
     * @param FechaRegistro
     */
    public TareaAgenda(Date fechaFin, Date recordatorio, String contexto, String anotacion, String requisitos, Complejidad miComplejidad, String Nombre, Date FechaRegistro) {    
        super(contexto, anotacion, requisitos, miComplejidad, Nombre, FechaRegistro);
        this.fechaFin = fechaFin;
        this.recordatorio = recordatorio;
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

    public Date getRecordatorio() {
        return recordatorio;
    }

    public void setRecordatorio(Date recordatorio) {
        this.recordatorio = recordatorio;
    }

    
   
   
    
}
