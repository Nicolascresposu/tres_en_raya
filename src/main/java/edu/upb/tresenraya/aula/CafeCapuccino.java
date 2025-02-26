/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya;

/**
 *
 * @author nicol
 */
public class CafeCapuccino implements CafeInterface{
    String preparacion;

    public CafeCapuccino(String preparacion) {
        this.preparacion = preparacion;
    }

    @Override
    public CafeInterface preparar(String name) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
