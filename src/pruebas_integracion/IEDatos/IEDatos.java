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
import java.util.ArrayList;
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
import pruebas_integracion.administradorDeTareas.Proyecto;
import pruebas_integracion.administradorDeTareas.TareaAgenda;
import pruebas_integracion.administradorDeTareas.TareaEntrada;
import pruebas_integracion.administradorDeTareas.TareaSimple;
import pruebas_integracion.persistencia.Repositorio;

/**
 * Clase que se usa para guardar y cargar datos desde archivos xml.
 *
 * @author Juan José Luque Morales
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
                        NodeList nodosAgenda = e.getChildNodes();

                        procesarTareaAgenda(nodosAgenda);
                        break;

                    case "bandeja_entrada":

                        //Lista de nodos con todas las TareasEntrada
                        NodeList nodosBandejaEntrada = e.getChildNodes();

                        procesarTareaEntrada(nodosBandejaEntrada);
                        break;

                    /*case "tareas_inmediatas":
                        break;*/
                    case "proyectos":

                        //Lista de nodos de elementos Proyecto
                        NodeList proyectos = e.getChildNodes();
                        
                        procesarProyectos(proyectos);
                        break;

                    /*case "papelera":
                        break;*/
                    case "archivo_seguimiento":

                        //Lista de nodos con todas las TareasSimples.
                        NodeList nodosArchivoSeguimiento = e.getChildNodes();

                        procesarTareaSimple(nodosArchivoSeguimiento);
                        break;

                    /*case "archivo_consulta":
                        break;

                    //¡Ojo! Aquí se guardan objetos TareaSimple y TareaInmediata.
                    case "acciones_siguientes":
                        break;*/
                }
            }

        }
    }

    private static void procesarProyectos(NodeList nl) throws DOMException, NumberFormatException {

        for (int i = 0; i < nl.getLength(); i++) {

            String idProyecto = "";
            String nombreProyecto = "";
            String fechaFin = "";
            ArrayList listaTareasProyecto = new ArrayList();

            if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
                
                //Elemento Proyecto
                Element proyecto = (Element) nl.item(i);
                //Atributo id
                idProyecto = proyecto.getAttribute("id");
                //Lista de nodos del elemento Proyecto
                NodeList nodosProyecto = proyecto.getChildNodes();
                
                for (int j = 0; j < nodosProyecto.getLength(); j++){
                    
                    if (nodosProyecto.item(j).getNodeType() == Node.ELEMENT_NODE){
                        
                        Element etiquetaProyecto = (Element) nodosProyecto.item(j);
                        
                        switch (etiquetaProyecto.getTagName()){
                            
                            case "nombre":
                                nombreProyecto = etiquetaProyecto.getTextContent().trim();
                                break;
                                
                            case "fecha_fin":
                                fechaFin = etiquetaProyecto.getTextContent().trim();
                                break;
                        }
                    }
                }
                
                Proyecto unProyecto = new Proyecto(
                        nombreProyecto, 
                        Timestamp.valueOf(fechaFin), 
                        Integer.parseInt(idProyecto));
            }
        }
    }

    private static void procesarTareaProyecto(NodeList nl) throws DOMException, NumberFormatException {
        
        
    }

    private static void procesarContextos(NodeList nl) throws DOMException {

        for (int i = 0; i < nl.getLength(); i++) {

            String contexto = "";

            if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {

                contexto = nl.item(i).getTextContent().trim();
                Repositorio.getInstancia().getContextos().add(contexto);
            }
        }
    }

    private static void procesarTareaSimple(NodeList nl) throws NumberFormatException, DOMException {

        for (int i = 0; i < nl.getLength(); i++) {

            String idTareaSimple = "";
            String nombreTareaSimple = "";
            String contextoTareaSimple = "";
            String anotacionTareaSimple = "";
            String complejidadTareaSimple = "";

            if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {

                //Elemento TareaSimple.
                Element tareaSimple = (Element) nl.item(i);

                //Atributo id.
                idTareaSimple = tareaSimple.getAttribute("id");

                //Lista de nodos del elemento TareaSimple.
                NodeList nodosTareaSimple = tareaSimple.getChildNodes();

                /*Creamos un objeto TareaSimple a partir del
                contenido de las etiquetas.*/
                for (int j = 0; j < nodosTareaSimple.getLength(); j++) {

                    if (nodosTareaSimple.item(j).getNodeType() == Node.ELEMENT_NODE) {

                        Element etiquetaTareaSimple = (Element) nodosTareaSimple.item(j);

                        switch (etiquetaTareaSimple.getTagName()) {
                            case "nombre":
                                nombreTareaSimple
                                        = etiquetaTareaSimple.getTextContent().trim();
                                break;

                            case "contexto":
                                contextoTareaSimple
                                        = etiquetaTareaSimple.getTextContent().trim();
                                break;

                            case "anotacion":
                                anotacionTareaSimple
                                        = etiquetaTareaSimple.getTextContent().trim();
                                break;

                            case "complejidad":
                                complejidadTareaSimple
                                        = etiquetaTareaSimple.getTextContent().trim();
                                break;
                        }
                    }
                }
                TareaSimple unaTareaSimple = new TareaSimple(
                        contextoTareaSimple,
                        Complejidad.valueOf(complejidadTareaSimple),
                        anotacionTareaSimple,
                        nombreTareaSimple,
                        Integer.parseInt(idTareaSimple));
                Repositorio.getInstancia().agregarEnSeguimiento(
                        unaTareaSimple);
            }
        }
    }

    private static void procesarTareaAgenda(NodeList nl) throws DOMException, NumberFormatException {

        for (int i = 0; i < nl.getLength(); i++) {

            String fechaFinTareaAgenda = "";
            String fechaInicioTareaAgenda = "";
            String contextoTareaAgenda = "";
            String complejidadTareaAgenda = "";
            String anotacionTareaAgenda = "";
            String nombreTareaAgenda = "";
            String idTareaAgenda = "";

            if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {

                //Lista de nodos del elemento TareaAgenda
                Element tareaAgenda = (Element) nl.item(i);
                //Atributo id
                idTareaAgenda = tareaAgenda.getAttribute("id");
                //Lista de nodos del elemento TareaAgenda
                NodeList nodosTareaAgenda = tareaAgenda.getChildNodes();

                for (int j = 0; j < nodosTareaAgenda.getLength(); j++) {

                    if (nodosTareaAgenda.item(j).getNodeType() == Node.ELEMENT_NODE) {

                        Element etiquetaTareaAgenda = (Element) nodosTareaAgenda.item(j);

                        switch (etiquetaTareaAgenda.getTagName()) {

                            case "fecha_fin":
                                fechaFinTareaAgenda
                                        = etiquetaTareaAgenda.getTextContent();
                                break;

                            case "fecha_inicio":
                                fechaInicioTareaAgenda
                                        = etiquetaTareaAgenda.getTextContent();
                                break;

                            case "contexto":
                                contextoTareaAgenda
                                        = etiquetaTareaAgenda.getTextContent().trim();
                                break;

                            case "complejidad":
                                complejidadTareaAgenda
                                        = etiquetaTareaAgenda.getTextContent().trim();
                                break;

                            case "anotacion":
                                anotacionTareaAgenda
                                        = etiquetaTareaAgenda.getTextContent().trim();
                                break;

                            case "nombre":
                                nombreTareaAgenda
                                        = etiquetaTareaAgenda.getTextContent().trim();
                                break;
                        }
                    }
                }
                //Tenemos que preguntar el formato de fecha
                TareaAgenda unaTareaAgenda = new TareaAgenda(
                        Timestamp.valueOf(fechaFinTareaAgenda),
                        Timestamp.valueOf(fechaInicioTareaAgenda),
                        contextoTareaAgenda,
                        Complejidad.valueOf(complejidadTareaAgenda),
                        anotacionTareaAgenda,
                        nombreTareaAgenda,
                        Integer.parseInt(idTareaAgenda)
                );

                Repositorio.getInstancia().getAgenda().add(unaTareaAgenda);
            }
        }
    }

    private static void procesarTareaEntrada(NodeList nl) throws DOMException, NumberFormatException {

        for (int i = 0; i < nl.getLength(); i++) {

            String idTareaEntrada = "";
            String nombreTareaEntrada = "";

            if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {

                //Elemento TareaEntrada
                Element tareaEntrada = (Element) nl.item(i);
                //Etiqueta nombre.
                nombreTareaEntrada = tareaEntrada.getTextContent().trim();
                //Atributo id
                idTareaEntrada = tareaEntrada.getAttribute("id").trim();

                //Creamos un objeto y lo guardamos en el repositorio.
                TareaEntrada unaTareaEntrada = new TareaEntrada(
                        nombreTareaEntrada,
                        Integer.parseInt(idTareaEntrada));

                Repositorio.getInstancia().agregarEnBandeja(unaTareaEntrada);
            }
        }
    }

    /**
     * Compara la fecha de modificación del archivo xml con la fecha de la
     * última conexión a la base de datos del programa.
     *
     * @return true si están sincronizados y false si no lo están.
     */
    public static boolean compararSincro() {

        boolean datosSincro = false;

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
