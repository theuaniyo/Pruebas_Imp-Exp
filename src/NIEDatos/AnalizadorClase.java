/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NIEDatos;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 *
 * @author Juanjo Luque
 */
public class AnalizadorClase {
    
    public static ArrayList<String> leerClase(Class unaClase){
        
        ArrayList<String> etiquetasClase = new ArrayList<>();
        Field[] listaAtributos = unaClase.getDeclaredFields();
        
        for (Field f : listaAtributos) {
            etiquetasClase.add(f.getName());
        }
        return etiquetasClase;
    }
    
    public static Class clasePadre(Class unaClase) {
        Class clasePadre;
        
        clasePadre = unaClase.getSuperclass();
        
        return clasePadre;
    }
    
    public static ArrayList<String> clasesDelPaquete(Package unPaquete) {
        ArrayList<String> arrayClases = new ArrayList<>();
        Class[] clasesDelPaquete;
        
        clasesDelPaquete = unPaquete.getClass().getDeclaredClasses();
        
        for (Class c : clasesDelPaquete) {
            arrayClases.add(c.getName());
        }
        
        return arrayClases;
    }
}
