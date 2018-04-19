/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas_integracion.FiltradoSimple;

import administradorDeTareas.Complejidad;
import administradorDeTareas.TareaSimple;
import java.util.ArrayList;

/**
 *
 * @author xisko
 */
public class CriterioComplejidad implements InterfazTareaSimple{

    private final Complejidad miComplejidad;

    public CriterioComplejidad(Complejidad miComplejidad) {
        this.miComplejidad = miComplejidad;
    }

    @Override
    public ArrayList<? extends TareaSimple> eligeCriterios(ArrayList<? extends TareaSimple> miLista) {
        ArrayList<TareaSimple> filtradoFinal = new ArrayList<>();
        
        for (TareaSimple e : miLista) {
            if (e.getMiComplejidad().compareTo(miComplejidad)==0) {
                filtradoFinal.add(e);
            }
        }
        
        return filtradoFinal;
    }

}
