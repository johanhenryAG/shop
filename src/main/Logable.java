/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

/**
 *
 * @author johan
 */
public interface Logable {
    /**
     * 
     * @param user número de usuario/empleado
     * @param password contraseña
     * @return
     */
    boolean login(int user, String password);
}
