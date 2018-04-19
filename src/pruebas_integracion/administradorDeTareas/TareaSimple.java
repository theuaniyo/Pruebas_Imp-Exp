/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administradorDeTareas;

import java.util.Date;

/**
 *
 * @author xisko
 */
public class TareaSimple extends TareaEntrada{
    
    private String contexto;
    private String anotacion;

    /**
     *
     */
   
    private String requisitos;
    private Complejidad miComplejidad;

    /**
     *
     * @return
     */
    public Complejidad getMiComplejidad() {
        return miComplejidad;
    }

    /**
     *
     * @param miComplejidad
     */
    public void setMiComplejidad(Complejidad miComplejidad) {
        this.miComplejidad = miComplejidad;
    }

    /**
     *
     * @param contexto
     * @param anotacion
     * @param requisitos
     * @param miComplejidad
     * @param Nombre
     * @param FechaRegistro
     */
    public TareaSimple(String contexto, String anotacion, String requisitos, Complejidad miComplejidad, String Nombre, Date FechaRegistro) {
        super(Nombre, FechaRegistro);
        this.contexto = contexto;
        this.anotacion = anotacion;
        this.requisitos = requisitos;
        this.miComplejidad = miComplejidad;
    }

    /**
     *
     * @return
     */
    public String getContexto() {
        return contexto;
    }

    /**
     *
     * @param contexto
     */
    public void setContexto(String contexto) {
        this.contexto = contexto;
    }

    /**
     *
     * @return
     */
    public String getAnotacion() {
        return anotacion;
    }

    /**
     *
     * @param anotacion
     */
    public void setAnotacion(String anotacion) {
        this.anotacion = anotacion;
    }

    /**
     *
     * @return
     */
    public String getRequisitos() {
        return requisitos;
    }

    /**
     *
     * @param requisitos
     */
    public void setRequisitos(String requisitos) {
        this.requisitos = requisitos;
    }

   

    

   
    
    
}
