/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FiltradoAgenda;

import administradorDeTareas.TareaAgenda;
import java.util.ArrayList;

/**
 *
 * @author xisko
 */
public interface InterfazTareaAgenda {
    
    /**
     *
     * @param miLista
     * @return
     */
    abstract ArrayList<TareaAgenda> eligeCriteriosAgenda(ArrayList<TareaAgenda> miLista);
    
}
