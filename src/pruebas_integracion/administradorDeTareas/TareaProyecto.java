/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas_integracion.administradorDeTareas;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author xisko
 */
public class TareaProyecto extends TareaSimple{
    
    
    private Proyecto unProyecto;
    private Prioridad miPrioridad;
    
 
    /**
     *
     * 
     * @param unProyecto
     * @param miPrioridad
     * @param contexto
     * @param anotacion
     * @param miComplejidad
     * @param nombre
     * @param id
     */
    

    public TareaProyecto(Proyecto unProyecto, Prioridad miPrioridad, String contexto, Complejidad miComplejidad, String anotacion, String nombre, int id) {
        super(contexto, miComplejidad, anotacion, nombre, id);
        this.unProyecto = unProyecto;
        this.miPrioridad = miPrioridad;
        
    }

    /**
     *
     * @return
     */
    public Proyecto getUnProyecto() {
        return unProyecto;
    }

    /**
     *
     * @param unProyecto
     */
    public void setUnProyecto(Proyecto unProyecto) {
        this.unProyecto = unProyecto;
    }

    /**
     *
     * @return
     */
    public Prioridad getMiPrioridad() {
        return miPrioridad;
    }

    /**
     *
     * @param miPrioridad
     */
    public void setMiPrioridad(Prioridad miPrioridad) {
        this.miPrioridad = miPrioridad;
    }

  

    
    
}
