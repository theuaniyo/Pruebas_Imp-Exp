/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebasXML;

import java.util.Scanner;

/**
 *
 * @author Juanjo Luque
 */
public class UI {

    private static Repositorio miRepositorio;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        miRepositorio = Repositorio.getInstance();
        /*
        Tarea tarea1 = new Tarea("Tarea 1", "Esta es la primera tarea.");
        miRepositorio.guardarTarea(tarea1);
        Tarea tarea2 = new Tarea("Tarea 2", "Esta es la segunda tarea.");
        miRepositorio.guardarTarea(tarea2);
        Tarea tarea3 = new Tarea("Tarea 3", "Esta es la tercera tarea.");
        miRepositorio.guardarTarea(tarea3);
        Tarea tarea4 = new Tarea("Tarea 3", "Esta es la cuarta tarea.");
        miRepositorio.guardarTarea(tarea4);
        Tarea tarea5 = new Tarea("Tarea 5", "Esta es la quinta tarea.");
        miRepositorio.guardarTarea(tarea5);
        Tarea tarea6 = new Tarea("Tarea 6", "Esta es la sexta tarea.");
        miRepositorio.guardarTarea(tarea6);
        Tarea tarea7 = new Tarea("Tarea 7", "Esta es la septima tarea.");
        miRepositorio.guardarTarea(tarea7);
        Tarea tarea8 = new Tarea("Tarea 8", "Esta es la octava tarea.");
        miRepositorio.guardarTarea(tarea8);
        Tarea tarea9 = new Tarea("Tarea 9", "Esta es la novena tarea.");
        miRepositorio.guardarTarea(tarea9);
        Tarea tarea10 = new Tarea("Tarea 10", "Esta es la décima tarea.");
        miRepositorio.guardarTarea(tarea9);
        
        Proyecto proyecto1 = new Proyecto("Proyecto 1", "Este ese el primer proyecto.");
        proyecto1.addTarea(tarea2);
        proyecto1.addTarea(tarea3);
        miRepositorio.guardarProyecto(proyecto1);
        
        Proyecto proyecto2 = new Proyecto("Proyecto 2", "Este es el segundo proyecto.");
        proyecto2.addTarea(tarea4);
        proyecto2.addTarea(tarea5);
        proyecto2.addTarea(tarea6);
        miRepositorio.guardarProyecto(proyecto2);
        
        Proyecto proyecto3 = new Proyecto("Proyecto 3", "Este es el tercer proyecto.");
        proyecto3.addTarea(tarea9);
        proyecto3.addTarea(tarea10);
        miRepositorio.guardarProyecto(proyecto3);
        */
        menuPrincipal();
        elegirOpcion(leerTeclado());

    }

    private static void menuPrincipal() {
        System.out.println("1. Listar tareas y proyectos.");
        System.out.println("2. Generar XML.");
        System.out.println("3. Cargar datos desde XML.");
        System.out.println("0. Salir.");
        System.out.println("Elija una opción:");
    }

    private static String leerTeclado() {
        String entrada = "";
        Scanner sc = new Scanner(System.in);
        entrada = sc.nextLine();
        return entrada;
    }

    private static void elegirOpcion(String entrada) {

        char opcion = entrada.charAt(0);
        String ruta = "tareas.xml";

        switch (opcion) {

            //Listar tareas
            case '1':
                for (Tarea t : miRepositorio.getListadoTareas()) {
                    if (t.getProyecto() == null) {
                        System.out.println("Tarea: " + t.getTitulo() + " " + t.getDescripcion());
                    }
                }

                for (Proyecto p : miRepositorio.getListadoProyectos()) {
                    System.out.println("Proyecto: " + p.getTitulo() + " " + p.getDescripcion());
                    for (Tarea t : p.getListaTareas()) {
                        System.out.println("Tarea: " + t.getTitulo() + " " + t.getDescripcion());
                    }
                }
                break;
                
            //Generar XML
                
            case '2':
                break;
                
            //Cargar datos desde XML
            case '3':
                miRepositorio.xml2Object(ruta);
                        menuPrincipal();
                        elegirOpcion(leerTeclado());
                break;

            //Salir
            case '0':
                System.exit(0);
                break;
                
            //Opcion equivocada
            default:
                System.out.println("Opcion incorrecta.");
                menuPrincipal();
                elegirOpcion(leerTeclado());
                break;
        }
    }
}
