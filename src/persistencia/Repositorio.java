package persistencia;

import administradorDeTareas.Complejidad;
import administradorDeTareas.TareaAgenda;
import administradorDeTareas.TareaEntrada;
import administradorDeTareas.TareaInmediata;
import administradorDeTareas.TareaSimple;
import administradorDeTareas.Usuario;
//import GoogleCalendar.CalendarioIO;
//import GoogleCalendar.Conexion;
import IEDatos.IEDatos;
//import com.google.api.client.util.DateTime;
//import com.google.api.services.calendar.model.CalendarList;
//import com.google.api.services.calendar.model.CalendarListEntry;
//import com.google.api.services.calendar.model.Event;
//import com.google.api.services.calendar.model.Events;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Xaxo
 * @author Pedro A Alcantara
 * @author Jesus Budía
 * @author Álvaro Luque
 * @author Oscar Somoza
 * @author Baltasar Jimenez
 */
public class Repositorio {

    private ArrayList<String> contextos;
    private ArrayList<TareaAgenda> agenda;
    private ArrayList<TareaEntrada> bandejaEntrada;
    private ArrayList<TareaInmediata> tareasInmediatas;
    private ArrayList<TareaEntrada> tareasFinalizada;
    private ArrayList<TareaSimple> tareasSimples;
    private ArrayList<TareaEntrada> papelera;

    //Archivar las tareas descartas en una papelera o borrar definitivamente?
    /*private ArrayList<TareaEntrada> papelera; */
    //Algún día  tal vez
    private ArrayList<TareaEntrada> archivoSeguimiento;
    private ArrayList<TareaEntrada> archivoConsulta;

    private ArrayList<TareaSimple> accionesSiguientes;

    private static Repositorio instancia = null;

    private AccesoBD accesoBD;

    //Devuelve la instancia, necesario por usar el patrón de diseño Singleton.
    public static Repositorio getInstancia() throws SQLException, FileNotFoundException {
        if (instancia == null) {
            instancia = new Repositorio();
        }
        return instancia;

    }

    //Constructor
    private Repositorio() throws SQLException, FileNotFoundException {
        //Conectar con base de datos e inicializar todos los conjuntos.
        accesoBD = new AccesoBD();
        contextos = new ArrayList<>();
        agenda = new ArrayList<>();
        bandejaEntrada = new ArrayList<>();
        tareasInmediatas = new ArrayList<>();
        archivoSeguimiento = new ArrayList<>();
        archivoConsulta = new ArrayList<>();
        accionesSiguientes = new ArrayList<>();
        tareasFinalizada = new ArrayList<>();
        tareasSimples = new ArrayList<>();

        try {

            cargarTareasEntrada();
            cargarTareasAgenda();
            cargarTareasInmediatas();
            cargarTareasFinalizadas();
            cargarTareasSimples();

        } catch (SQLException e) {
            IEDatos.cargarDesdeXml();
        }

    }

    public ArrayList<String> getContextos() {
        return contextos;
    }

    public ArrayList<TareaInmediata> getTareasInmediatas() {
        return tareasInmediatas;
    }

    public ArrayList<TareaEntrada> getPapelera() {
        return papelera;
    }

    public ArrayList<TareaEntrada> getArchivoSeguimiento() {
        return archivoSeguimiento;
    }

    public ArrayList<TareaSimple> getTareasSimples() {
        return tareasSimples;
    }

    public ArrayList<TareaEntrada> getArchivoConsulta() {
        return archivoConsulta;
    }

    public ArrayList<TareaSimple> getAccionesSiguientes() {
        return accionesSiguientes;
    }

    public AccesoBD getAccesoBD() {
        return accesoBD;
    }

    public void agregarEnContextos(String contexto) {
        contextos.add(contexto);
    }

    public void agregarEnAgenda(TareaAgenda nuevaTarea) {
        agenda.add(nuevaTarea);
    }

    public void agregarEnBandeja(TareaEntrada nuevaTarea) {
        bandejaEntrada.add(nuevaTarea);

    }

    public void agregarEnSimples(TareaSimple nuevaTarea) {
        tareasSimples.add(nuevaTarea);

    }

