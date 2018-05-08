/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IEDatos;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import administradorDeTareas.Complejidad;
import administradorDeTareas.Prioridad;
import administradorDeTareas.Proyecto;
import administradorDeTareas.TareaAgenda;
import administradorDeTareas.TareaEntrada;
import administradorDeTareas.TareaInmediata;
import administradorDeTareas.TareaProyecto;
import administradorDeTareas.TareaSimple;
import java.lang.reflect.Field;
import persistencia.Repositorio;

/**
 * Clase que se usa para guardar y cargar datos desde archivos xml.
 *
 * @author Juan J. Luque Morales
 */
public class IEDatos {

    /**
     * Instancia de la ruta del archivo xml (PROVISIONAL).
     */
    private static String ruta = "gestor_gtd.xml";
    /**
     * Constante con la raíz del archivo XML.
     */
    private static final String RAIZ = "gestor_gtd";

    /**
     *
     * @return la ruta del archivo XML.
     */
    public static String getRuta() {
        return ruta;
    }

    /**
     * Cambia la ruta donde se guardará/cargará el archivo XML.
     *
     * @param ruta cadena que contiene la ruta del archivo.
     */
    public static void setRuta(String ruta) {
        IEDatos.ruta = ruta;
    }

    /**
     * Guarda los datos del programa en un archivo XML.
     *
     */
    public static void guardarXml() {

        //Crear DOM vacío
        Document xml = DOMUtil.crearDOMVacio(RAIZ);
        //xml.createAttribute("usuario");//.setValue(miUsuario.getNick);
        //PONER A GTD SE AÑANDA UN ATRIBUTO USUARIO
        if (!Repositorio.getInstancia().getContextos().isEmpty()) {
            Element eleContextos = xml.createElement("contextos");
            xml.getDocumentElement().appendChild(eleContextos);
            for (String cont : Repositorio.getInstancia().getContextos()) {
                Element eleContexto = xml.createElement("contexto");
                eleContexto.setTextContent(cont);
                eleContextos.appendChild(eleContexto);
            }
        }
        if (!Repositorio.getInstancia().getAgenda().isEmpty()) {
            Element eleListaTareasAgenda = xml.createElement("agenda");
            xml.getDocumentElement().appendChild(eleListaTareasAgenda);
            for (TareaAgenda ta : Repositorio.getInstancia().getAgenda()) {
                escribirTareaAgendaXml(xml, eleListaTareasAgenda, ta);
            }
        }
        if (!Repositorio.getInstancia().getBandejaEntrada().isEmpty()) {
            Element eleBandejaEntrada = xml.createElement("bandeja_entrada");
            xml.getDocumentElement().appendChild(eleBandejaEntrada);
            for (TareaEntrada te : Repositorio.getInstancia().getBandejaEntrada()) {

                escribirTareaEntradaXml(xml, eleBandejaEntrada, te);
            }
        }
        if (!Repositorio.getInstancia().getTareasInmediatas().isEmpty()) {

            Element eleTareasInmediatas = xml.createElement("tareas_inmediatas");
            xml.getDocumentElement().appendChild(eleTareasInmediatas);

            for (TareaInmediata ti : Repositorio.getInstancia().getTareasInmediatas()) {
                escribirTareaInmediataXml(xml, eleTareasInmediatas, ti);
            }
        }
        if (!Repositorio.getInstancia().getProyectos().isEmpty()) {
            Element eleProyectos = xml.createElement("proyectos");
            xml.getDocumentElement().appendChild(eleProyectos);
            for (Proyecto p : Repositorio.getInstancia().getProyectos()) {
                escribirProyectoySusTareasProyectoXml(xml, eleProyectos, p);
            }
        }
        if (!Repositorio.getInstancia().getPapelera().isEmpty()) {
            Element elePapelera = xml.createElement("papelera");
            xml.getDocumentElement().appendChild(elePapelera);

            for (TareaEntrada tep : Repositorio.getInstancia().getPapelera()) {
                if (tep.getClass().equals(TareaEntrada.class)) {
                    escribirTareaEntradaXml(xml, elePapelera, tep);                  
                }
            }
        }
        if (!Repositorio.getInstancia().getArchivoSeguimiento().isEmpty()) {

            Element eleArchivoSeguimiento = xml.createElement("archivo_seguimiento");
            xml.getDocumentElement().appendChild(eleArchivoSeguimiento);

            for (TareaEntrada tas : Repositorio.getInstancia().getArchivoSeguimiento()) {
                if (tas.getClass().equals(TareaEntrada.class)) {
                    escribirTareaEntradaXml(xml, eleArchivoSeguimiento, tas);
                }
            }
        }
        if (!Repositorio.getInstancia().getArchivoConsulta().isEmpty()) {
            Element eleArchivoConsulta = xml.createElement("archivo_consulta");
            xml.getDocumentElement().appendChild(eleArchivoConsulta);
            for (TareaEntrada tas : Repositorio.getInstancia().getArchivoSeguimiento()) {
                escribirTareaEntradaXml(xml, eleArchivoConsulta, tas);
            }
        }
        if (!Repositorio.getInstancia().getAccionesSiguientes().isEmpty()) {

            Element eleAccionesSiguientes = xml.createElement("acciones_siguientes");
            xml.getDocumentElement().appendChild(eleAccionesSiguientes);
            for (TareaSimple ts : Repositorio.getInstancia().getAccionesSiguientes()) {
                if (ts.getClass().equals(TareaSimple.class)) {
                    escribirTareaSimpleXml(xml, eleAccionesSiguientes, ts);
                } else if (ts.getClass().equals(TareaInmediata.class)) {
                    TareaInmediata ti = (TareaInmediata) ts;
                    escribirTareaInmediataXml(xml, eleAccionesSiguientes, ti);
                }
            }
        }
//######¡OJO! El segundo parámetro habría que cambiarlo al atributo ruta.#######
        DOMUtil.DOM2XML(xml, "prueba.xml");
    }

