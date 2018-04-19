/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FiltradoAgenda;

import administradorDeTareas.TareaAgenda;
import java.util.ArrayList;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


/**
 *
 * @author kimbo
 */
public class CriterioFechaFin {

    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    private Date date = new Date();

    /**
     * Devuelve una lista ordenada de fechas, si no hay fechas, devuelve una
     * lista nulla
     *
     * @param miLista
     * @return Date myList
     */
    public List<Date> eligeCriteriosAgenda(ArrayList<TareaAgenda> miLista) {
        System.out.println("HORA SISTEMA--> " + date);
        List<Date> myList = new ArrayList<>();
        if (!miLista.isEmpty()) {
            for (TareaAgenda e : miLista) {
                myList.add(e.getFechaFin());
            }
            Collections.sort(myList, new Comparator<Date>() {
                @Override
                public int compare(Date o1, Date o2) {
                    return o1.compareTo(o2);
                }
            });

            return myList;
        } else {
            myList = null;
            return myList;
        }
    }

}
