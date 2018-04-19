/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FiltradoSimple;

import administradorDeTareas.TareaSimple;
import java.util.ArrayList;

/**
 *
 * @author xisko
 */
public interface InterfazTareaSimple {
    
    /**
     *
     * @param miLista
     * @return
     */
    abstract ArrayList<? extends TareaSimple> eligeCriterios(ArrayList<? extends TareaSimple> miLista);
    
}
