/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.aula;

/**
 *
 * @author Usuario
 */
public abstract class Araña {
    private int cantidaOjos;
    private int tamaño;
    private String especie;
 
    public void generarTelaAraña(){
        System.out.println("Genera telaaraña");
    }
    public void pica(){
        System.out.println("Picó la araña");
    }

    public int getCantidaOjos() {
        return cantidaOjos;
    }

    public int getTamaño() {
        return tamaño;
    }

    public String getEspecie() {
        return especie;
    }

    public void setCantidaOjos(int cantidaOjos) {
        this.cantidaOjos = cantidaOjos;
    }

    public void setTamaño(int tamaño) {
        this.tamaño = tamaño;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }
    
    
}
