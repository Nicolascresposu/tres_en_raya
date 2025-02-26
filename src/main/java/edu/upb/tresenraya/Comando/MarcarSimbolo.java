/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.Comando;

/**
 *
 * @author nicol
 */
public class MarcarSimbolo extends Comando {

    public String simbolo;
    public int valorX;
    public int valorY;
    public MarcarSimbolo(String comando) {
        parsear(comando);
    }
    @Override
    public void parsear(String comando) {
        if (comando.matches("0008\\|.\\|.\\|.")) {
            String[] split = comando.split("\\|");
            this.comando = split[0];
            this.simbolo = split[1];
            this.valorX = Integer.valueOf(split[2]);
            this.valorY = Integer.valueOf(split[3]);
            return;
        }
        System.out.println("El comando tenia una forma erronea.");
        return;
    }

    @Override
    public String getComando() {
        return comando+"|"+simbolo+"|"+valorX+"|"+valorY;
    }
    
    
}
