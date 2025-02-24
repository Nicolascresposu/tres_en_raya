/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.aula;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nicol
 */
public class MediadorPrueba {
    // esto es para tener una lista con todas las cosas a las que se lo vamos a enviar
    public static List<Enviador> listaDeOyentes = new ArrayList<>();
    
    public MediadorPrueba() {
        
    }
    //Para aumentar a la lista
    public static void addListener(Enviador oyenteInstancia){
       listaDeOyentes.add(oyenteInstancia);
    }
    
    public static void sendMessage(String msg){
        for (Enviador oyenteInstancia : listaDeOyentes) {
           //Le dice a cada uno de la lista que corran la funcion onMessage con el argumento msg
            oyenteInstancia.onMessage(msg);
        }
    }
    public static void onClose(){
         for (Enviador oyenteInstancia : listaDeOyentes) {
            oyenteInstancia.onClose();
        } 
   }
}
