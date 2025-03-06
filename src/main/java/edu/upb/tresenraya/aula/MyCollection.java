/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.aula;

import java.util.ArrayList;

/**
 *
 * @author nicol
 */
public class MyCollection implements PatronIterator {

    private int index;
    private ArrayList lista;
    public MyCollection() {
        this.index=0;
        this.lista = new ArrayList();
    }
    @Override
    public boolean hasNext() {
        return index<lista.size();
    }

    @Override
    public Object getNext() {
        if (hasNext()) {    
            Object current = lista.get(index);
            index++;
            return current;
        }
        return null;
    }
    
    
    
    public void addItem(Object T) {
        lista.add(T);
    }
    public static void main(String[] args) {
        MyCollection myc = new MyCollection();
        myc.addItem(0);
        System.out.println(myc.hasNext());
        System.out.println(myc.getNext());
        
    }
}
