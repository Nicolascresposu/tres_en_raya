/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.aula;

/**
 *
 * @author Usuario
 */
public class Hombre extends Araña{
    private int edad;
    private String nombre;
    private int peso;
    private int estatura;
    
    public Hombre(){
        super();
    }
    
    public void caminar(){
        System.out.println("Camina");
    }
    public void corre(){
        System.out.println("Corre!!!!!!");
    }
    
    public void hablar(){
        System.out.println("Habla");
    }

    public int getEdad() {
        return edad;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPeso() {
        return peso;
    }

    public int getEstatura() {
        return estatura;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public void setEstatura(int estatura) {
        this.estatura = estatura;
    }

    @Override
    public void generarTelaAraña() {
        System.out.println("Genera telaarraña desde su mano");
    }
    
    
    
}
