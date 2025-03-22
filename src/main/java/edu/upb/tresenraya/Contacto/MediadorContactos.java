/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.Contacto;

import edu.upb.tresenraya.server.SocketClient;
//import edu.upb.tresenraya.utils.MyCollection;

/**
 *
 * @author Usuario
 */
public class MediadorContactos {

    private final MyCollection<SocketListener> lister = new MyCollection<>();
    private static MediadorContactos instance = new MediadorContactos();

    private MediadorContactos() {
    }

    public static MediadorContactos geInstance() {
        return instance;
    }

    public void addListener(SocketListener sl) {
        lister.add(sl);
    }

    public void removeCliente(SocketClient sl) {
        lister.reset();
        while (lister.hasNext()) {
            SocketListener listener = lister.getNext();
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    listener.removeClient(sl);
                }
            });
            
        }

    }

    public void newClient(SocketClient msg) {
        lister.reset();
        while (lister.hasNext()) {
            SocketListener listener = lister.getNext();
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    listener.onNewClient(msg);
                }
            });
            
        }
    }
}