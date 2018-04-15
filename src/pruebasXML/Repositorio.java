/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebasXML;

import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 *
 * @author Juanjo Luque
 */
public class Repositorio {

    ArrayList<Tarea> listadoTareas;
    ArrayList<Proyecto> listadoProyectos;

    private Repositorio() {
        listadoTareas = new ArrayList<>();
        listadoProyectos = new ArrayList<>();
    }

    public static Repositorio getInstance() {
        return RepositorioHolder.INSTANCE;
    }

    private static class RepositorioHolder {

        private static final Repositorio INSTANCE = new Repositorio();
    }

    public ArrayList<Tarea> getListadoTareas() {
        return listadoTareas;
    }

    public ArrayList<Proyecto> getListadoProyectos() {
        return listadoProyectos;
    }

    public void guardarProyecto(Proyecto unProyecto) {
        listadoProyectos.add(unProyecto);
    }

    public Proyecto buscarProyecto(String titulo) {
        Proyecto proyectoBuscado = null;
        for (Proyecto p : listadoProyectos) {
            if (p.getTitulo().equals(titulo)) {
                proyectoBuscado = p;
            }
        }
        return proyectoBuscado;
    }

    public void borrarProyecto(Proyecto unProyecto) {
        if (buscarProyecto(unProyecto.getTitulo()) != null) {
            listadoProyectos.remove(unProyecto);
        }
    }

    public void guardarTarea(Tarea unaTarea) {
        listadoTareas.add(unaTarea);
    }

    public Tarea buscarTarea(String titulo) {
        Tarea tareaBuscada = null;
        for (Tarea t : listadoTareas) {
            if (t.getTitulo().equals(titulo)) {
                tareaBuscada = t;
            }
        }
        return tareaBuscada;
    }

    public void borrarTarea(Tarea unaTarea) {
        if (buscarTarea(unaTarea.getTitulo()) != null) {
            listadoTareas.remove(unaTarea);
        }
    }

    public void generarXml(String ruta) {

        //Crear DOM vacío
        Document xml = DOMUtil.crearDOMVacio("gtd");

        //Añadimos las etiquetas
        for (Tarea t : listadoTareas) {

            //Primero para las tareas que no pertenecen a un proyecto
            if (t.getProyecto() == null) {
                Element dirTarea = xml.createElement("tarea");
                xml.getDocumentElement().appendChild(dirTarea);

                Element dirTitulo = xml.createElement("titulo");
                dirTitulo.setTextContent(t.getTitulo());
                dirTarea.appendChild(dirTitulo);

                Element dirDescripcion = xml.createElement("descripcion");
                dirDescripcion.setTextContent(t.getDescripcion());
                dirTarea.appendChild(dirDescripcion);
            }
        }

        //Después para las tareas asociadas a un proyecto
        for (Proyecto p : listadoProyectos) {

            Element dirProyecto = xml.createElement("proyecto");
            dirProyecto.setAttribute("titulo", p.getTitulo());
            xml.getDocumentElement().appendChild(dirProyecto);

            Element dirDescripcionProyecto = xml.createElement("descripcionProyecto");
            dirDescripcionProyecto.setTextContent(p.getDescripcion());
            dirProyecto.appendChild(dirDescripcionProyecto);

            Element dirTareas = xml.createElement("tareas");
            dirProyecto.appendChild(dirTareas);

            for (Tarea t : p.getListaTareas()) {
                Element dirTarea = xml.createElement("tarea");
                dirTareas.appendChild(dirTarea);

                Element dirTitulo = xml.createElement("titulo");
                dirTitulo.setTextContent(t.getTitulo());
                dirTarea.appendChild(dirTitulo);

                Element dirDescripcion = xml.createElement("descripcion");
                dirDescripcion.setTextContent(t.getDescripcion());
                dirTarea.appendChild(dirDescripcion);
            }
        }

        System.out.println(DOMUtil.DOM2XML(xml, ruta));
    }

    public void cargarDesdeXml(String ruta) {
        
    }
}
