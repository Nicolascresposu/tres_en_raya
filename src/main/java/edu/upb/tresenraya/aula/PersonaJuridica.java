/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.aula;

/**
 *
 * @author Usuario
 */
public class PersonaJuridica implements PersonaParaClaseDel6 {

    private String name;
    private int edad;
    private boolean vivo;
    private String genero;
    private boolean vigente;

    public boolean isVigente() {
        return vigente;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean vivo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getGenero() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void caminar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getEdad() {
        return this.edad;
    }

    @Override
    public String hablar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
