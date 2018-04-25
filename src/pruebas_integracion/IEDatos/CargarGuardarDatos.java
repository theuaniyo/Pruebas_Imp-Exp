/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas_integracion.IEDatos;

import java.util.Scanner;
import pruebasXML.Repositorio;

/**
 *
 * @author Juan José Luque Morales
 */
public class CargarGuardarDatos {

    public static void main(String[] args) {

        Repositorio miRepo = Repositorio.getInstance();
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
