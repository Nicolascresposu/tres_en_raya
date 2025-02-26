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
public abstract class MetodoPago {
    private BigDecimal monto;
    private String nombre;
    protected abstract String cobrar(BigDecimal monto);
    public MetodoPago( String nombre) {
        this.nombre = nombre;
    }
//    public MetodoPago create(String name) {
//        if(name.equals("QR")) {
//            return new MetodoPagoQr();
//        }
////        return new MetodoPagoQr();
//        if (name.equals("Tarjeta")) {
//            return new MetodoPagoTarjeta();
////              y asi
//        }
//        return null;
//    }
//    public String cobrar(BigDecimal monto) {
//        return "QR";
//    public boolean cobrarTarjeta(BigDecimal monto, String pam, String pin) {
//        // con cybersource
//        return true;   
//    }
    
    
}
    
        