    public void agregarEnInmediatas(TareaInmediata nuevaTarea) {
        tareasInmediatas.add(nuevaTarea);
    }

    public void agregarEnSeguimiento(TareaEntrada nuevaTarea) {
        archivoSeguimiento.add(nuevaTarea);
    }

    public void agregarEnConsulta(TareaEntrada nuevaTarea) {
        archivoConsulta.add(nuevaTarea);
    }

    public void agregarEnSiguientes(TareaSimple nuevaTarea) {
        accionesSiguientes.add(nuevaTarea);
    }

    public void agregarEnFinalizadas(TareaEntrada nuevaTarea) {
        tareasFinalizada.add(nuevaTarea);
    }

    public ArrayList<TareaEntrada> getTareasFinalizada() {
        return tareasFinalizada;
    }

    /**
     * Agrega una tarea a un proyecto.
     *
     * @param nuevaTarea
     */


    public void quitarEnAgenda(TareaAgenda nuevaTarea) {
        agenda.remove(nuevaTarea);
    }

    public void quitarEnBandeja(TareaEntrada nuevaTarea) {
        bandejaEntrada.remove(nuevaTarea);
    }

    public void quitarEnInmediatas(TareaSimple nuevaTarea) {
        tareasInmediatas.remove(nuevaTarea);
    }

    public void quitarEnSeguimiento(TareaEntrada nuevaTarea) {
        archivoSeguimiento.remove(nuevaTarea);
    }

    public void quitarEnConsulta(TareaEntrada nuevaTarea) {
        archivoConsulta.remove(nuevaTarea);
    }

    public void quitarEnSiguientes(TareaSimple nuevaTarea) {
        accionesSiguientes.remove(nuevaTarea);
    }

    public void quitarEnContextos(String contexto) {
        contextos.remove(contexto);
    }

    public void quitarEnSimples(TareaSimple tareaSimple) {
        tareasSimples.remove(tareaSimple);
    }


    
    /**
     * Author Álvaro Luque Jiménez
     *
     * @param contrasena
     * @param nick
     * @param email
     * @throws SQLException
     */
    public void insertarUsuario(String contrasena, String nick, String email) throws SQLException, FileNotFoundException {

        Statement stm = accesoBD.abrirConexion().createStatement();

        String insertar = "insert into Usuario values('" + nick + "','" + contrasena + "','" + email + "')";

        stm.execute(insertar);

        accesoBD.cerrarConexion(accesoBD.abrirConexion());

        IEDatos.guardarXml();

    }

