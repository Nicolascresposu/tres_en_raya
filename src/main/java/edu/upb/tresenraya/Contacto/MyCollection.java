/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.Contacto;

import edu.upb.tresenraya.aula.PatronIterator;
import java.util.ArrayList;

/**
 *
 * @author nicol
 */
public class MyCollection<T> implements PatronIterator {

    private int index;
    private final ArrayList<T> lista = new ArrayList<>();
//    public MyCollection() {
//        this.index=0;
//        this.lista = new ArrayList();
//    }
    @Override
    public boolean hasNext() {
        return index<lista.size();
    }

    @Override
    public T getNext() {
        if (hasNext()) {    
            T current = lista.get(index);
            index++;
//            index++;
            return current;
        }
        return null;
    }
    
    public void setLast() {
        index = lista.size()-1;
    }
    public boolean hasPrevious() {
        return index>0;
    }
    public Object getPrevious() {
        if (hasPrevious()) {
            Object current = lista.get(index);
            index--;
            return current;
        }
        return null;
    }
    
    
    //Deprecated please dont use
    public void addItem(T contact) {
        lista.add(contact);
    }
    
    public static void main(String[] args) {
        MyCollection myc = new MyCollection();
        myc.addItem(0);
        System.out.println(myc.hasNext());
        System.out.println(myc.getNext());
        
    }
    public void reset() {
        index=0;
        return;
    }
    void add(T sl) {
        //Esta funcion es identica a addItem
        lista.add(sl);
    }
}
