/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FiltradoTareaProyecto;

import administradorDeTareas.Prioridad;
import administradorDeTareas.TareaProyecto;

import java.util.ArrayList;

/**
 *
 * @author xisko
 */
public class CriterioPrioridad implements InterfazTareaProyecto{
    
    private final Prioridad miPrioridad;

    public CriterioPrioridad(Prioridad miPrioridad) {
        this.miPrioridad = miPrioridad;
    }
    
    
    
    public ArrayList<TareaProyecto> eligeCriterios(ArrayList<TareaProyecto> miLista) {
       
       ArrayList<TareaProyecto> filtradoFinal = new ArrayList<>();
        
        for (TareaProyecto e : miLista) {
            if (e.getMiPrioridad().compareTo(miPrioridad)==0) {
                filtradoFinal.add(e);
            }
        }
      
        return filtradoFinal;
    }
    
}
