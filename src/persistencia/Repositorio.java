package persistencia;

import administradorDeTareas.Complejidad;
import administradorDeTareas.Proyecto;
import administradorDeTareas.TareaAgenda;
import administradorDeTareas.TareaEntrada;
import administradorDeTareas.TareaInmediata;
import administradorDeTareas.TareaProyecto;
import administradorDeTareas.TareaSimple;
import administradorDeTareas.Usuario;
//import GoogleCalendar.CalendarioIO;
import administradorDeTareas.Prioridad;
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
    private ArrayList<Proyecto> proyectos;
    private ArrayList<Usuario> usuarios;
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
    public static Repositorio getInstancia() throws SQLException {
        if (instancia == null) {
            instancia = new Repositorio();
        }
        return instancia;

    }

    //Constructor
    private Repositorio() throws SQLException {
        //Conectar con base de datos e inicializar todos los conjuntos.
        accesoBD = new AccesoBD();
        contextos = new ArrayList<>();
        agenda = new ArrayList<>();
        bandejaEntrada = new ArrayList<>();
        tareasInmediatas = new ArrayList<>();
        proyectos = new ArrayList<>();
        usuarios = new ArrayList<>();
        archivoSeguimiento = new ArrayList<>();
        archivoConsulta = new ArrayList<>();
        accionesSiguientes = new ArrayList<>();
        tareasFinalizada = new ArrayList<>();
        tareasSimples = new ArrayList<>();

        cargarTareasEntrada();
        cargarTareasAgenda();
        cargarTareasInmediatas();
        cargarTareasFinalizadas();
        cargarTareasSimples();

    }

    public ArrayList<String> getContextos() {
        return contextos;
    }

    public ArrayList<TareaInmediata> getTareasInmediatas() {
        return tareasInmediatas;
    }

    public ArrayList<Proyecto> getProyectos() {
        return proyectos;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
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

    public void agregarEnProyectos(Proyecto nuevoProyecto) {
        proyectos.add(nuevoProyecto);
    }

    public void agregarEnUsuarios(Usuario nuevoUsuario) {
        usuarios.add(nuevoUsuario);
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
    
    public void agregarEnFinalizadas(TareaEntrada nuevaTarea){
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
    public void agregarTareaEnProyecto(TareaProyecto nuevaTarea, Proyecto seleccionado) {
        //seleccionado.agregarTarea(nuevaTarea);
    }

    public void quitarEnAgenda(TareaAgenda nuevaTarea) {
        agenda.remove(nuevaTarea);
    }

    public void quitarEnBandeja(TareaEntrada nuevaTarea) {
        bandejaEntrada.remove(nuevaTarea);
    }

    public void quitarEnInmediatas(TareaSimple nuevaTarea) {
        tareasInmediatas.remove(nuevaTarea);
    }

    public void quitarEnProyectos(Proyecto nuevoProyecto) {
        proyectos.remove(nuevoProyecto);
    }

    public void quitarEnUsuarios(Usuario nuevoUsuario) {
        usuarios.remove(nuevoUsuario);
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

    /**
     * Quita una tarea a un proyecto, si es que la contiene.
     *
     * @param nuevaTarea
     * @param seleccionado
     */
    public void quitarTareaEnProyecto(TareaProyecto nuevaTarea, Proyecto seleccionado) {
        //seleccionado.quitarTarea(nuevaTarea);
    }

    /**
     * Author Álvaro Luque Jiménez
     *
     * @param contrasena
     * @param nick
     * @param email
     * @throws SQLException
     */
    public void insertarUsuario(String contrasena, String nick, String email) throws SQLException {

        Statement stm = accesoBD.abrirConexion().createStatement();

        String insertar = "insert into Usuario values('" + nick + "','" + contrasena + "','" + email + "')";

        stm.execute(insertar);

        accesoBD.cerrarConexion(accesoBD.abrirConexion());

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

    public void procesarTarea(TareaEntrada aProcesar, TareaSimple procesada) throws SQLException {
        if (!aProcesar.getNombre().equals(procesada.getNombre())) {
            throw new IllegalArgumentException("Las tareas no tienen el mismo nombre");
        } //Sí coinciden los nombres
        else {
            quitarEnBandeja(aProcesar);
            //Comprobamos el tipo
            if (procesada instanceof TareaAgenda) {       
                agregarEnAgenda((TareaAgenda) procesada);
                agregarTareasAgendaBD((TareaAgenda)procesada);
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

            System.out.println(nombre);
            
            nuevaTarea = new TareaAgenda(Timestamp.valueOf(fechafin), Timestamp.valueOf(fechainicio), contexto, Complejidad.valueOf(complejidad), anotacion, nombre);

            agregarEnAgenda(nuevaTarea);
            /*System.out.println(nuevaTarea.getNombre());
            System.out.println(nuevaTarea.getAnotacion());
            System.out.println(nuevaTarea.getContexto());
            System.out.println(nuevaTarea.getMiComplejidad());
            System.out.println(nuevaTarea.getNombre());
            System.out.print(nuevaTarea.getId());*/
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

            System.out.println(nombre);
            
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
    public void agregarTareasEntradaBD(TareaEntrada tareaEntradaAgregar, String usuarioConectado) throws SQLException {

        Connection con = accesoBD.abrirConexion();

        Statement stm = con.createStatement();

        stm.executeUpdate("INSERT INTO TareaEntrada (nombre, nickUsuario) VALUES('" + tareaEntradaAgregar.getNombre() + "', '" + usuarioConectado + "');");

        accesoBD.cerrarConexion(con);

    }
    
    /**
     *
     * @param tareaEntradaEliminar
     * @throws SQLException
     *
     * Author Álvaro Luque Jiménez
     */
    public void quitarTareasEntradaBD(TareaEntrada tareaEntradaEliminar) throws SQLException {

        Connection con = accesoBD.abrirConexion();

        Statement stm = con.createStatement();

        stm.executeUpdate("DELETE FROM TareaEntrada WHERE nombre = \"" + tareaEntradaEliminar.getNombre() + "\"");

        accesoBD.cerrarConexion(con);

    }

    public void agregarTareasAgendaBD(TareaAgenda tareaAgenda) throws SQLException {
        
        Connection con = accesoBD.abrirConexion();

        Statement stm = con.createStatement();

        stm.executeUpdate("INSERT INTO TareaDeAgenda VALUES(\"" + tareaAgenda.getNombre() + "\", \"" + tareaAgenda.getFechaInicio() + "\", \"" + tareaAgenda.getFechaFin() + "\")");

        accesoBD.cerrarConexion(con);

    }
    
    public void quitarTareasAgendaBD(TareaAgenda tareaAgendaEliminar) throws SQLException {

        Connection con = accesoBD.abrirConexion();

        Statement stm = con.createStatement();

        stm.executeUpdate("DELETE FROM TareaDeAgenda WHERE nombre = \"" + tareaAgendaEliminar.getNombre() + "\"");

        accesoBD.cerrarConexion(con);

    }
    
    public void agregarTareasSimplesBD(TareaSimple tareaSimple) throws SQLException {
        
        Connection con = accesoBD.abrirConexion();

        Statement stm = con.createStatement();

        stm.executeUpdate("INSERT INTO TareaSimple VALUES('" + tareaSimple.getNombre() + "', '" + tareaSimple.getAnotacion() + "', '" + tareaSimple.getMiComplejidad() + "', " + tareaSimple.isDelegada() + ", '" + tareaSimple.getContexto() + "')");

        accesoBD.cerrarConexion(con);

    }
    
    public void agregarTareasInmediatasBD(TareaInmediata tareaInmediata) throws SQLException {
        
        Connection con = accesoBD.abrirConexion();

        Statement stm = con.createStatement();

        stm.executeUpdate("INSERT INTO TareaInmediata VALUES('" + tareaInmediata.getNombre() + "', " + tareaInmediata.isTerminada() + ")");

        accesoBD.cerrarConexion(con);

    }

     /**
     *
     * @param tareaEntradaFinalizada
     * @throws SQLException
     *
     * Author Pedro A Alcantara
     * Author Jesús Budía
     */
    public void moverAFinalizadas(TareaEntrada tareaEntradaFinalizada) throws SQLException {
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
            
            
            
        } else  if(tareaEntradaFinalizada instanceof TareaInmediata){
            
            Connection con = accesoBD.abrirConexion();

            Statement stm = con.createStatement();

            stm.executeUpdate("INSERT INTO Finalizada (nombre) VALUES(\"" + tareaEntradaFinalizada.getNombre() + "\")");
            
            stm.executeUpdate("DELETE FROM TareaEntrada WHERE nombre = \"" + tareaEntradaFinalizada.getNombre() + "\"");

            accesoBD.cerrarConexion(con);
            
        }
    }
    
    public void exportarEventos() throws IOException {
        String descripcion = "";

        for (TareaAgenda tarea : agenda) {
            descripcion = tarea.getMiComplejidad() + "|" + tarea.getAnotacion();
            //CalendarioIO.crearEvento(tarea.getNombre(), tarea.getContexto(), descripcion, tarea.getFechaInicio(), tarea.getFechaFin());
        }
    }
    
   

}
