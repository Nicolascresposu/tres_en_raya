/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.Comando;

/**
 *
 * @author nicol
 */
public class SolicitudIniciarJuego extends Comando{
    String simbolo;
    String comando = "0004";

    public SolicitudIniciarJuego(String simbolo) {
        this.simbolo = simbolo;
    }
    
    @Override
    public void parsear(String comando) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getComando() {
        return comando+"|"+simbolo;
    }
    
}
