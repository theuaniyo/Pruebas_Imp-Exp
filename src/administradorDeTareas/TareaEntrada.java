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
public class TareaEntrada {
    
    private String nombre;
    private static int cantidad = 0;
    private int id;

    /**
     *
     * @param nombre
     * @param id
     */
    public TareaEntrada(String nombre) {
        this.nombre = nombre;
        cantidad++;
        
        this.id=cantidad;
    }

    /**
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @param Nombre
     */
    public void setNombre(String Nombre) {
        this.nombre = Nombre;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }


   
}
