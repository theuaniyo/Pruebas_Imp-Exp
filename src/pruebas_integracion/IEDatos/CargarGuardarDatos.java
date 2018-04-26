/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas_integracion.IEDatos;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import pruebasXML.Repositorio;
import pruebas_integracion.administradorDeTareas.Complejidad;
import pruebas_integracion.administradorDeTareas.Prioridad;
import pruebas_integracion.administradorDeTareas.Proyecto;
import pruebas_integracion.administradorDeTareas.TareaAgenda;
import pruebas_integracion.administradorDeTareas.TareaEntrada;
import pruebas_integracion.administradorDeTareas.TareaProyecto;
import pruebas_integracion.administradorDeTareas.TareaSimple;

/**
 *
 * @author Juan José Luque Morales
 */
public class CargarGuardarDatos {

    public static void main(String[] args) {

        Repositorio miRepo = Repositorio.getInstance();
        String entrada = "";
        java.util.Date fechaActual = new java.util.Date();
        for (int i = 0; i < 3; i++) {
            TareaEntrada te = new TareaEntrada("Tarea", fechaActual);
            RepoProvisional.getInstance().getBandejaEntrada().add(te);
            TareaAgenda ta = new TareaAgenda(fechaActual, fechaActual, "contexto", "anotacion", "requisitos", Complejidad.Alta, "nombre", fechaActual);
            RepoProvisional.getInstance().getAgenda().add(ta);
            TareaSimple ts = new TareaSimple("contexto", "anotacion", "requisitos", Complejidad.Media, "nombre", fechaActual);
            RepoProvisional.getInstance().getListaTareasSimples().add(ts);
            
 
            ArrayList<TareaProyecto> lis=new ArrayList<>(); 
            Proyecto p = new Proyecto("NombreProyecto", lis, fechaActual);
            RepoProvisional.getInstance().getMisProyectos().add(p);
            for(int j=0; j<2;j++){
            TareaProyecto tp = new TareaProyecto(null, p, Prioridad.Media, "contexto", "anotacion", "requisitos", Complejidad.Media, "nombre", fechaActual);
            p.getListaTareasProyecto().add(tp);
            }
        }

        do {
            System.out.println("Elige opción:");
            System.out.println("1. Prueba de carga.");
            System.out.println("2. Prueba de guardado.");
            System.out.println("0. Salir.");

            Scanner sc = new Scanner(System.in);
            entrada = sc.nextLine();

            switch (entrada) {
                case "1":
                    IEDatos.cargarDesdeXml(IEDatos.getRuta());
                    break;

                case "2":

                    IEDatos.guardarXml("pruebaGuardado.xml");
                    break;

                case "0":
                    System.exit(0);
                    break;

                default:
                    System.out.println("Opción incorrecta.");
                    break;
            }

        } while (true);
    }

}