    private static void escribirTareaInmediataXml(Document xml, Element eleAccionesSiguientes, TareaInmediata ti) throws DOMException {
        Element eleTareaInmediataAcSi = xml.createElement("tarea_inmediata");
        eleAccionesSiguientes.appendChild(eleTareaInmediataAcSi);
        //eleTareaInmediataAcSi.setAttribute("id", (Integer.toString(ti.getId())));
        
        Element eleTareaInmediataTerminadaAcSi = xml.createElement("terminada");
        eleTareaInmediataAcSi.appendChild(eleTareaInmediataTerminadaAcSi);
        eleTareaInmediataTerminadaAcSi.setTextContent(Boolean.toString(ti.isTerminada()));
        
        Element eleTareaInmediataContextoAcSim = xml.createElement("contexto");
        eleTareaInmediataAcSi.appendChild(eleTareaInmediataContextoAcSim);
        eleTareaInmediataContextoAcSim.setTextContent(ti.getContexto());
        
        Element eleTareaInmediataComplejidadAcSim = xml.createElement("complejidad");
        eleTareaInmediataAcSi.appendChild(eleTareaInmediataComplejidadAcSim);
        eleTareaInmediataComplejidadAcSim.setTextContent(ti.getMiComplejidad().toString());
        
        Element eleTareaInmediataAnotacionPape = xml.createElement("anotacion");
        eleTareaInmediataAcSi.appendChild(eleTareaInmediataAnotacionPape);
        eleTareaInmediataAnotacionPape.setTextContent(ti.getAnotacion());
        
        Element eleTareaInmediataNombrePape = xml.createElement("nombre");
        eleTareaInmediataAcSi.appendChild(eleTareaInmediataNombrePape);
        eleTareaInmediataNombrePape.setTextContent(ti.getNombre());
    }

    private static void escribirTareaSimpleXml(Document xml, Element eleAccionesSiguientes, TareaSimple ts) throws DOMException {
        Element eleTareaSimpleAcSim = xml.createElement("tarea_simple");
        eleAccionesSiguientes.appendChild(eleTareaSimpleAcSim);
        
        //eleTareaSimpleAcSim.setAttribute("id", (Integer.toString(ts.getId())));
        
        Element eleTareaSimpleContextoAcSim = xml.createElement("contexto");
        eleTareaSimpleAcSim.appendChild(eleTareaSimpleContextoAcSim);
        eleTareaSimpleContextoAcSim.setTextContent(ts.getContexto());
        
        Element eleTareaSimpleComplejidadAcSim = xml.createElement("complejidad");
        eleTareaSimpleAcSim.appendChild(eleTareaSimpleComplejidadAcSim);
        eleTareaSimpleComplejidadAcSim.setTextContent(ts.getMiComplejidad().toString());
        
        Element eleTareaSimpleAnotacionAcSim = xml.createElement("anotacion");
        eleTareaSimpleAcSim.appendChild(eleTareaSimpleAnotacionAcSim);
        eleTareaSimpleAnotacionAcSim.setTextContent(ts.getAnotacion());
        
        Element eleTareaSimpleNombreAcSim = xml.createElement("nombre");
        eleTareaSimpleAcSim.appendChild(eleTareaSimpleNombreAcSim);
        eleTareaSimpleNombreAcSim.setTextContent(ts.getNombre());
    }

