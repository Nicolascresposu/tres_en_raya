/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.Comando;

/**
 *
 * @author nicol
 */
public class SolicitudConexion extends Comando{
//    private String comando = "0001";
//    private String nombre = "NICOLAS CRESPO";
    public String nombre;
    public SolicitudConexion(String nombre) {
        this.nombre = nombre;
        this.comando = "0001";
    }

    @Override
    public void parsear(String comando) {
        if (comando.matches("0001\\|.*")) {
            String[] split = comando.split("\\|");
            this.comando = split[0];
            this.nombre = split[1];
            return;
        }
        System.out.println("El comando tenia una forma erronea.");
        return;
    }

    @Override
    public String getComando() {
        return comando+"|"+nombre+System.lineSeparator();
    }
    
}
