/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.server;

import edu.upb.tresenraya.Comando.AceptacionConexion;
import edu.upb.tresenraya.Comando.Comando;
import edu.upb.tresenraya.Comando.MarcarSimbolo;
import edu.upb.tresenraya.Comando.NuevaPartida;
import edu.upb.tresenraya.Comando.RechazoConexion;
import edu.upb.tresenraya.Comando.SolicitudConexion;
import edu.upb.tresenraya.mediador.Mediador;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author rlaredo
 */
public class SocketClient extends Thread {

    private final Socket socket;
    private final String ip;
    private final DataOutputStream dout;
    private final BufferedReader br;

    public SocketClient(Socket socket) throws IOException {
        this.socket = socket;
        this.ip = socket.getInetAddress().getHostAddress();
        dout = new DataOutputStream(socket.getOutputStream());
        br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            String message;
            while ((message = br.readLine()) != null) {
                if (message.contains("0001")) {
                    Comando c = new SolicitudConexion();
                    Mediador.sendMessage(c);
                }
                if (message.contains("0002")) {
                    Comando c = new RechazoConexion();
                    Mediador.sendMessage(c);
                }
                if (message.contains("0003")) {
                    Comando c = new AceptacionConexion();
                    Mediador.sendMessage(c);    
                }
                
                if (message.contains("0007")) {
                    Comando c = new NuevaPartida();
                    Mediador.sendMessage(c);
                }
                
                if (message.contains("0008")) {
                    Comando c = new MarcarSimbolo(message);
                    Mediador.sendMessage(c);
                }
                
                if (message.equals("leave")) {
                    System.exit(0);
                }
                if (message.equals("verde")) {
                    Mediador.onButtonGreen();
                }
                if (message.equals("cerrar")) {
                    Mediador.onClose();
                    return;
                } else {
                    Mediador.sendMessage(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void send(byte[] buffer) {
        try {
            dout.write(buffer);
            dout.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        SocketClient socketClient;
//        if (args.length>0) {
//            socketClient = new SocketClient(new Socket(args[0], 1825));
//        } else {
            socketClient = new SocketClient(new Socket("localhost", 1825));
//        }
        socketClient.start();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("Escriba un mensaje: ");
            socketClient.send((br.readLine() + System.lineSeparator()).getBytes());

        }
    }
}
