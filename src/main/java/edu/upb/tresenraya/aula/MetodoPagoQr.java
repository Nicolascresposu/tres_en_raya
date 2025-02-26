/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.aula;

import java.math.BigDecimal;

/**
 *
 * @author nicol
 */
public class MetodoPagoQr extends MetodoPago{

    public MetodoPagoQr(String nombre) {
        super(nombre);
    }

    @Override
    protected String cobrar(BigDecimal monto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