    /**
     * Author Álvaro Luque Jiménez
     *
     * @param contrasena
     * @param nick
     * @return
     * @throws SQLException
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public boolean existeUsuario(String contrasena, String nick) throws SQLException, UnsupportedEncodingException, NoSuchAlgorithmException {

        String hashContrasena = passHash(contrasena);

        boolean existe = false;

        String contrasenyaBaseDatos = "";

        String nickBaseDatos = "";

        Connection con = accesoBD.abrirConexion();

        Statement stm = con.createStatement();

        ResultSet rs = stm.executeQuery("select contrasenya, nick from Usuario where contrasenya = '" + hashContrasena + "' and nick = '" + nick + "'");

        while (rs.next()) {
            contrasenyaBaseDatos = rs.getString("contrasenya");
            nickBaseDatos = rs.getString("nick");
        }

        existe = nickBaseDatos.equals(nick);
        existe = contrasenyaBaseDatos.equals(hashContrasena);

        accesoBD.cerrarConexion(con);

        return existe;

    }

    private String passHash(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(StandardCharsets.UTF_8.encode(password));
        return String.format("%032x", new BigInteger(1, md5.digest()));
    }

    public ArrayList<TareaEntrada> getBandejaEntrada() {
        return bandejaEntrada;
    }

    public ArrayList<TareaAgenda> getAgenda() {
        return agenda;
    }

    public void agregarEnPapelera(TareaEntrada nuevaTarea) {
        papelera.add(nuevaTarea);
    }

    public void procesarTarea(TareaEntrada aProcesar, TareaSimple procesada) throws SQLException, FileNotFoundException {
        if (!aProcesar.getNombre().equals(procesada.getNombre())) {
            throw new IllegalArgumentException("Las tareas no tienen el mismo nombre");
        } //Sí coinciden los nombres
        else {
            quitarEnBandeja(aProcesar);
            //Comprobamos el tipo
            if (procesada instanceof TareaAgenda) {
                agregarEnAgenda((TareaAgenda) procesada);
                agregarTareasAgendaBD((TareaAgenda) procesada);
            } else if (procesada instanceof TareaSimple) {
                agregarEnSimples(procesada);
                agregarTareasSimplesBD(procesada);
            }
        }
        ////Problemática también debe de poderse procesar y que vaya a LA LISTA tarea inmediatas. SOLUCIONAR
    }

    public void procesarTarea(TareaEntrada aProcesar, String listaDestino) {

        if (listaDestino.equals("Papelera")) {
            agregarEnPapelera(aProcesar);
        } else if (listaDestino.equals("Archivo seguimiento")) {
            agregarEnSeguimiento(aProcesar);
        } else if (listaDestino.equals("Archivo consulta")) {
            agregarEnConsulta(aProcesar);
        }

        quitarEnBandeja(aProcesar);

    }

    /**
     * Author Álvaro Luque Jiménez
     *
     * @throws SQLException
     */
    public void cargarTareasEntrada() throws SQLException {

        String nombre = "";

        TareaEntrada nuevaTarea = null;

        Connection con = accesoBD.abrirConexion();

        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM TareaEntrada WHERE nombre NOT IN (SELECT nombre FROM TareaSimple);");

        while (rs.next()) {

            nombre = rs.getString("nombre");

            nuevaTarea = new TareaEntrada(nombre);

            agregarEnBandeja(nuevaTarea);
        }

        accesoBD.cerrarConexion(con);

    }

    public void cargarTareasSimples() throws SQLException {

        String nombre = "";
        String anotacion = "";
        String complejidad = "";

        String contexto = "";

        boolean delegada;

        TareaSimple nuevaTarea = null;

        Connection con = accesoBD.abrirConexion();

        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM TareaSimple WHERE nombre NOT IN (SELECT nombre FROM TareaDeAgenda) AND nombre NOT IN (SELECT nombre FROM TareaDeProyecto)");

        while (rs.next()) {

            nombre = rs.getString("nombre");
            anotacion = rs.getString("anotacion");
            complejidad = rs.getString("complejidad");
            contexto = rs.getString("contexto");
            delegada = rs.getBoolean("delegada");

            nuevaTarea = new TareaSimple(contexto, Complejidad.valueOf(complejidad), anotacion, nombre, delegada);

            agregarEnSimples(nuevaTarea);

        }

        accesoBD.cerrarConexion(con);

    }

    public void cargarTareasInmediatas() throws SQLException {

        String nombre = "";
        String anotacion = "";
        String complejidad = "";
        boolean terminada = true;

        String contexto = "";

        TareaInmediata nuevaTarea = null;

        Connection con = accesoBD.abrirConexion();

        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT ti.nombre, te.nombre, ti.terminada FROM TareaInmediata ti, TareaEntrada te WHERE te.nombre=ti.nombre");

        while (rs.next()) {

            nombre = rs.getString("nombre");
            terminada = rs.getBoolean("terminada");

            nuevaTarea = new TareaInmediata(terminada, nombre);

            agregarEnInmediatas(nuevaTarea);
        }

        accesoBD.cerrarConexion(con);

    }

    public void cargarTareasAgenda() throws SQLException {

        String fechafin = "";
        String fechainicio = "";
        String nombre = "";
        String anotacion = "";
        String complejidad = "";

        String contexto = "";

        TareaAgenda nuevaTarea = null;

        Connection con = accesoBD.abrirConexion();

        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT te.nombre, fechainicio, fechafin,"
                + " anotacion, complejidad, contexto FROM TareaDeAgenda ta, TareaSimple ts, TareaEntrada te "
                + "WHERE ta.nombre=ts.nombre AND ta.nombre=te.nombre");

