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
    
    private TareaProyecto dependencia;
    private Proyecto unProyecto;
    private Prioridad miPrioridad;

    /**
     *
     * @param dependencia
     * @param unProyecto
     * @param miPrioridad
     * @param contexto
     * @param anotacion
     * @param requisitos
     * @param miComplejidad
     * @param Nombre
     * @param FechaRegistro
     */
    public TareaProyecto(TareaProyecto dependencia, Proyecto unProyecto, Prioridad miPrioridad, String contexto, String anotacion, String requisitos, Complejidad miComplejidad, String Nombre, Date FechaRegistro) {
        super(contexto, anotacion, requisitos, miComplejidad, Nombre, FechaRegistro);
        this.dependencia = dependencia;
        this.unProyecto = unProyecto;
        this.miPrioridad = miPrioridad;
    }

    /**
     *
     * @return
     */
    public TareaProyecto getDependencia() {
        return dependencia;
    }

    /**
     *
     * @param dependencia
     */
    public void setDependencia(TareaProyecto dependencia) {
        this.dependencia = dependencia;
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
