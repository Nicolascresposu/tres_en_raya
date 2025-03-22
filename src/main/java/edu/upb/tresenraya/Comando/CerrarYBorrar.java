/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.Comando;

/**
 *
 * @author nicol
 */
public class CerrarYBorrar extends Comando {
    public String ip;
    public String nombre;
    public CerrarYBorrar(String comando) {
        parsear(comando);
    }

    @Override
    public void parsear(String comando) {
        if (comando.matches("0010\\|.*")) {
            String[] split = comando.split("\\|");
            this.nombre = split[1];
            System.out.println("CerrarYBorrar Parseado");
            return;
        } else {
            System.out.println("El comando tenia una forma erronea. Nombre permanecera nulo");
            return;
        }
            
    }

    @Override
    public String getComando() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
