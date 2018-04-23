/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas_integracion.IEDatos;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.StringWriter;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pruebas_integracion.administradorDeTareas.Proyecto;
import pruebas_integracion.administradorDeTareas.TareaAgenda;
import pruebas_integracion.administradorDeTareas.TareaEntrada;
import pruebas_integracion.administradorDeTareas.TareaSimple;

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

    public static String getRuta() {
        return ruta;
    }

    public static void setRuta(String ruta) {
        IEDatos.ruta = ruta;
    }

    /**
     * Guarda los datos del programa en un archivo XML.
     *
     * @param ruta la ruta donde se guardará el archivo.
     */
    public static void guardarXml(String ruta) {

    }

    /**
     * Carga los datos del programa desde un archivo XML.
     *
     * @param ruta la ruta de origen del archivo a cargar.
     */
    public static void cargarDesdeXml(String ruta) {
         //Crear DOM vacío
        Document xml = pruebasXML.DOMUtil.crearDOMVacio("gtd");
        //ES POSIBLE QUE A GTD SE AÑANDA UN ATRIBUTO USUARIO
        if(!RepoProvisional.getInstance().getBandejaEntrada().isEmpty()){
         Element eleBandejaEntrada=xml.createElement("bandeja_entrada");
            for(TareaEntrada te : RepoProvisional.getInstance().getBandejaEntrada()){
                
                Element eleTareaEntrada=xml.createElement("tarea_entrada");
                eleBandejaEntrada.appendChild(eleTareaEntrada);
                Element eleNombreTareaEntrada = xml.createElement("nombre");
                eleNombreTareaEntrada.setTextContent(te.getNombre());
                eleTareaEntrada.appendChild(eleNombreTareaEntrada);
                
            }
        }
        if(!RepoProvisional.getInstance().getListaTareasSimples().isEmpty()){
        
            Element eleListaTareasSimples=xml.createElement("lista_tareas_simples");
            for(TareaSimple ts : RepoProvisional.getInstance().getListaTareasSimples()){
                
                Element eleTareaSimple=xml.createElement("tarea_simple");
                eleListaTareasSimples.appendChild(eleTareaSimple);
                Element eleNombreTareaSimple = xml.createElement("nombre");
                eleNombreTareaSimple.setTextContent(ts.getNombre());
                eleTareaSimple.appendChild(eleNombreTareaSimple);
                Element eleContextoTareaSimple=xml.createElement("contexto");
                eleContextoTareaSimple.setTextContent(ts.getContexto());
                eleTareaSimple.appendChild(eleContextoTareaSimple);
                Element eleAnotacionTareaSimple=xml.createElement("anotacion");
                eleAnotacionTareaSimple.setTextContent(ts.getAnotacion());
                eleTareaSimple.appendChild(eleAnotacionTareaSimple);
                Element eleComplejidadTareaSimple=xml.createElement("complejidad");
                eleComplejidadTareaSimple.setTextContent(ts.getMiComplejidad().toString());
                eleTareaSimple.appendChild(eleComplejidadTareaSimple);
                Element eleRequisitosTareaSimple=xml.createElement("requisitos");
                eleRequisitosTareaSimple.setTextContent(ts.getRequisitos());
                eleTareaSimple.appendChild(eleRequisitosTareaSimple);
            }
        }
        //METER TAREAS AGENDA ANTES DE PROYECTOS
        if(!RepoProvisional.getInstance().getMisProyectos().isEmpty()){
            
            Element eleListaProyectos=xml.createElement("mis_proyectos");
            for(Proyecto p : RepoProvisional.getInstance().getMisProyectos()){
                
            }
        }
        /*
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

        System.out.println(pruebasXML.DOMUtil.DOM2XML(xml, ruta));
        
     */   
    }

    /**
     * Compara la fecha de modificación del archivo xml con la fecha de la
     * última conexión a la base de datos del programa.
     */
    public static boolean compararSincro() {
        
        boolean datosSincro = false;
        
        return datosSincro;
    }

    //###############Clase DOMUtil SIEMPRE al final#############################
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
