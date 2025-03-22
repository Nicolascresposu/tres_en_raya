/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.aula;

import edu.upb.tresenraya.Contacto.MyCollection;
import edu.upb.tresenraya.Comando.NuevaPartida;
import edu.upb.tresenraya.Comando.SolicitudConexion;

/**
 *
 * @author nicol
 */
public class CollectionTestClass {
    public static void main(String[] args) {
        MyCollection myc = new MyCollection();
//        myc.addItem(0);
//        myc.addItem(1);
//        myc.addItem(2);
//        myc.addItem(3);
//        myc.addItem(4);
//        myc.addItem(5);
//        myc.addItem(6);
//        myc.addItem("siete");
//        myc.addItem("ocho");
//        myc.addItem("nueve");
//        myc.addItem("diez");
//        System.out.println(myc.hasNext());
//        for (int i=0;i<11;i++) {
//            System.out.println(myc.getNext());
//        }
//        System.out.println("El siguiente deberia ser vacio:");
//        System.out.println(myc.hasNext());
//        System.out.println(myc.getNext());
        myc.addItem(new SolicitudConexion());
        myc.addItem(new NuevaPartida());
        System.out.println(myc.getNext());
        System.out.println(myc.getNext());
        
        
    }
}
