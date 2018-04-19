/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas_integracion.FiltradoTareaProyecto;

import administradorDeTareas.TareaProyecto;
import java.util.ArrayList;

/**
 *
 * @author xisko
 */
public interface InterfazTareaProyecto {
    
    /**
     *
     * @param miLista
     * @return
     */
    abstract ArrayList<TareaProyecto> eligeCriterios(ArrayList<TareaProyecto> miLista);
    
}
