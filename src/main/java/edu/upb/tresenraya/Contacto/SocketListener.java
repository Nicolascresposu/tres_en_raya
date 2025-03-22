/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.Contacto;
import edu.upb.tresenraya.server.SocketClient;

/**
 *
 * @author Usuario
 */
public interface SocketListener {
    void onNewClient(SocketClient sc);
    void removeClient(SocketClient sc);
    
}