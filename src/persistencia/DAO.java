/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

/**
 *
 * @author alumno
 */
public interface DAO {
    Object get(Object parametroBuscado);
    void create(Object parametro);
    void update(Object parametro);
    void delete(String userName);

    
}
