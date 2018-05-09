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
public class TareaSimple extends TareaEntrada {

    private String contexto;
    private Complejidad miComplejidad;
    private String anotacion;

    /**
     *
     * @param contexto
     * @param descripcion
     * @param miComplejidad
     * @param nombre
     * @param id
     */
    public TareaSimple(String contexto, Complejidad miComplejidad, String anotacion, String nombre) {
        super(nombre);
        this.contexto = contexto;
        this.miComplejidad = miComplejidad;
        this.anotacion = anotacion;
    }

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
     * @param descripcion
     */
    public void setAnotacion(String descripcion) {
        this.anotacion = descripcion;
    }

}
