/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas_integracion.IEDatos;

import pruebas_integracion.administradorDeTareas.TareaAgenda;
import pruebas_integracion.administradorDeTareas.TareaEntrada;
import pruebas_integracion.administradorDeTareas.TareaSimple;
import java.util.ArrayList;
import pruebas_integracion.administradorDeTareas.Proyecto;

/**
 * Repositorio provisional del grupo de Importación/Exportación de datos para
 * pruebas de la clase IEDatos.
 *
 * @see IEDatos
 * @author Juan José Luque Morales
 */
public class RepoProvisional {

    /**
     * Lista de objetos TareaEntrada.
     *
     * @see TareaEntrada
     */
    private ArrayList<TareaEntrada> bandejaEntrada;

    /**
     * Lista de objetos TareaSimple.
     *
     * @see TareaSimple
     */
    private ArrayList<TareaSimple> listaTareasSimples;

    /**
     * Lista de objetos TareaAgenda.
     *
     * @see TareaAgenda
     */
    private ArrayList<TareaAgenda> agenda;
    /**
     * Lista de objetos Proyecto.
     *
     * @see Proyecto
     */
    private ArrayList<Proyecto> misProyectos;

    /**
     * Constructor del repositorio. Inicializa los ArrayList de la clase.
     */
    private RepoProvisional() {
        bandejaEntrada = new ArrayList<>();
        listaTareasSimples = new ArrayList<>();
        agenda = new ArrayList<>();
        misProyectos = new ArrayList<>();
    }

    /**
     *
     * @return lista de objetos TareaEntrada.
     */
    public ArrayList<TareaEntrada> getBandejaEntrada() {
        return bandejaEntrada;
    }

    /**
     *
     * @param bandejaEntrada lista de objetos TareaEntrada
     */
    public void setBandejaEntrada(ArrayList<TareaEntrada> bandejaEntrada) {
        this.bandejaEntrada = bandejaEntrada;
    }

    /**
     *
     * @return lista de objetos TareaSimple
     */
    public ArrayList<TareaSimple> getListaTareasSimples() {
        return listaTareasSimples;
    }

    /**
     *
     * @param listaTareasSimples lista de objetos TareaSimple
     */
    public void setListaTareasSimples(ArrayList<TareaSimple> listaTareasSimples) {
        this.listaTareasSimples = listaTareasSimples;
    }

    /**
     *
     * @return lista de objetos TareaAgenda
     */
    public ArrayList<TareaAgenda> getAgenda() {
        return agenda;
    }

    /**
     *
     * @param agenda lista de objetos TareaAgenda
     */
    public void setAgenda(ArrayList<TareaAgenda> agenda) {
        this.agenda = agenda;
    }

    /**
     *
     * @return lista de objetos proyecto
     */
    public ArrayList<Proyecto> getMisProyectos() {
        return misProyectos;
    }

    /**
     *
     * @param misProyectos lista de objetos Proyecto
     */
    public void setMisProyectos(ArrayList<Proyecto> misProyectos) {
        this.misProyectos = misProyectos;
    }

    /**
     *
     * @return instancia del repositorio.
     */
    public static RepoProvisional getInstance() {
        return RepoProvisionalHolder.INSTANCE;
    }

    /**
     * Contiene una instancia del repositorio
     */
    private static class RepoProvisionalHolder {

        /**
         * Instancia del repositorio.
         */
        private static final RepoProvisional INSTANCE = new RepoProvisional();
    }
}
