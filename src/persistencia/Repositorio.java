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
import java.util.ArrayList;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author JJBRZ
 */
public class Repositorio {

    private ArrayList<String> contextos;
    private ArrayList<TareaAgenda> agenda;
    private ArrayList<TareaEntrada> bandejaEntrada;
    private ArrayList<TareaSimple> tareasInmediatas;
    private ArrayList<Proyecto> proyectos;
    //Que de error al agregar un usuario si el nick ya está pillado.
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
    public static Repositorio getInstancia() {
        if (instancia == null) {
            instancia = new Repositorio();
        }
        return instancia;

    }

    //Constructor
    private Repositorio() {
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
        papelera = new ArrayList<>();

        //TareaEntrada tarea1 = new TareaEntrada("Hola");
        //TareaEntrada tarea2 = new TareaEntrada("Mundo");

        //TareaAgenda tareaA1 = new TareaAgenda(null, null, "blablab", "afsdfsdf", "unos", null, "TareaA1");
        //TareaAgenda tareaA2 = new TareaAgenda(null, null, "blablab", "afsdfsdf", "unos", null, "TareaA2");
        //bandejaEntrada.add(tarea1);
        //bandejaEntrada.add(tarea2);

        //agenda.add(tareaA1);
        //agenda.add(tareaA2);
    }

    public ArrayList<String> getContextos() {
        return contextos;
    }

    public ArrayList<TareaSimple> getTareasInmediatas() {
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

    public void agregarEnInmediatas(TareaSimple nuevaTarea) {
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

    public void insertarUsuario(String contrasena, String nick, String email) throws SQLException {

        Statement stm = accesoBD.abrirConexion().createStatement();

        String insertar = "insert into Usuario values('" + nick + "','" + contrasena + "','" + email + "')";

        stm.execute(insertar);

        accesoBD.cerrarConexion(accesoBD.abrirConexion());

    }

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

    public void procesarTarea(TareaEntrada aProcesar, TareaSimple procesada) {
        if (!aProcesar.getNombre().equals(procesada.getNombre())) {
            throw new IllegalArgumentException("Las tareas no tienen el mismo nombre");
        } //Sí coinciden los nombres
        else {
            quitarEnBandeja(aProcesar);
            //Comprobamos el tipo
            if (procesada instanceof TareaAgenda) {
                agregarEnAgenda((TareaAgenda) procesada);
            } else if (procesada instanceof TareaInmediata) {
                agregarEnInmediatas((TareaInmediata) procesada);
            } else {
                agregarEnSiguientes(procesada);
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

    public void getTareasSimples() throws SQLException {

        String id = "";
        String nombre = "";
        String anotacion = "";
        String complejidad = "";

        String contexto = "";

        TareaSimple nuevaTarea = null;

        Connection con = accesoBD.abrirConexion();

        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT codigo, nombre,"
                + "anotacion, complejidad, requisitos, contexto FROM TareaSimple");

        while (rs.next()) {
            id = rs.getString("codigo");
            nombre = rs.getString("nombre");
            anotacion = rs.getString("anotacion");
            complejidad = rs.getString("complejidad");

            contexto = rs.getString("contexto");

            nuevaTarea = new TareaSimple(contexto, Complejidad.valueOf(complejidad), anotacion, nombre, Integer.parseInt(id));

            agregarEnSiguientes(nuevaTarea);
            System.out.println(nuevaTarea.getNombre());
            System.out.println(nuevaTarea.getAnotacion());
            System.out.println(nuevaTarea.getContexto());
            System.out.println(nuevaTarea.getMiComplejidad());
            System.out.println(nuevaTarea.getNombre());
            System.out.print(nuevaTarea.getId());
        }

        accesoBD.cerrarConexion(con);

    }

    /*public void exportarEventos() throws IOException {
        String descripcion = "";

        for (TareaAgenda tarea : agenda) {
            descripcion = tarea.getId() + "|" + tarea.getMiComplejidad() + "|" + tarea.getAnotacion();
            CalendarioIO.crearEvento(tarea.getNombre(), tarea.getContexto(), descripcion, tarea.getFechaInicio(), tarea.getFechaFin());
        }
    }*/

}
