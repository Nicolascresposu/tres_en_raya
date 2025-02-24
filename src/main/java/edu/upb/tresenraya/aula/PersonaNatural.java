/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.aula;

/**
 *
 * @author Usuario
 */
public class PersonaNatural implements PersonaParaClaseDel6{
    private String name;
    private int edad;
    private boolean vivo;
    private String genero;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String hablar() {
        return "HOla como estan?";
    }

    @Override
    public boolean vivo() {
       return vivo;
    }

    @Override
    public String getGenero() {
        return this.genero;
    }

    @Override
    public void caminar() {
        System.out.println("Inicio la caminata");
    }
    
    public int getEdad(){
        return this.edad;
    }
    
}
