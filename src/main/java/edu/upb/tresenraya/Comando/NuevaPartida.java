/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.Comando;

/**
 *
 * @author nicol
 */
public class NuevaPartida extends Comando{
    public NuevaPartida() {
        this.comando="0007";
    }
    public void parsear(String comando) {
        //Todo esto es lo mismo que nada lol
        if (comando.matches("0007.*")) {
            return;
        }
        return;
    }

    @Override
    public String getComando() {
        return comando;
    }
    
}
