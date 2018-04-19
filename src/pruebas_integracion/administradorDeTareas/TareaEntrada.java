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
    private Date fechaRegistro;

    /**
     *
     * @param Nombre
     * @param FechaRegistro
     */
    public TareaEntrada(String Nombre, Date FechaRegistro) {
        this.nombre = Nombre;
        this.fechaRegistro = FechaRegistro;
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
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     *
     * @param FechaRegistro
     */
    public void setFechaRegistro(Date FechaRegistro) {
        this.fechaRegistro = FechaRegistro;
    }
    
}
