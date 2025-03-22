/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.Comando;

/**
 *
 * @author nicol
 */
public class AceptacionConexion extends Comando {
//    private String comando = "0003|NICOLAS CRESPO";
//    private String nombre = "NICOLAS CRESPO";
    public String nombre;
    public AceptacionConexion(String nombre) {
        this.nombre = nombre;
        this.comando = comando;
    }
    public AceptacionConexion() {    
    }
    
    @Override
    public void parsear(String comando) {
        if (comando.matches("0003\\|.*")) {
            String[] split = comando.split("\\|");
            this.comando = split[0];
            this.nombre = split[1];
            return;
        }
        System.out.println("El comando tenia una forma erronea.");
        return;
    }
//    public String parsear(String comando) {
//        if (comando.matches("0003\\|.*")) {
//            return "El jugador"+comando.split("\\|")[1]+"ha aceptado tu solicitud";
//        }
//        return "El comando de aceptacion recibido no tenia el formato correcto. Se recibio: "+comando;
//    }

    @Override
    public String getComando() {
        //Asi deberia funcionar getcomando
        return "0003"+"|"+nombre;
    }
    
}
