/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas_integracion.FiltradoSimple;

import administradorDeTareas.TareaSimple;
import java.util.ArrayList;

/**
 *
 * @author alumno
 */
public class CriterioContexto implements InterfazTareaSimple {

    private String contexto;

    //String pattern = "[a-zA-Z]+";
    public CriterioContexto(String contexto) {
        this.contexto = contexto;
    }

    /**
     *
     * @param miLista
     * @return
     */
    @Override

    public ArrayList<? extends TareaSimple> eligeCriterios(ArrayList<? extends TareaSimple> miLista) {
        ArrayList<TareaSimple> filtradoContexto = new ArrayList<>();

        for (TareaSimple e : miLista) {
            if (e.getContexto().equals(contexto)) {
                filtradoContexto.add(e);
            }
        }
        System.out.println("se ha filtrado");
        return filtradoContexto;
    }
}