    private static void escribirProyectoySusTareasProyectoXml(Document xml, Element eleProyectos, Proyecto p) throws DOMException {
        Element eleProyecto = xml.createElement("proyecto");
        eleProyectos.appendChild(eleProyecto);
        //eleProyecto.setAttribute("id", (Integer.toString(p.getId())));
        
        Element eleNombreProyecto = xml.createElement("nombre");
        eleNombreProyecto.setTextContent(p.getNombreP());
        eleProyecto.appendChild(eleNombreProyecto);
        
        if (!p.getListaTareasProyecto().isEmpty()) {
            Element eleListaTareasProyectos = xml.createElement("lista_tareas_proyecto");
            eleProyecto.appendChild(eleListaTareasProyectos);
            for (TareaProyecto tp : p.getListaTareasProyecto()) {
                escribirTareaProyectoXml(xml, eleListaTareasProyectos, tp);               
            }
        }
        
        Element eleFechaFinProyecto = xml.createElement("fecha_fin");
        eleFechaFinProyecto.setAttribute("fecha", p.getFechaFin().toString());
        eleProyecto.appendChild(eleFechaFinProyecto);
    }

    private static void escribirTareaProyectoXml(Document xml, Element eleListaTareasProyectos, TareaProyecto tp) throws DOMException {
        Element eleTareaProyecto = xml.createElement("tarea_proyecto");
        eleListaTareasProyectos.appendChild(eleTareaProyecto);
        //eleTareaProyecto.setAttribute("id", (Integer.toString(tp.getId())));
        //eleTareaProyecto.setAttribute("proyecto", Integer.toString(tp.getUnProyecto().getId()));
        
        Element eleTareaProyectoPrioridad = xml.createElement("prioridad");
        eleTareaProyecto.appendChild(eleTareaProyectoPrioridad);
        eleTareaProyectoPrioridad.setTextContent(tp.getMiPrioridad().toString());
        
        Element eleTareaProyectoContexto = xml.createElement("contexto");
        eleTareaProyecto.appendChild(eleTareaProyectoContexto);
        eleTareaProyectoContexto.setTextContent(tp.getContexto());
        
        Element eleTareaProyectoComplejidad = xml.createElement("complejidad");
        eleTareaProyecto.appendChild(eleTareaProyectoComplejidad);
        eleTareaProyectoComplejidad.setTextContent(tp.getMiComplejidad().toString());
        
        Element eleTareaProyectoAnotacion = xml.createElement("anotacion");
        eleTareaProyecto.appendChild(eleTareaProyectoAnotacion);
        eleTareaProyectoAnotacion.setTextContent(tp.getAnotacion());
        
        Element eleTareaProyectoNombre = xml.createElement("nombre");
        eleTareaProyecto.appendChild(eleTareaProyectoNombre);
        eleTareaProyectoNombre.setTextContent(tp.getNombre());
    }

    private static void escribirTareaEntradaXml(Document xml, Element eleBandejaEntrada, TareaEntrada te) throws DOMException {
        Element eleTareaEntrada = xml.createElement("tarea_entrada");
        eleBandejaEntrada.appendChild(eleTareaEntrada);
        
        //eleTareaEntrada.setAttribute("id", (Integer.toString(te.getId())));
        
        Element eleNombreTareaEntrada = xml.createElement("nombre");
        eleNombreTareaEntrada.setTextContent(te.getNombre());
        eleTareaEntrada.appendChild(eleNombreTareaEntrada);
    }

    private static void escribirTareaAgendaXml(Document xml, Element eleListaTareasAgenda, TareaAgenda ta) throws DOMException {
        Element eleTareaAgenda = xml.createElement("tarea_agenda");
        eleListaTareasAgenda.appendChild(eleTareaAgenda);
        
        
        //eleTareaAgenda.setAttribute("id", (Integer.toString(ta.getId())));
        
        Element eleFechaFin = xml.createElement("fecha_fin");
        eleFechaFin.setTextContent(ta.getFechaFin().toString());
        eleTareaAgenda.appendChild(eleFechaFin);
        
        Element eleFechaInicio = xml.createElement("fecha_inicio");
        eleFechaInicio.setTextContent(ta.getFechaInicio().toString());
        eleTareaAgenda.appendChild(eleFechaInicio);
        
        Element eleContextoTareaAgenda = xml.createElement("contexto");
        eleContextoTareaAgenda.setTextContent(ta.getContexto());
        eleTareaAgenda.appendChild(eleContextoTareaAgenda);
        
        Element eleComplejidadTareaAgenda = xml.createElement("complejidad");
        eleComplejidadTareaAgenda.setTextContent(ta.getMiComplejidad().toString());
        eleTareaAgenda.appendChild(eleComplejidadTareaAgenda);
        
        Element eleAnotacionTareaAgenda = xml.createElement("anotacion");
        eleAnotacionTareaAgenda.setTextContent(ta.getAnotacion());
        eleTareaAgenda.appendChild(eleAnotacionTareaAgenda);
        
        Element eleNombreTareaAgenda = xml.createElement("nombre");
        eleNombreTareaAgenda.setTextContent(ta.getNombre());
        eleTareaAgenda.appendChild(eleNombreTareaAgenda);
    }

