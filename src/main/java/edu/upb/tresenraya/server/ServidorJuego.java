/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.server;

import edu.upb.tresenraya.Comando.Comando;
import edu.upb.tresenraya.mediador.Mediador;
import edu.upb.tresenraya.mediador.OnMessageListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author rlaredo
 */
public class ServidorJuego extends Thread implements OnMessageListener{

    private final ServerSocket serverSocket;
    public SocketClient client;
    

    public ServidorJuego() throws IOException {
        this.serverSocket = new ServerSocket(1825);
        Mediador.addListener(this);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = this.serverSocket.accept();
                client = new SocketClient(socket);
                //client.addJLabel(enRayaUI.getLabel());
                client.start();
            } catch (IOException e) {
                
            }

        }

    }

    @Override
    public void onMessage(String msg) {
        System.out.println(msg);
    }

    @Override
    public void onClose() {
        System.out.println("Server: Cayo el cliente");
    }

    @Override
    public void onButtonGreen() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void onMessage(Comando c) {
        System.out.println(c.getComando());
    }
    

}
