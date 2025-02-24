/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.aula;

/**
 *
 * @author nicol
 */
public class Receptaculo implements Enviador {

    @Override
    public void onMessage(String message) {
        System.out.println("Mensaje:" + message);
    }

    @Override
    public void onClose() {
        System.out.println("Se ha solicitado cerrar");
    }
        
}
