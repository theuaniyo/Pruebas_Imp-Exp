/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FiltradoTareaProyecto;

import administradorDeTareas.TareaProyecto;
import java.util.ArrayList;

/**
 *
 * @author xisko
 */
public class CriterioPerteneceProyecto implements InterfazTareaProyecto{
    
    private String pattern = "[a-ZA-Z0-9]";
    
    /**
     *
     * @param miLista
     * @return
     */
    public ArrayList<TareaProyecto> eligeCriterios(ArrayList<TareaProyecto> miLista) {
       
        ArrayList<TareaProyecto> filtradoNombreProyecto = new ArrayList<>();
        
        for(TareaProyecto e: miLista){
            
            if(e.getUnProyecto().getNombreP().matches(pattern)){
                filtradoNombreProyecto.add(e);
            }
        }
        return filtradoNombreProyecto;
    }
    
}
