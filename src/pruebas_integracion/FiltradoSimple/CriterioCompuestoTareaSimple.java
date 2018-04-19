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
public class CriterioCompuestoTareaSimple implements InterfazTareaSimple{

    private InterfazTareaSimple Criterio1;

    private InterfazTareaSimple Criterio2;

    /**
     *
     * @param Criterio1
     * @param Criterio2
     */
    public CriterioCompuestoTareaSimple(InterfazTareaSimple Criterio1, InterfazTareaSimple Criterio2) {
        this.Criterio1 = Criterio1;
        this.Criterio2 = Criterio2;
    }

    /**
     *
     * @param miLista
     * @return
     */
    public ArrayList<? extends TareaSimple> eligeCriterios(ArrayList<? extends TareaSimple> miLista) {

        ArrayList<TareaSimple> miComposicion = (ArrayList<TareaSimple>) Criterio1.eligeCriterios(miLista);

        return Criterio2.eligeCriterios(miComposicion);

    }

}