    /**
     * @author Juan J. Luque Morales Carga los datos del programa desde un
     * archivo XML. Para cambiar la ruta desde la que se cargará el archivo, hay
     * que usar el método setRuta de esta clase.
     *
     */
    public static void cargarDesdeXml() {

        //Paso de XML a árbol DOM
        Document doc = DOMUtil.XML2DOM(ruta);
        //Guardamos las etiquetas hijas de la raíz en una lista de nodos
        NodeList nodosRaiz = doc.getDocumentElement().getChildNodes();

        for (int i = 0; i < nodosRaiz.getLength(); i++) {

            //Si es un elemento, lo guardamos para filtralo
            if (nodosRaiz.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) nodosRaiz.item(i);

                switch (e.getTagName()) {

                    case "contextos":
                        //Lista de nodos de elementos Contexto
                        NodeList contextos = e.getChildNodes();
                        procesarContextos(contextos);
                        break;
                    case "agenda":
                        //Lista de nodos de elementos TareaAgenda
                        NodeList agenda = e.getChildNodes();

                        for (int j = 0; j < agenda.getLength(); j++) {

                            if (agenda.item(j).getNodeType() == Node.ELEMENT_NODE) {

                                //Una etiqueta TareaAgenda
                                Element tareaAgenda = (Element) agenda.item(j);
                                TareaAgenda unaTareaAgenda = procesarTareaAgenda(tareaAgenda);
                                Repositorio.getInstancia().agregarEnAgenda(unaTareaAgenda);
                            }
                        }
                        break;

                    case "bandeja_entrada":

                        //Lista de nodos con todas las TareasEntrada
                        NodeList bandejaEntrada = e.getChildNodes();

                        for (int j = 0; j < bandejaEntrada.getLength(); j++) {

                            if (bandejaEntrada.item(j).getNodeType() == Node.ELEMENT_NODE) {

                                //Una etiqueta TareaEntrada
                                Element tareaEntrada
                                        = (Element) bandejaEntrada.item(j);
                                TareaEntrada unaTareaEntrada
                                        = procesarTareaEntrada(tareaEntrada);
                                Repositorio.getInstancia().agregarEnBandeja(
                                        unaTareaEntrada);
                            }
                        }
                        break;

                    case "tareas_inmediatas":

                        //Lista de nodos con todas las TareasInmediatas
                        NodeList tareasInmediatas = e.getChildNodes();

                        for (int j = 0; j < tareasInmediatas.getLength(); j++) {

                            if (tareasInmediatas.item(j).getNodeType() == Node.ELEMENT_NODE) {

                                //Una etiqueta TareaInmediata
                                Element tareaInmediata
                                        = (Element) tareasInmediatas.item(j);
                                TareaInmediata unaTareaInmediata
                                        = procesarTareaInmediata(tareaInmediata);
                                Repositorio.getInstancia().agregarEnInmediatas(
                                        unaTareaInmediata);
                            }
                        }
                        break;

                    case "proyectos":

                        //Lista de nodos de elementos Proyecto
                        NodeList proyectos = e.getChildNodes();

                        for (int j = 0; j < proyectos.getLength(); j++) {

                            if (proyectos.item(j).getNodeType() == Node.ELEMENT_NODE) {

                                Proyecto unProyecto;
                                //Una etiqueta proyecto.
                                Element proyecto = (Element) proyectos.item(j);
                                unProyecto = procesarProyecto(proyecto);
                                Repositorio.getInstancia().agregarEnProyectos(unProyecto);
                            }
                        }
                        break;

                    //EN PRINCIPIO EN LA PAPELERA SÓLO HAY OBJETOS TAREAENTRADA
                    case "papelera":

                        //Lista de nodos dentro de la etiqueta papelera
                        NodeList papelera = e.getChildNodes();

                        for (int j = 0; j < papelera.getLength(); j++) {

                            if (papelera.item(j).getNodeType() == Node.ELEMENT_NODE) {

                                Element tareaEnPapelera = (Element) papelera.item(j);

                                TareaEntrada unaTareaEntrada
                                        = procesarTareaEntrada(tareaEnPapelera);
                                Repositorio.getInstancia().
                                        agregarEnPapelera(unaTareaEntrada);

                                /*
                                switch (tareaEnPapelera.getNodeName()) {

                                    case "tarea_entrada":

                                        TareaEntrada unaTareaEntrada
                                                = procesarTareaEntrada(tareaEnPapelera);
                                        Repositorio.getInstancia().
                                                agregarEnPapelera(unaTareaEntrada);
                                        break;

                                    case "tarea_simple":

                                        TareaSimple unaTareaSimple
                                                = procesarTareaSimple(tareaEnPapelera);
                                        Repositorio.getInstancia().
                                                agregarEnPapelera(unaTareaSimple);
                                        break;

                                    case "tarea_inmediata":

                                        TareaInmediata unaTareaInmediata
                                                = procesarTareaInmediata(tareaEnPapelera);
                                        Repositorio.getInstancia().
                                                agregarEnPapelera(unaTareaInmediata);
                                        break;

                                    case "tarea_agenda":

                                        TareaAgenda unaTareaAgenda
                                                = procesarTareaAgenda(tareaEnPapelera);
                                        Repositorio.getInstancia().
                                                agregarEnPapelera(unaTareaAgenda);
                                        break;

                                    case "proyecto":
                                        
                                        Proyecto unProyecto 
                                                = procesarProyecto(tareaEnPapelera);
                                        Repositorio.getInstancia().agregarEnPapelera(unProyecto);
                                        break;
                                    case "tarea_proyecto":

                                        TareaProyecto unaTareaProyecto
                                                = procesarTareaProyecto(tareaEnPapelera);

                                        tareaEnPapelera.getAttributes();
                                        int id = Integer.parseInt(
                                                tareaEnPapelera.getAttribute("proyecto"));
                                        Proyecto unpProyecto = Repositorio.
                                                getInstancia().buscarProyecto(id);

                                        unaTareaProyecto.setUnProyecto(unpProyecto);

                                        Repositorio.getInstancia().
                                                agregarEnPapelera(unaTareaProyecto);
                                        break;
                                }*/
                            }
                        }
                        break;
                    case "archivo_seguimiento":

                        //Todos los nodos de archivoSeguimiento
                        NodeList archivoSeguimiento = e.getChildNodes();

                        for (int j = 0; j < archivoSeguimiento.getLength(); j++) {

                            if (archivoSeguimiento.item(j).getNodeType() == Node.ELEMENT_NODE) {

                                TareaEntrada unaTareaEntrada;

                                //Una etiqueta TareaEntrada
                                Element tareaEntrada
                                        = (Element) archivoSeguimiento.item(j);
                                unaTareaEntrada
                                        = procesarTareaEntrada(tareaEntrada);
                                Repositorio.getInstancia().agregarEnSeguimiento(
                                        unaTareaEntrada);
                            }
                        }
                        break;

                    case "archivo_consulta":

                        //Nodos dentro de archivoConsulta
                        NodeList archivoConsulta = e.getChildNodes();

                        for (int j = 0; j < archivoConsulta.getLength(); j++) {

                            if (archivoConsulta.item(j).getNodeType() == Node.ELEMENT_NODE) {

                                //Una etiqueta TareaEntrada
                                Element tareaEntrada
                                        = (Element) archivoConsulta.item(j);
                                TareaEntrada unaTareaEntrada
                                        = procesarTareaEntrada(tareaEntrada);
                                Repositorio.getInstancia().agregarEnConsulta(
                                        unaTareaEntrada);
                            }
                        }
                        break;

                    //¡Ojo! Aquí se guardan objetos TareaSimple y TareaInmediata.
                    case "acciones_siguientes":

                        //Nodos dentro de accionesSiguientes.
                        NodeList accionesSiguientes = e.getChildNodes();

                        for (int j = 0; j < accionesSiguientes.getLength(); j++) {

                            if (accionesSiguientes.item(j).getNodeType() == Node.ELEMENT_NODE) {

                                Element tareaSiguiente = (Element) accionesSiguientes.item(j);

                                switch (tareaSiguiente.getTagName()) {

                                    case "tarea_simple":

                                        TareaSimple unaTareaSimple
                                                = procesarTareaSimple(tareaSiguiente);
                                        Repositorio.getInstancia().
                                                agregarEnSiguientes(unaTareaSimple);
                                        break;

                                    case "tarea_inmediata":

                                        TareaSimple unaTareaInmediata
                                                = procesarTareaInmediata(tareaSiguiente);
                                        Repositorio.getInstancia().
                                                agregarEnSiguientes(unaTareaInmediata);
                                        break;
                                }
                            }
                        }
                        break;
                }
            }

        }
    }

    /**
     * @author Juan J. Luque Morales Convierte una etiqueta del archivo XML a un
     * objeto TareaInmediata.
     *
     * @param e un elemento del XML que representa a la TareaInmediata.
     * @return un objeto TareaInmediata.
     * @throws DOMException si hay un error relacionado con el XML.
     * @throws NumberFormatException si el id de la tarea no es un valor
     * numérico.
     */
    private static TareaInmediata procesarTareaInmediata(Element e)
            throws DOMException, NumberFormatException {

        TareaInmediata unaTareaInmediata = new TareaInmediata(
                false, "", Complejidad.Media, "", "", 0);

        //Atributo id
        //unaTareaInmediata.setId(Integer.parseInt(e.getAttribute("id")));

        //Nodos dentro de TareaInmediata
        NodeList nodosTareaInmediata = e.getChildNodes();

        for (int i = 0; i < nodosTareaInmediata.getLength(); i++) {

            if (nodosTareaInmediata.item(i).getNodeType() == Node.ELEMENT_NODE) {

                //Una etiqueta de TareaInmediata
                Element etiquetaTareaInmediata = (Element) nodosTareaInmediata.item(i);

                switch (etiquetaTareaInmediata.getTagName()) {

                    case "terminada":

                        unaTareaInmediata.setTerminada(Boolean.valueOf(
                                etiquetaTareaInmediata.getTextContent().trim()));
                        break;

                    case "contexto":

                        unaTareaInmediata.setContexto(
                                etiquetaTareaInmediata.getTextContent().trim());
                        break;

                    case "complejidad":

                        unaTareaInmediata.setMiComplejidad(Complejidad.valueOf(
                                etiquetaTareaInmediata.getTextContent().trim()));
                        break;

                    case "anotacion":

                        unaTareaInmediata.setAnotacion(
                                etiquetaTareaInmediata.getTextContent().trim());
                        break;

                    case "nombre":

                        unaTareaInmediata.setNombre(
                                etiquetaTareaInmediata.getTextContent().trim());
                        break;
                }
            }
        }

        return unaTareaInmediata;
    }

    /**
     * @author Juan J. Luque Morales Convierte una etiqueta del archivo XML a un
     * objeto Proyecto.
     *
     * @param e un elemento del XML que representa un Proyecto.
     * @return un objeto Proyecto.
     * @throws DOMException si hay un error relacionado con el XML.
     * @throws NumberFormatException si el id del proyecto no es un valor
     * numérico.
     */
    private static Proyecto procesarProyecto(Element e)
            throws DOMException, NumberFormatException {

        Proyecto unProyecto = new Proyecto("", null);

        //Atributo id
        //unProyecto.setId(Integer.parseInt(e.getAttribute("id")));
        //Lista de nodos del elemento Proyecto
        NodeList nodosProyecto = e.getChildNodes();

        for (int i = 0; i < nodosProyecto.getLength(); i++) {

            if (nodosProyecto.item(i).getNodeType() == Node.ELEMENT_NODE) {

                Element etiquetaProyecto = (Element) nodosProyecto.item(i);

                switch (etiquetaProyecto.getTagName()) {

                    case "nombre":
                        unProyecto.setNombreP(
                                etiquetaProyecto.getTextContent().trim());
                        break;

                    case "fecha_fin":
                        unProyecto.setFechaFin(Timestamp.valueOf(
                                etiquetaProyecto.getTextContent().trim()));
                        break;

                    case "lista_tareas_proyecto":
                        TareaProyecto unaTareaProyecto;
                        //Nodos de la lista de tareas del proyecto.
                        NodeList tareas = etiquetaProyecto.getChildNodes();

                        for (int j = 0; j < tareas.getLength(); j++) {

                            if (tareas.item(j).getNodeType() == Node.ELEMENT_NODE) {

                                Element tareaProyecto = (Element) tareas.item(j);
                                unaTareaProyecto = procesarTareaProyecto(tareaProyecto);
                                unaTareaProyecto.setUnProyecto(unProyecto);
                                unProyecto.insertarTareaProyecto(unaTareaProyecto);
                            }
                        }
                        break;
                }
            }
        }
        return unProyecto;
    }

    /**
     * @author Juan J. Luque Morales Convierte una etiqueta del archivo XML a un
     * objeto TareaProyecto.
     *
     * @param e un elemento del XML que representa una TareaProyecto.
     * @return un objeto TareaProyecto.
     * @throws DOMException si hay un error relacionado con el XML.
     * @throws NumberFormatException si el id no es un valor numérico.
     */
    private static TareaProyecto procesarTareaProyecto(Element e)
            throws DOMException, NumberFormatException {

        TareaProyecto unaTareaProyecto = new TareaProyecto(
                null, Prioridad.Media, "", Complejidad.Media, "", "", 0);

        //Atributo id
        //unaTareaProyecto.setId(Integer.parseInt(e.getAttribute("id")));

        //Lista de nodos de la TareaProyecto
        NodeList nodosTareaProyecto = e.getChildNodes();

        for (int i = 0; i < nodosTareaProyecto.getLength(); i++) {

            if (nodosTareaProyecto.item(i).getNodeType() == Node.ELEMENT_NODE) {

                Element etiquetaTareaProyecto = (Element) nodosTareaProyecto.item(i);

                switch (etiquetaTareaProyecto.getTagName()) {

                    case "prioridad":
                        unaTareaProyecto.setMiPrioridad(Prioridad.valueOf(
                                etiquetaTareaProyecto.getTextContent().trim()));
                        break;

                    case "contexto":
                        unaTareaProyecto.setContexto(
                                etiquetaTareaProyecto.getTextContent().trim());
                        break;

                    case "complejidad":
                        unaTareaProyecto.setMiComplejidad(Complejidad.valueOf(
                                etiquetaTareaProyecto.getTextContent().trim()));
                        break;
                    case "anotacion":
                        unaTareaProyecto.setAnotacion(
                                etiquetaTareaProyecto.getTextContent().trim());
                        break;
                    case "nombre":
                        unaTareaProyecto.setNombre(
                                etiquetaTareaProyecto.getTextContent().trim());
                        break;
                }
            }
        }
        return unaTareaProyecto;
    }

    /**
     * @author Juan J. Luque Morales Guarda los contextos que lea del archivo
     * XML en el la lista "contextos" del repositorio.
     *
     * @param nl una lista de nodos que contiene los contextos.
     * @throws DOMException si hay algún fallo relacionado con el XML.
     */
    private static void procesarContextos(NodeList nl) throws DOMException {

        for (int i = 0; i < nl.getLength(); i++) {

            String contexto = "";

            if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {

                contexto = nl.item(i).getTextContent().trim();
                Repositorio.getInstancia().getContextos().add(contexto);
            }
        }
    }

    /**
     * @author Juan J. Luque Morales Convierte una etiqueta del XML a un objeto
     * TareaSimple.
     *
     * @param e un elemento que representa una TareaSimple.
     * @return un objeto TareaSimple.
     * @throws NumberFormatException si el id no tiene un valor numérico.
     * @throws DOMException si hay algún fallo relacionado con el XML.
     */
    private static TareaSimple procesarTareaSimple(Element e)
            throws NumberFormatException, DOMException {

        TareaSimple unaTareaSimple = new TareaSimple(
                "", Complejidad.Media, "", "", 0);
        //Atributo id.
        //unaTareaSimple.setId(Integer.parseInt(e.getAttribute("id")));

        //Lista de nodos del elemento TareaSimple.
        NodeList nodosTareaSimple = e.getChildNodes();

        for (int i = 0; i < nodosTareaSimple.getLength(); i++) {

            if (nodosTareaSimple.item(i).getNodeType() == Node.ELEMENT_NODE) {

                Element etiquetaTareaSimple = (Element) nodosTareaSimple.item(i);

                switch (etiquetaTareaSimple.getTagName()) {
                    case "nombre":
                        unaTareaSimple.setNombre(
                                etiquetaTareaSimple.getTextContent().trim());
                        break;

                    case "contexto":
                        unaTareaSimple.setContexto(
                                etiquetaTareaSimple.getTextContent().trim());
                        break;

                    case "anotacion":
                        unaTareaSimple.setAnotacion(
                                etiquetaTareaSimple.getTextContent().trim());
                        break;

                    case "complejidad":
                        unaTareaSimple.setMiComplejidad(Complejidad.valueOf(
                                etiquetaTareaSimple.getTextContent().trim()));
                        break;
                }
            }
        }
        return unaTareaSimple;
    }

    /**
     * @author Juan J. Luque Morales Convierte una etiqueta del archivo XML a un
     * objeto TareaAgenda.
     *
     * @param e un elemento que representa una TareaAgenda.
     * @return un objeto TareaAgenda.
     * @throws DOMException si hay algún fallo relacionado con el XML.
     * @throws NumberFormatException si el id no tiene valor numérico.
     */
    private static TareaAgenda procesarTareaAgenda(Element e)
            throws DOMException, NumberFormatException {

        TareaAgenda unaTareaAgenda = new TareaAgenda(null, null, "",
                Complejidad.Media, "", "", 0);

        //Atributo id
        //unaTareaAgenda.setId(Integer.parseInt(e.getAttribute("id")));
        //Lista de nodos del elemento TareaAgenda
        NodeList nodosTareaAgenda = e.getChildNodes();

        for (int i = 0; i < nodosTareaAgenda.getLength(); i++) {

            if (nodosTareaAgenda.item(i).getNodeType() == Node.ELEMENT_NODE) {

                Element etiquetaTareaAgenda = (Element) nodosTareaAgenda.item(i);

                switch (etiquetaTareaAgenda.getTagName()) {

                    case "fecha_fin":
                        unaTareaAgenda.setFechaFin(Timestamp.valueOf(
                                etiquetaTareaAgenda.getTextContent().trim()));
                        break;

                    case "fecha_inicio":
                        unaTareaAgenda.setFechaInicio(Timestamp.valueOf(
                                etiquetaTareaAgenda.getTextContent().trim()));
                        break;

                    case "contexto":
                        unaTareaAgenda.setContexto(
                                etiquetaTareaAgenda.getTextContent().trim());
                        break;

                    case "complejidad":
                        unaTareaAgenda.setMiComplejidad(Complejidad.valueOf(
                                etiquetaTareaAgenda.getTextContent().trim()));
                        break;

                    case "anotacion":
                        unaTareaAgenda.setAnotacion(
                                etiquetaTareaAgenda.getTextContent().trim());
                        break;

                    case "nombre":
                        unaTareaAgenda.setNombre(
                                etiquetaTareaAgenda.getTextContent().trim());
                        break;
                }
            }
        }
        return unaTareaAgenda;
    }

    /**
     * @author Juan J. Luque Morales Convierte una etiqueta del archivo XML en
     * un objeto TareaEntrada.
     *
     * @param e el elemento del archivo XML que representa una TareaEntrada.
     * @return un objeto TareaEntrada.
     * @throws DOMException si hay algún fallo relacionado con el XML.
     * @throws NumberFormatException si el id no tiene valor numérico.
     */
    private static TareaEntrada procesarTareaEntrada(Element e)
            throws DOMException, NumberFormatException {

        TareaEntrada unaTareaEntrada;
        //String idTareaEntrada = "";
        String nombreTareaEntrada = "";

        nombreTareaEntrada = e.getTextContent().trim();

        //idTareaEntrada = e.getAttribute("id").trim();

        unaTareaEntrada = new TareaEntrada(nombreTareaEntrada);

        return unaTareaEntrada;
    }

    /**
     * @author Juan J. Luque Morales Compara la fecha de modificación del
     * archivo xml con la fecha de la última conexión a la base de datos del
     * programa.
     *
     * @param l un long que representa la fecha de la última sincronización de
     * la base de datos.
     * @return true si están sincronizados y false si no lo están.
     */
    public static boolean comprobarSincro(long l) {

        boolean datosSincro = false;

        File f = new File(ruta);
        long fm = f.lastModified();

        if (fm == l) {
            datosSincro = true;
        }

        return datosSincro;

    }

    //##################Clase DOMUtil SIEMPRE al final##########################
    /**
     * Utilidades para pasar árboles DOM a documentos XML y viceversa.
     *
     * @author Salvador Romero Villegas
     */
    private static class DOMUtil {

        /**
         * Carga un archivo con un documento XML a un árbol DOM.
         *
         * @param CaminoAArchivoXml puede ser un archivo local de tu disco duro
         * o una URI de Internet (http://...).
         * @return el documento DOM o null si no se ha podido cargar el
         * documento.
         */
        public static Document XML2DOM(String CaminoAArchivoXml) {
            Document doc = null;
            try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                doc = db.parse(CaminoAArchivoXml);

            } catch (Exception ex) {
                Logger.getLogger(DOMUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
            return doc;
        }

        /**
         * Convierte una cadena que contiene un documento XML a un árbol DOM.
         *
         * @param documentoXML cadena que contiene el documento XML.
         * @return El árbol DOM o null si no se ha podido convertir.
         */
        public static Document String2DOM(String documentoXML) {
            ByteArrayInputStream bais = new ByteArrayInputStream(documentoXML.getBytes());
            Document doc = null;
            try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                doc = db.parse(bais);

            } catch (Exception ex) {
                Logger.getLogger(DOMUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
            return doc;
        }

        /**
         * Convierte un árbol DOM a una cadena que contiene un documento XML.
         *
         * @param doc Árbol DOM.
         * @return null si no se ha podido convertir o la cadena con el
         * documento en XML si se ha podido convertir.
         */
        public static String DOM2XML(Document doc) {
            String xmlString = null;
            try {
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                StreamResult result = new StreamResult(new StringWriter());
                DOMSource source = new DOMSource(doc);
                transformer.transform(source, result);
                xmlString = result.getWriter().toString();
            } catch (TransformerException ex) {
                Logger.getLogger(DOMUtil.class.getName()).log(Level.SEVERE, null, ex);
                xmlString = null;
            }
            return xmlString;
        }

        /**
         * Convierte un árbol DOM a XML y lo guarda en un archivo.
         *
         * @param doc Documento a convertir en XML.
         * @param CaminoAlArchivoXML Camino o path para llegar al archivo en el
         * disco.
         * @return true si se ha podido convertir y false en cualquier otra
         * situación.
         */
        public static boolean DOM2XML(Document doc, String CaminoAlArchivoXML) {
            try {
                File f = new File(CaminoAlArchivoXML);
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                StreamResult result = new StreamResult(f);
                DOMSource source = new DOMSource(doc);
                transformer.transform(source, result);
                return true;
            } catch (TransformerException ex) {
                Logger.getLogger(DOMUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }

        /**
         * Crea un árbol DOM vacío.
         *
         * @param etiquetaRaiz Nombre de la etiqueta raíz del árbol DOM, donce
         * estará contenida el resto del documento.
         * * @return Retornará el documento creado o null si se ha producido
         * algún error.
         */
        public static Document crearDOMVacio(String etiquetaRaiz) {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db;
            Document d = null;
            try {
                db = dbf.newDocumentBuilder();
                d = db.newDocument();
                d.appendChild(d.createElement(etiquetaRaiz));
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(DOMUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
            return d;
        }
    }
}
