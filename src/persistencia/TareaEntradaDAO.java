/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import administradorDeTareas.TareaEntrada;

/**
 *
 * @author alumno
 */
public interface TareaEntradaDAO extends DAO{
         
    TareaEntrada get(int pera);
}
