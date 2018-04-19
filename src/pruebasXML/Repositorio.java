/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebasXML;

import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.w3c.dom.Text;

/**
 *
 * @author Juanjo Luque
 */
public class Repositorio {

    ArrayList<Tarea> listadoTareas;
    ArrayList<Proyecto> listadoProyectos;
    Tarea tarea;
    Proyecto proyecto;

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

    public void cargarDesdeXml(String ruta) {

        Document doc = DOMUtil.XML2DOM(ruta);
        String titulo = "";
        String descripcion = "";
        String tituloProyecto = "";
        String descripcionProyecto = "";

        NodeList children = doc.getDocumentElement().getChildNodes();

        for (int i = 0; i < children.getLength(); i++) {
            Node c = children.item(i);

            switch (c.getNodeType()) {
                case Node.ELEMENT_NODE:
                    Element e = (Element) c;
                    if (e.getTagName().equals("tareaSimple")) {
                        NodeList t = e.getElementsByTagName("titulo");
                        NodeList d = e.getElementsByTagName("descripcion");
                        titulo = t.item(0).getTextContent();
                        descripcion = d.item(0).getTextContent();
                        tarea = new Tarea(titulo, descripcion);
                        listadoTareas.add(tarea);
                    } else if (e.getTagName().equals("proyecto")) {
                        NodeList dp = e.getElementsByTagName("descripcionProyecto");
                        NodeList t = e.getElementsByTagName("titulo");
                        NodeList d = e.getElementsByTagName("descripcion");

                        tituloProyecto = e.getAttribute("titulo");
                        descripcionProyecto = dp.item(0).getTextContent();
                        proyecto = new Proyecto(tituloProyecto, descripcionProyecto);

                        for (int j = 0; j < t.getLength(); j++) {
                            titulo = t.item(j).getTextContent();
                            descripcion = d.item(j).getTextContent();
                            tarea = new Tarea(titulo, descripcion);
                            listadoTareas.add(tarea);
                            proyecto.addTarea(tarea);
                        }

                        listadoProyectos.add(proyecto);
                    }

                    break;

            }

        }

    }

    public void generarXml(String ruta) {

        //Crear DOM vacío
        Document xml = DOMUtil.crearDOMVacio("gtd");

        //Añadimos las etiquetas
        for (Tarea t : listadoTareas) {

            //Primero para las tareas que no pertenecen a un proyecto
            if (t.getProyecto() == null) {
                Element dirTarea = xml.createElement("tareaSimple");
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
}
