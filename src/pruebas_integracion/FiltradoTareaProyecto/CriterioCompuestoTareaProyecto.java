/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas_integracion.FiltradoTareaProyecto;

import pruebas_integracion.FiltradoSimple.InterfazTareaSimple;
import administradorDeTareas.TareaProyecto;
import java.util.ArrayList;

/**
 *
 * @author xisko
 */
public class CriterioCompuestoTareaProyecto implements InterfazTareaProyecto{

    private ArrayList<InterfazTareaProyecto> criterioProyecto;

    private ArrayList<InterfazTareaSimple> criterioSimple;

    /**
     *
     * @param criterioProyecto
     * @param criterioSimple
     */
    public CriterioCompuestoTareaProyecto(ArrayList<InterfazTareaProyecto> criterioProyecto, ArrayList<InterfazTareaSimple> criterioSimple) {
        this.criterioProyecto = criterioProyecto;
        this.criterioSimple = criterioSimple;
    }

    /**
     *
     * @param miLista
     * @return
     */
    public ArrayList<TareaProyecto> eligeCriterios(ArrayList<TareaProyecto> miLista) {
        boolean algunoaplicado = false;
        ArrayList<TareaProyecto> filtroCompuesto = new ArrayList<>();

        for (InterfazTareaSimple micriterio : criterioSimple) {
            if (!algunoaplicado) {
                //aplicarlo con miLista a la derecha
                filtroCompuesto = (ArrayList<TareaProyecto>) micriterio.eligeCriterios(miLista);
                algunoaplicado = true;
            } else {
                //aplicarlo con filtrocompuesto a la derecha
                filtroCompuesto = (ArrayList<TareaProyecto>) micriterio.eligeCriterios(filtroCompuesto);

            }
        }
        return filtroCompuesto;
    }

}
