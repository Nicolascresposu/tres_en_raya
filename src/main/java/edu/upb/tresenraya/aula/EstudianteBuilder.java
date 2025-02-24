/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.aula;

/**
 *
 * @author nicol
 */
public class EstudianteBuilder {
    private Estudiante estudiante;
    
    public EstudianteBuilder() {
        this.estudiante = new Estudiante();
    }
    
    public EstudianteBuilder nombre(String nombre) {
        this.estudiante.setNombre(nombre);
        return this;
    }
    public Estudiante build() {
        return this.estudiante;
    }
    public static EstudianteBuilder builder() {
        return new EstudianteBuilder();
    }
    
}
