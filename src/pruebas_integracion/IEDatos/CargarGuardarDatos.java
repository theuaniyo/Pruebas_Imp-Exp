/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas_integracion.IEDatos;

import java.util.Scanner;
import pruebas_integracion.administradorDeTareas.TareaEntrada;
import pruebas_integracion.administradorDeTareas.TareaSimple;

/**
 *
 * @author Juan José Luque Morales
 */
public class CargarGuardarDatos {

    public static void main(String[] args) {

        RepoProvisional miRepo = RepoProvisional.getInstance();
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
                    //IEDatos.cargarDesdeXml(IEDatos.getRuta());
                    System.out.println("Total objetos TareaEntrada: " + miRepo.getBandejaEntrada().size());
                    for (TareaEntrada unaTareaEntrada : miRepo.getBandejaEntrada()) {
                        System.out.println(unaTareaEntrada.getNombre());
                    }
                    System.out.println("Total objetos TareaSimple: " + miRepo.getListaTareasSimples().size());
                    for (TareaSimple unaTareaSimple : miRepo.getListaTareasSimples()) {
                        System.out.println(unaTareaSimple.getNombre());
                        System.out.println(unaTareaSimple.getContexto());
                        System.out.println(unaTareaSimple.getMiComplejidad().toString());
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
