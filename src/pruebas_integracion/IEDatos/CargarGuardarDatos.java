/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas_integracion.IEDatos;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Scanner;
import pruebas_integracion.administradorDeTareas.TareaAgenda;
import pruebas_integracion.administradorDeTareas.TareaEntrada;
import pruebas_integracion.administradorDeTareas.TareaSimple;
import pruebas_integracion.persistencia.Repositorio;

/**
 *
 * @author Juan José Luque Morales
 */
public class CargarGuardarDatos {

    public static void main(String[] args) {

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
                    System.out.println("Contextos guardados: " + Repositorio.getInstancia().getContextos().size());
                    for (String c : Repositorio.getInstancia().getContextos()){
                        System.out.println(c);
                    }
                    System.out.println("Total objetos en Agenda: " + Repositorio.getInstancia().getAgenda().size());
                    for (TareaAgenda unaTareaAgenda : Repositorio.getInstancia().getAgenda()){
                        System.out.println(unaTareaAgenda.getId());
                        System.out.println(unaTareaAgenda.getNombre());
                        System.out.println(unaTareaAgenda.getContexto());
                        System.out.println(unaTareaAgenda.getDescripcion());
                        System.out.println(unaTareaAgenda.getMiComplejidad().toString());
                        System.out.println(unaTareaAgenda.getFechaInicio());
                        System.out.println(unaTareaAgenda.getFechaFin());
                    }
                    System.out.println("Total objetos en Bandeja de Entrada: " + Repositorio.getInstancia().getBandejaEntrada().size());
                    for (TareaEntrada unaTareaEntrada : Repositorio.getInstancia().getBandejaEntrada()) {
                        System.out.println(unaTareaEntrada.getId());
                        System.out.println(unaTareaEntrada.getNombre());
                    }
                    System.out.println("Total objetos en Archivo Seguimiento: " + Repositorio.getInstancia().getArchivoSeguimiento().size());
                    for (TareaEntrada t : Repositorio.getInstancia().getArchivoSeguimiento()) {
                        if (t.getClass().equals(TareaSimple.class)) {
                            TareaSimple unaTareaSimple = (TareaSimple) t;
                            System.out.println(unaTareaSimple.getId());
                            System.out.println(unaTareaSimple.getNombre());
                            System.out.println(unaTareaSimple.getContexto());
                            System.out.println(unaTareaSimple.getMiComplejidad().toString());
                            System.out.println(unaTareaSimple.getDescripcion());
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
