/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NIEDatos;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import persistencia.Repositorio;

/**
 *
 * @author Juanjo Luque
 */
public class AnalizadorPrueba {
    
    
/*        
    public static ArrayList<String> leerClase(Class unaClase){
        
        ArrayList<String> etiquetasClase = new ArrayList<>();
        Field[] listaAtributos = unaClase.getDeclaredFields();
        
        for (Field f : listaAtributos) {
            etiquetasClase.add(f.getName());
        }
        return etiquetasClase;
    }
    
    public static String clasePadre(Class unaClase) {
        String clasePadre;
        
        clasePadre = unaClase.getSuperclass().toString().replaceFirst(".*\\.", "");
        
        return clasePadre;
    }
    
    public static String[] nombreClases(String ruta) throws IOException {
        String[] nombreClases;
        
        File f = new File(ruta);
        
        nombreClases = f.list();
        
        for (int i = 0; i < nombreClases.length; i++){
            nombreClases[i] = nombreClases[i].replace(".java", "");
        }
        
        return nombreClases;
    }
    
    public static ArrayList<String> etiquetasRepositorio(Class repositorio) {
        
        ArrayList<String> etiquetasRepositorio = new ArrayList<>();
        
        Field[] atributosRepositorio = repositorio.getDeclaredFields();
        
        for (Field f : atributosRepositorio) {
            if (f.getType()==ArrayList.class) {
                etiquetasRepositorio.add(f.getName());
            }
        }
        
        return etiquetasRepositorio;
    }
    
    public static void tareasEnColeccion(String nombreColeccion) throws NoSuchFieldException {
        
        ArrayList<String> atributosRepositorio = etiquetasRepositorio(Repositorio.class);
        
        for (String str : atributosRepositorio){
            java.lang.annotation.Annotation[] anotaciones 
                    = Repositorio.class.getField(str).getAnnotations();
            
            
        }    
    }
*/      
}
