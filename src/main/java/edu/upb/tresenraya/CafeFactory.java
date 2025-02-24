/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya;

/**
 *
 * @author nicol
 */
public class CafeFactory {
    public static CafeInterface create(String name) {
        if (name.equals("capuccino")) {
            return new CafeCapuccino("1 cucharada de leche y 3 cucharadas de cafe");
        }
        if (name.equals("mocca")) {
            return new CafeMocca("2/3 leche y 1 cucharada de azucar");
        }
        return null;
    }

    
//    public String preparar() {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
    
}
