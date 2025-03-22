/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.Comando;

/**
 *
 * @author nicol
 */
public class RechazoConexion extends Comando{
    //Comentado porque lombok ya lo arma (???)
//    private String comando = "0002";
    
//    public String parsear(String comando) {
//        if (comando.matches("0002\\|.*")) {
//            return "El jugador"+comando.split("\\|")[1]+"ha rechazado la solicitud";
//        }
//        return "El otro jugador rechazo la conexion.";
//    }
    public RechazoConexion() {
        this.comando="0002";
    }
    public void parsear(String comando) {
        if (comando.matches("0002.*")) {
            return;
        }
        return;
    }

    @Override
    public String getComando() {
        return "0002";
    }
    
}
