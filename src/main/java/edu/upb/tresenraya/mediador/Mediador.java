/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.mediador;

import edu.upb.tresenraya.Comando.Comando;
import edu.upb.tresenraya.TresEnRayaUI;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class Mediador {
    public static List<OnMessageListener> lister = new ArrayList<>();
    
    public Mediador(){
        
    }
    
    public static void addListener(OnMessageListener messageLister){
       lister.add(messageLister);
    }
    
    public static void sendMessage(String msg){
        for (OnMessageListener onMessageLister : lister) {
           
            java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                onMessageLister.onMessage(msg);
                onMessageLister.onMessage(msg);
                
            }
        });
            
        }
    }
    public static void onClose(){
         for (OnMessageListener onMessageLister : lister) {
            onMessageLister.onClose();
        } 
   }
    public static void onButtonGreen(){
        for (OnMessageListener onMessageLister : lister) {
            onMessageLister.onButtonGreen();
        }
    }
    
    public static void sendMessage(Comando msg){
        for (OnMessageListener onMessageLister : lister) {
           
            java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                onMessageLister.onMessage(msg);
                onMessageLister.onMessage(msg);
                
            }
        });
            
        }
    }
    
    
}
