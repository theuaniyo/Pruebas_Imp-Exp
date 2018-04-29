package pruebas_integracion.persistencia;

import pruebas_integracion.administradorDeTareas.Complejidad;
import pruebas_integracion.administradorDeTareas.Proyecto;
import pruebas_integracion.administradorDeTareas.TareaAgenda;
import pruebas_integracion.administradorDeTareas.TareaEntrada;
import pruebas_integracion.administradorDeTareas.TareaInmediata;
import pruebas_integracion.administradorDeTareas.TareaProyecto;
import pruebas_integracion.administradorDeTareas.TareaSimple;
import pruebas_integracion.administradorDeTareas.Usuario;
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
    private ArrayList<TareaInmediata> tareasInmediatas;
    private ArrayList<Proyecto> proyectos;
    //Que de error al agregar un usuario si el nick ya está pillado.
    private ArrayList<Usuario> usuarios;
    
    
    
    private ArrayList<TareaEntrada> papelera;
    
    //Algún día  tal vez
    private ArrayList<TareaEntrada> archivoSeguimiento;
    private ArrayList<TareaEntrada> archivoConsulta;
    
    
    private ArrayList<TareaSimple> accionesSiguientes;
    
    
    private static Repositorio instancia = null;
     
    
    
    //Devuelve la instancia, necesario por usar el patrón de diseño Singleton. 
    public static Repositorio getInstancia () {
        if (instancia == null) {
            instancia = new Repositorio();
        }
        return instancia;
        
    }
    
    
    //Constructor
    private Repositorio() {
        //Conectar con base de datos e inicializar todos los conjuntos.
        contextos = new ArrayList<>();
        agenda = new ArrayList<>();
        bandejaEntrada = new ArrayList<>();
        tareasInmediatas = new ArrayList<>();;
        proyectos = new ArrayList<>();
        usuarios = new ArrayList<>();
        archivoSeguimiento = new ArrayList<>();
        archivoConsulta = new ArrayList<>();
        accionesSiguientes = new ArrayList<>();
       
    }

    public ArrayList<String> getContextos() {
        return contextos;
    }

    public ArrayList<TareaAgenda> getAgenda() {
        return agenda;
    }

    public ArrayList<TareaEntrada> getBandejaEntrada() {
        return bandejaEntrada;
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

    public ArrayList<TareaEntrada> getArchivoConsulta() {
        return archivoConsulta;
    }

    public ArrayList<TareaSimple> getAccionesSiguientes() {
        return accionesSiguientes;
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
    
    public void agregarEnPapelera(TareaEntrada nuevaTarea) {
        papelera.add(nuevaTarea);
    }
    
    /**
     * Agrega una tarea a un proyecto.
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
     * @param nuevaTarea 
     * @param seleccionado 
     */
    public void quitarTareaEnProyecto(TareaProyecto nuevaTarea, Proyecto seleccionado) {
        //seleccionado.quitarTarea(nuevaTarea);
    }
    
    public void procesarTarea(TareaEntrada aProcesar, TareaSimple procesada) {
        if (!aProcesar.getNombre().equals(procesada.getNombre() )  ) {
            throw new IllegalArgumentException ("Las tareas no tienen el mismo nombre");
        }
        //Sí coinciden los nombres
        else {
            quitarEnBandeja(aProcesar);
            //Comprobamos el tipo
            if (procesada instanceof TareaAgenda ) {
                agregarEnAgenda((TareaAgenda) procesada);
            }
            else if(procesada instanceof TareaInmediata){
                agregarEnInmediatas((TareaInmediata) procesada);
            }
            else{
                agregarEnSiguientes(procesada);
            }
            }
            ////Problemática también debe de poderse procesar y que vaya a LA LISTA tarea inmediatas. SOLUCIONAR
    }
    
    public void procesarTarea(TareaEntrada aProcesar, String listaDestino){
        
        if(listaDestino.equals("Papelera")){
            agregarEnPapelera(aProcesar);
        }else if(listaDestino.equals("Archivo seguimiento")){
            agregarEnSeguimiento(aProcesar);
        }else if(listaDestino.equals("Archivo consulta")){
            agregarEnConsulta(aProcesar);
        }
        
        quitarEnBandeja(aProcesar);
        
        
    }
    
    
    
    public void getTareasSimples() throws SQLException{
        
        String id = "";
        String nombre = "";
        String anotacion = "";
        String complejidad = "";
 
        String contexto = "";
        
        TareaSimple nuevaTarea = null;
        
        Connection con = AccesoBD.abrirConexion();
        
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery ("SELECT codigo, nombre,"
        + "anotacion, complejidad, requisitos, contexto FROM TareaSimple");
        
        while (rs.next()){
            id = rs.getString("codigo");
            nombre = rs.getString ("nombre");
            anotacion = rs.getString ("anotacion");
            complejidad = rs.getString ("complejidad");
   
            contexto = rs.getString ("contexto");
            
            nuevaTarea = new TareaSimple(contexto,Complejidad.valueOf(complejidad), anotacion, nombre, Integer.parseInt(id));
            
            agregarEnSiguientes(nuevaTarea);
            System.out.println(nuevaTarea.getNombre());
            System.out.println(nuevaTarea.getDescripcion());
            System.out.println(nuevaTarea.getContexto());
            System.out.println(nuevaTarea.getMiComplejidad());
            System.out.println(nuevaTarea.getNombre());
            System.out.print(nuevaTarea.getId());
        }
        
        AccesoBD.cerrarConexion(con);        
        
    }
    
    
    
}
     

