/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.aula;

/**
 *
 * @author nicol
 */
public class MainBuilder {
//    Estudiante.builder().nombre("AMINA").codigo("1234").build()
    
    public static void main(String[] args) {
        Universidad u = Universidad.builder().nombre("test").build();
        System.out.println(u);
    }
}
