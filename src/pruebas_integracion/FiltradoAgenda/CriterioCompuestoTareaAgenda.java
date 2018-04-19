/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas_integracion.FiltradoAgenda;

import pruebas_integracion.FiltradoSimple.InterfazTareaSimple;
import administradorDeTareas.TareaAgenda;
import java.util.ArrayList;

/**
 *
 * @author xisko
 */
public class CriterioCompuestoTareaAgenda implements InterfazTareaAgenda{
    
    private ArrayList<InterfazTareaAgenda> criterioAgenda;
   
   private ArrayList<InterfazTareaSimple> criterioSimple;

    /**
     *
     * @param criterioAgenda
     * @param criterioSimple
     */
    public CriterioCompuestoTareaAgenda(ArrayList<InterfazTareaAgenda> criterioAgenda, ArrayList<InterfazTareaSimple> criterioSimple) {
        this.criterioAgenda = criterioAgenda;
        this.criterioSimple = criterioSimple;
    }
  

 //

    /**
     *
     * @param miLista
     * @return
     */
    public ArrayList<TareaAgenda> eligeCriteriosAgenda(ArrayList<TareaAgenda> miLista) {
        boolean algunoaplicado = false;
        ArrayList<TareaAgenda> filtroCompuesto= new ArrayList<>();
        
        
        for (InterfazTareaSimple micriterio : criterioSimple) {
            if (!algunoaplicado) {
                //aplicarlo con miLista a la derecha
               filtroCompuesto= (ArrayList<TareaAgenda>) micriterio.eligeCriterios(miLista);
                algunoaplicado = true;
            }
            else {
                //aplicarlo con filtrocompuesto a la derecha
             filtroCompuesto= (ArrayList<TareaAgenda>) micriterio.eligeCriterios(filtroCompuesto);

            }
        }
        return filtroCompuesto;
    } 
    
}
