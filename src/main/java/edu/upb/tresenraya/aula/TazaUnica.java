/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.aula;

/**
 *
 * @author nicol
 */
public class TazaUnica {
    String test = "hola!";
    private static TazaUnica instancia = new TazaUnica();
    private TazaUnica() {
    }
    public static TazaUnica getInstancia() {
        return TazaUnica.instancia;
    }
    
    
    
    
    public static void main(String[] args) {
        System.out.println(TazaUnica.getInstancia().test);
    }
}
