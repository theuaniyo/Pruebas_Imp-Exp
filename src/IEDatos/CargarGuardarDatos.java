/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IEDatos;

import java.util.Scanner;
import administradorDeTareas.Proyecto;
import administradorDeTareas.TareaAgenda;
import administradorDeTareas.TareaEntrada;
import administradorDeTareas.TareaInmediata;
import administradorDeTareas.TareaProyecto;
import administradorDeTareas.TareaSimple;
import java.sql.SQLException;
import persistencia.Repositorio;

/**
 *
 * @author Juan José Luque Morales
 */
public class CargarGuardarDatos {

    public static void main(String[] args) throws SQLException {

        String entrada = "";

        do {
            System.out.println("Elige opción:");
            System.out.println("1. Prueba de carga.");
            System.out.println("2. Prueba de guardado.");
            System.out.println("0. Salir.");

            Scanner sc = new Scanner(System.in);
            entrada = sc.nextLine();

            switch (entrada) {

                case "1":
                    IEDatos.cargarDesdeXml();
                    //Contextos
                    System.out.println("Contextos guardados: " + Repositorio.getInstancia().getContextos().size());
                    for (String c : Repositorio.getInstancia().getContextos()) {
                        System.out.println(c);
                    }
                    //Agenda
                    System.out.println("Total objetos en Agenda: " + Repositorio.getInstancia().getAgenda().size());
                    for (TareaAgenda t : Repositorio.getInstancia().getAgenda()) {
                        System.out.println(t.getId());
                        System.out.println(t.getNombre());
                        System.out.println(t.getContexto());
                        System.out.println(t.getAnotacion());
                        System.out.println(t.getMiComplejidad().toString());
                        System.out.println(t.getFechaInicio());
                        System.out.println(t.getFechaFin());
                    }
                    //Bandeja de entrada
                    System.out.println("Total objetos en Bandeja de Entrada: " + Repositorio.getInstancia().getBandejaEntrada().size());
                    for (TareaEntrada t : Repositorio.getInstancia().getBandejaEntrada()) {
                        System.out.println(t.getId());
                        System.out.println(t.getNombre());
                    }
                    //Tareas Inmediatas
                    System.out.println("Total objetos en Tareas Inmediatas: " + Repositorio.getInstancia().getTareasInmediatas().size());
                    for (TareaInmediata t : Repositorio.getInstancia().getTareasInmediatas()) {
                        System.out.println(t.getId());
                        System.out.println(t.getNombre());
                        System.out.println(t.isTerminada());
                    }
                    //Proyectos
                    System.out.println("Total proyectos: " + Repositorio.getInstancia().getProyectos().size());
                    for (Proyecto p : Repositorio.getInstancia().getProyectos()) {
                        //System.out.println(p.getId());
                        System.out.println(p.getNombreP());
                        System.out.println(p.getFechaFin().toString());
                        for (TareaProyecto tp : p.getListaTareasProyecto()) {
                            System.out.println(tp.getId());
                            System.out.println(tp.getNombre());
                            System.out.println(tp.getAnotacion());
                            System.out.println(tp.getContexto());
                            System.out.println(tp.getMiComplejidad());
                            System.out.println(tp.getMiPrioridad());
                            //System.out.println("ID proyecto: " + tp.getUnProyecto().getId());
                        }
                    }
                    //Papelera
                    System.out.println("Total tareas finalizadas: " + Repositorio.getInstancia().getTareasFinalizada().size());
                    for (TareaEntrada t : Repositorio.getInstancia().getTareasFinalizada()) {

                        if (t.getClass().equals(TareaEntrada.class)) {
                            System.out.println(t.getId());
                            System.out.println(t.getNombre());
                        } else if (t.getClass().equals(TareaAgenda.class)) {
                            TareaAgenda ta = (TareaAgenda) t;
                            System.out.println(ta.getId());
                            System.out.println(ta.getNombre());
                            System.out.println(ta.getContexto());
                            System.out.println(ta.getAnotacion());
                            System.out.println(ta.getMiComplejidad().toString());
                            System.out.println(ta.getFechaInicio());
                            System.out.println(ta.getFechaFin());
                        } else if (t.getClass().equals(TareaSimple.class)) {
                            TareaSimple ts = (TareaSimple) t;
                            System.out.println(ts.getId());
                            System.out.println(ts.getNombre());
                            System.out.println(ts.getAnotacion());
                            System.out.println(ts.getContexto());
                            System.out.println(ts.getMiComplejidad());
                        } else if (t.getClass().equals(TareaInmediata.class)) {
                            TareaInmediata ti = (TareaInmediata) t;
                            System.out.println(ti.getId());
                            System.out.println(ti.getNombre());
                            System.out.println(ti.isTerminada());
                        } else if (t.getClass().equals(TareaProyecto.class)) {
                            TareaProyecto tp = (TareaProyecto) t;
                            System.out.println(tp.getId());
                            System.out.println(tp.getNombre());
                            System.out.println(tp.getAnotacion());
                            System.out.println(tp.getContexto());
                            System.out.println(tp.getMiComplejidad());
                            System.out.println(tp.getMiPrioridad());
                            if (tp.getUnProyecto() != null) {
                                //System.out.println("ID proyecto: " + tp.getUnProyecto().getId());
                            } else {
                                System.out.println("Sin proyecto.");
                            }
                        }
                    }
                    //Archivo Seguimiento
                    System.out.println("Total objetos en Archivo Seguimiento: " + Repositorio.getInstancia().getArchivoSeguimiento().size());
                    for (TareaEntrada t : Repositorio.getInstancia().getArchivoSeguimiento()) {
                        System.out.println(t.getId());
                        System.out.println(t.getNombre());
                    }
                    //Archivo consulta
                    System.out.println("Total tareas en Archivo Consulta: " + Repositorio.getInstancia().getArchivoConsulta().size());
                    for (TareaEntrada t : Repositorio.getInstancia().getArchivoConsulta()) {
                        System.out.println(t.getId());
                        System.out.println(t.getNombre());
                    }
                    //Acciones Siguientes
                    System.out.println("Total tareas en Acciones Siguientes: " + Repositorio.getInstancia().getAccionesSiguientes().size());
                    for (TareaSimple t : Repositorio.getInstancia().getAccionesSiguientes()) {
                        if (t.getClass().equals(TareaProyecto.class)) {
                            TareaSimple ti = (TareaProyecto) t;
                            System.out.println(ti.getId());
                            System.out.println(ti.getNombre());
                            System.out.println(ti.getAnotacion());
                            System.out.println(ti.getContexto());
                            System.out.println(ti.getMiComplejidad());

                        } else if (t.getClass().equals(TareaSimple.class)){
                            System.out.println(t.getId());
                            System.out.println(t.getNombre());
                            System.out.println(t.getAnotacion());
                            System.out.println(t.getContexto());
                            System.out.println(t.getMiComplejidad());
                        }
                    }
                    break;

                case "2":
                    IEDatos.guardarXml();
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
