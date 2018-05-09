/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administradorDeTareas;

/**
 *
 * @author cobos
 */
public class TareaInmediata extends TareaEntrada {

    private boolean terminada;

    /**
     *
     * @param terminada
     * @param contexto
     * @param miComplejidad
     * @param anotacion
     * @param nombre
     * @param id
     */
    public TareaInmediata(boolean terminada, String nombre) {
        super(nombre);
        this.terminada = terminada;
    }

    /**
     *
     * @return
     */
    public boolean isTerminada() {
        return terminada;
    }

    /**
     *
     * @param terminada
     */
    public void setTerminada(boolean terminada) {
        this.terminada = terminada;
    }

}
