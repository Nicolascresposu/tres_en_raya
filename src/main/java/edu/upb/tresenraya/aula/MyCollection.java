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

    public int index=0;
    public ArrayList lista;
    @Override
    public boolean hasNext() {
        if (index<lista.size()) {
            return true;
        }
        return false;
    }

    @Override
    public Object getNext() {
        if (hasNext()) {
            index++;
        return lista.get(index);
        }
        return null;
    }
    
    
    
    public void addItem(Object T) {
        lista.add(T);
    }
    
}