        while (rs.next()) {

            nombre = rs.getString("nombre");
            anotacion = rs.getString("anotacion");
            complejidad = rs.getString("complejidad");
            fechafin = rs.getString("fechafin");
            fechainicio = rs.getString("fechainicio");

            contexto = rs.getString("contexto");

            nuevaTarea = new TareaAgenda(Timestamp.valueOf(fechafin), Timestamp.valueOf(fechainicio), contexto, Complejidad.valueOf(complejidad), anotacion, nombre);

            agregarEnAgenda(nuevaTarea);

        }

        accesoBD.cerrarConexion(con);

    }

    public void cargarTareasFinalizadas() throws SQLException {

        String nombre = "";

        TareaEntrada nuevaTarea = null;

        Connection con = accesoBD.abrirConexion();

        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT nombre FROM Finalizada");

        while (rs.next()) {

            nombre = rs.getString("nombre");

            nuevaTarea = new TareaEntrada(nombre);

            agregarEnFinalizadas(nuevaTarea);
        }

        accesoBD.cerrarConexion(con);

    }

    /**
     *
     * @param tareaEntradaAgregar
     * @param usuarioConectado
     * @throws SQLException
     *
     * Author Álvaro Luque Jiménez
     */
    public void agregarTareasEntradaBD(TareaEntrada tareaEntradaAgregar) throws SQLException, FileNotFoundException {

        Connection con = accesoBD.abrirConexion();

        Statement stm = con.createStatement();

        stm.executeUpdate("INSERT INTO TareaEntrada (nombre) VALUES('" + tareaEntradaAgregar.getNombre() + "');");

        accesoBD.cerrarConexion(con);

        IEDatos.guardarXml();

    }

    /**
     *
     * @param tareaEntradaEliminar
     * @throws SQLException
     *
     * Author Álvaro Luque Jiménez
     */
    public void quitarTareasEntradaBD(TareaEntrada tareaEntradaEliminar) throws SQLException, FileNotFoundException {

        Connection con = accesoBD.abrirConexion();

        Statement stm = con.createStatement();

        stm.executeUpdate("DELETE FROM TareaEntrada WHERE nombre = \"" + tareaEntradaEliminar.getNombre() + "\"");

        accesoBD.cerrarConexion(con);

        IEDatos.guardarXml();

    }

    public void quitarTareasSimplesBD(TareaSimple tareaSimpleEliminar) throws SQLException, FileNotFoundException {

        Connection con = accesoBD.abrirConexion();

        Statement stm = con.createStatement();

        stm.executeUpdate("DELETE FROM TareaSimple WHERE nombre = \"" + tareaSimpleEliminar.getNombre() + "\"");

        accesoBD.cerrarConexion(con);

        IEDatos.guardarXml();

    }

    public void agregarTareasAgendaBD(TareaAgenda tareaAgenda) throws SQLException, FileNotFoundException {

        Connection con = accesoBD.abrirConexion();

        Statement stm = con.createStatement();

        stm.executeUpdate("INSERT INTO TareaDeAgenda VALUES(\"" + tareaAgenda.getNombre() + "\", \"" + tareaAgenda.getFechaInicio() + "\", \"" + tareaAgenda.getFechaFin() + "\")");

        accesoBD.cerrarConexion(con);

        IEDatos.guardarXml();

    }

    public void quitarTareasAgendaBD(TareaAgenda tareaAgendaEliminar) throws SQLException, FileNotFoundException {

        Connection con = accesoBD.abrirConexion();

        Statement stm = con.createStatement();

        stm.executeUpdate("DELETE FROM TareaDeAgenda WHERE nombre = \"" + tareaAgendaEliminar.getNombre() + "\"");

        accesoBD.cerrarConexion(con);

        IEDatos.guardarXml();

    }

    public void agregarTareasSimplesBD(TareaSimple tareaSimple) throws SQLException, FileNotFoundException {

        Connection con = accesoBD.abrirConexion();

        Statement stm = con.createStatement();

        stm.executeUpdate("INSERT INTO TareaSimple VALUES('" + tareaSimple.getNombre() + "', '" + tareaSimple.getAnotacion() + "', '" + tareaSimple.getMiComplejidad() + "', " + tareaSimple.isDelegada() + ", '" + tareaSimple.getContexto() + "')");

        accesoBD.cerrarConexion(con);

        IEDatos.guardarXml();

    }

    public void agregarTareasInmediatasBD(TareaInmediata tareaInmediata) throws SQLException, FileNotFoundException {

        Connection con = accesoBD.abrirConexion();

        Statement stm = con.createStatement();

        stm.executeUpdate("INSERT INTO TareaInmediata VALUES('" + tareaInmediata.getNombre() + "', " + tareaInmediata.isTerminada() + ")");

        accesoBD.cerrarConexion(con);

        IEDatos.guardarXml();

    }

    /**
     *
     * @param tareaEntradaFinalizada
     * @throws SQLException
     *
     * Author Pedro A Alcantara Author Jesús Budía
     */
    public void moverAFinalizadas(TareaEntrada tareaEntradaFinalizada) throws SQLException, FileNotFoundException {
        agregarEnFinalizadas(tareaEntradaFinalizada);
        quitarEnBandeja(tareaEntradaFinalizada);
        //Comprobamos el tipo
        if (tareaEntradaFinalizada instanceof TareaAgenda) {
            quitarEnAgenda((TareaAgenda) tareaEntradaFinalizada);

            Connection con = accesoBD.abrirConexion();

            Statement stm = con.createStatement();

            stm.executeUpdate("INSERT INTO Finalizada (nombre) VALUES(\"" + tareaEntradaFinalizada.getNombre() + "\")");

            stm.executeUpdate("DELETE FROM TareaDeAgenda WHERE nombre = \"" + tareaEntradaFinalizada.getNombre() + "\"");

            accesoBD.cerrarConexion(con);

            IEDatos.guardarXml();

        } else if (tareaEntradaFinalizada instanceof TareaInmediata) {

            Connection con = accesoBD.abrirConexion();

            Statement stm = con.createStatement();

            stm.executeUpdate("INSERT INTO Finalizada (nombre) VALUES(\"" + tareaEntradaFinalizada.getNombre() + "\")");

            stm.executeUpdate("DELETE FROM TareaEntrada WHERE nombre = \"" + tareaEntradaFinalizada.getNombre() + "\"");

            accesoBD.cerrarConexion(con);

            IEDatos.guardarXml();

        } else if (tareaEntradaFinalizada instanceof TareaEntrada) {

            Connection con = accesoBD.abrirConexion();

            Statement stm = con.createStatement();

            stm.executeUpdate("INSERT INTO Finalizada (nombre) VALUES(\"" + tareaEntradaFinalizada.getNombre() + "\")");

            stm.executeUpdate("DELETE FROM TareaEntrada WHERE nombre = \"" + tareaEntradaFinalizada.getNombre() + "\"");

            accesoBD.cerrarConexion(con);

            IEDatos.guardarXml();

        }
    }

    /**
     * Exporta todas las tareas agenda en forma de eventos, en el caso de que ya
     * se hubiese exportado con anterioridad se comprueba para que no se
     * duplique.
     *
     * @throws IOException
     */
    /*
    public void exportarEventos() throws IOException {
        String descripcion = "";
        String delegada = "";
        for (TareaAgenda tarea : agenda) {
            if (!CalendarioIO.comprobarExistente(tarea.getNombre())) {
                if (tarea.isDelegada()) {
                    delegada = "Es delegada";
                } else {
                    delegada = "No es delegada";
                }
                descripcion = tarea.getAnotacion() + "|" + tarea.getMiComplejidad();
                CalendarioIO.crearEvento(tarea.getNombre(), tarea.getContexto(), descripcion, tarea.getFechaInicio(), tarea.getFechaFin());
            }
        }
    }
    */

    /**
     * Importa todas las tareas desde googlecalendar en forma de tareas agenda,
     * en el caso de que ya se hubiese importado con anterioridad se comprueba
     * para que no se duplique.
     *
     * @throws IOException
     */
    /*
    public void importarEventos() throws IOException {
        String titulo = "";
        String contexto = "";
        String descripcion = "";
        String cadenaComplejidad = "";
        Complejidad complejidad = Complejidad.Baja;
        String anotacion = "";
        boolean delegada = false;
        String cadenaDelegada = "";
        String rutaFichero = "";
        Timestamp fechaInicio;
        Timestamp fechaFin;

        Events eventos = null;

        com.google.api.services.calendar.Calendar service
                = Conexion.getCalendarService();

        DateTime now = new DateTime(System.currentTimeMillis());
        CalendarList listaCalendarios = service.calendarList().list().execute();
        List<CalendarListEntry> lista = listaCalendarios.getItems();

        //Por cada calendario creamos su lista de eventos
        for (CalendarListEntry entradaCalendario : lista) {
            eventos = CalendarioIO.generarEventosCalendario(service, now, eventos, entradaCalendario.getId());
            List<Event> listaEventos = eventos.getItems();

            //Se importa cada evento si es que no estaba importado ya.
            for (Event evento : listaEventos) {

                //Comprobar si ya existe una tarea con el nombre del evento para no duplicarla
                if (!existeTarea(evento.getSummary())) {

                    //Generar una tarea a partir del a partir del Evento
                    titulo = evento.getSummary();

                    String fecha = "";

                    if (!(evento.getStart().getDateTime() == null)) {
                        fecha = evento.getStart().getDateTime().toString();
                        fecha = fecha.replace("T", " ");
                        fecha = fecha.substring(0, 19);

                        fechaInicio = Timestamp.valueOf(fecha);
                    } else {
                        Timestamp ahora = new Timestamp(System.currentTimeMillis());

                        fechaInicio = ahora;

                    }

                    if (!(evento.getEnd().getDateTime() == null)) {
                        fecha = evento.getEnd().getDateTime().toString();
                        fecha = fecha.replace("T", " ");
                        fecha = fecha.substring(0, 19);

                        fechaFin = Timestamp.valueOf(fecha);
                    } else {
                        Timestamp ahora = new Timestamp(System.currentTimeMillis());

                        fechaFin = ahora;

                    }

                    contexto = entradaCalendario.getSummary();
                    descripcion = evento.getDescription();

                    //Generamos los campos a partir de la descripción
                    //System.out.println(descripcion);
                    TareaAgenda tareaImportada = null;

                    String[] campos = null;

                    if (descripcion != null) {
                        campos = descripcion.split("\\|");

                        if (campos.length == 1) {
                            anotacion = campos[0];

                            //Generamos una tarea
                            tareaImportada = new TareaAgenda(fechaFin, fechaInicio, contexto, Complejidad.Baja, anotacion, titulo);
                        } else if (campos.length == 2) {
                            anotacion = campos[0];
                            cadenaComplejidad = campos[1];
                            cadenaComplejidad = cadenaComplejidad.trim();
                            complejidad = Complejidad.valueOf(cadenaComplejidad);

                            tareaImportada = new TareaAgenda(fechaFin, fechaInicio, contexto, complejidad, anotacion, titulo);

                            //RutaFichero
                        }

                    } else {
                        anotacion = "";
                        complejidad = Complejidad.Baja;

                        tareaImportada = new TareaAgenda(fechaFin, fechaInicio, contexto, complejidad, anotacion, titulo);

                    }

                    //Añade la tarea generada al repositorio
                    agenda.add(tareaImportada);
                    try {
                        System.out.println(titulo);
                        TareaEntrada temp = new TareaEntrada(titulo);
                        agregarTareasEntradaBD(temp);
                        TareaSimple aux = new TareaSimple(tareaImportada.getContexto(), tareaImportada.getMiComplejidad(),
                                tareaImportada.getAnotacion(), tareaImportada.getNombre(), false);
                        agregarTareasSimplesBD(aux);
                        agregarTareasAgendaBD(tareaImportada);

                    } catch (SQLException ex) {
                        Logger.getLogger(Repositorio.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                //Reseteamos la lista de eventos
                eventos = null;
            }
        }

    }
*/

    //Comprueba si existe una tarea en el repositorio con el nombre indicado
    private boolean existeTarea(String nombre) {
        boolean yaExiste = false;

        Iterator<TareaAgenda> miIterador = agenda.iterator();
        while (miIterador.hasNext() && !yaExiste) {
            if (miIterador.next().getNombre().equals(nombre)) {
                yaExiste = true;
            }
        }

        return yaExiste;
    }

}
