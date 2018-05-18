/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administradorDeTareas;

import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistencia.Repositorio;

/**
 *
 * @author xisko
 */
public class TareaEntrada {

    private String nombre;

    /**
     *
     * @param nombre
     *
     */
    public TareaEntrada(String nombre) {

        this.nombre = nombre;

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

}
