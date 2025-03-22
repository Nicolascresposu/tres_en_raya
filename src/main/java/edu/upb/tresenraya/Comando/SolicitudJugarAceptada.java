/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.Comando;

/**
 *
 * @author nicol
 */
public class SolicitudJugarAceptada extends Comando{
    String comando = "0006";

    @Override
    public void parsear(String comando) {
        throw new UnsupportedOperationException("No es necesario para un 0006."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getComando() {
        return comando;
    }
}
