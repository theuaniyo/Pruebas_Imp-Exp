/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas_integracion.IEDatos;

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
import pruebas_integracion.administradorDeTareas.Complejidad;
import pruebas_integracion.administradorDeTareas.Prioridad;
import pruebas_integracion.administradorDeTareas.Proyecto;
import pruebas_integracion.administradorDeTareas.TareaAgenda;
import pruebas_integracion.administradorDeTareas.TareaEntrada;
import pruebas_integracion.administradorDeTareas.TareaInmediata;
import pruebas_integracion.administradorDeTareas.TareaProyecto;
import pruebas_integracion.administradorDeTareas.TareaSimple;
import pruebas_integracion.persistencia.Repositorio;

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

    }

    /**
     * @author Juan J. Luque Morales
     * Carga los datos del programa desde un archivo XML. Para cambiar la ruta
     * desde la que se cargará el archivo, hay que usar el método setRuta de
     * esta clase.
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

                    case "papelera":

                        //Lista de nodos dentro de la etiqueta papelera
                        NodeList papelera = e.getChildNodes();

                        for (int j = 0; j < papelera.getLength(); j++) {

                            if (papelera.item(j).getNodeType() == Node.ELEMENT_NODE) {

                                Element tareaEnPapelera = (Element) papelera.item(j);

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

                                    //Preguntar si los proyectos tendrán otra papelera específica
                                    /*case "proyecto":
                                        
                                        Proyecto unProyecto 
                                                = procesarProyecto(tareaEnPapelera);
                                        Repositorio.getInstancia().agregarEnPapelera(unProyecto);
                                        break;*/
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
                                }
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

                                        TareaInmediata unaTareaInmediata
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
     * @author Juan J. Luque Morales
     * Convierte una etiqueta del archivo XML a un objeto TareaInmediata.
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
        unaTareaInmediata.setId(Integer.parseInt(e.getAttribute("id")));

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

                        unaTareaInmediata.setDescripcion(
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
     * @author Juan J. Luque Morales
     * Convierte una etiqueta del archivo XML a un objeto Proyecto.
     *
     * @param e un elemento del XML que representa un Proyecto.
     * @return un objeto Proyecto.
     * @throws DOMException si hay un error relacionado con el XML.
     * @throws NumberFormatException si el id del proyecto no es un valor
     * numérico.
     */
    private static Proyecto procesarProyecto(Element e)
            throws DOMException, NumberFormatException {

        Proyecto unProyecto = new Proyecto("", null, 0);

        //Atributo id
        unProyecto.setId(Integer.parseInt(e.getAttribute("id")));
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
     * @author Juan J. Luque Morales
     * Convierte una etiqueta del archivo XML a un objeto TareaProyecto.
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
        unaTareaProyecto.setId(Integer.parseInt(e.getAttribute("id")));

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
                        unaTareaProyecto.setDescripcion(
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
     * @author Juan J. Luque Morales
     * Guarda los contextos que lea del archivo XML en el la lista "contextos"
     * del repositorio.
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
     * @author Juan J. Luque Morales
     * Convierte una etiqueta del XML a un objeto TareaSimple.
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
        unaTareaSimple.setId(Integer.parseInt(e.getAttribute("id")));

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
                        unaTareaSimple.setDescripcion(
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
     * @author Juan J. Luque Morales
     * Convierte una etiqueta del archivo XML a un objeto TareaAgenda.
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
        unaTareaAgenda.setId(Integer.parseInt(e.getAttribute("id")));
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
                        unaTareaAgenda.setDescripcion(
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
     * @author Juan J. Luque Morales
     * Convierte una etiqueta del archivo XML en un objeto TareaEntrada.
     *
     * @param e el elemento del archivo XML que representa una TareaEntrada.
     * @return un objeto TareaEntrada.
     * @throws DOMException si hay algún fallo relacionado con el XML.
     * @throws NumberFormatException si el id no tiene valor numérico.
     */
    private static TareaEntrada procesarTareaEntrada(Element e)
            throws DOMException, NumberFormatException {

        TareaEntrada unaTareaEntrada;
        String idTareaEntrada = "";
        String nombreTareaEntrada = "";

        nombreTareaEntrada = e.getTextContent().trim();

        idTareaEntrada = e.getAttribute("id").trim();

        unaTareaEntrada = new TareaEntrada(nombreTareaEntrada,
                Integer.parseInt(idTareaEntrada));

        return unaTareaEntrada;
    }

    /**
     * @author Juan J. Luque Morales
     * Compara la fecha de modificación del archivo xml con la fecha de la
     * última conexión a la base de datos del programa.
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
