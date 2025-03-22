/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.server;

import edu.upb.tresenraya.Comando.AceptacionConexion;
import edu.upb.tresenraya.Comando.CerrarYBorrar;
import edu.upb.tresenraya.Comando.Comando;
import edu.upb.tresenraya.Comando.GodmodeMarcarSimbolo;
import edu.upb.tresenraya.Comando.MarcarSimbolo;
import edu.upb.tresenraya.Comando.NuevaPartida;
import edu.upb.tresenraya.Comando.RechazoConexion;
import edu.upb.tresenraya.Comando.SolicitudConexion;
import edu.upb.tresenraya.Comando.SolicitudIniciarJuego;
import edu.upb.tresenraya.Comando.SolicitudJugarAceptada;
import edu.upb.tresenraya.Comando.SolicitudJugarRechazada;
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
    //Quiza deberia volverlo a hacer privado?
    public DataOutputStream dout;
    public BufferedReader br;

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
                Mediador.sendMessage(message);
                if (message.contains("0001")) {
                    Comando c = new SolicitudConexion(message.split("\\|")[1]);
                    c.setIp(this.ip);
                    Mediador.sendMessage(c);
                    continue;
                }
                if (message.contains("0002")) {
                    Comando c = new RechazoConexion();
                    c.setIp(this.ip);
                    Mediador.sendMessage(c);
                    continue;
                }
                if (message.contains("0003")) {
                    Comando c = new AceptacionConexion(message.split("\\|")[1]);
                    c.setIp(this.ip);
                    // To-do anadir guardado de nombre desde el message que se recibe
                    Mediador.sendMessage(c);    
                    continue;
                }
                if (message.contains("0004")) {
                    Comando c = new SolicitudIniciarJuego(message.split("\\|")[1]);
                    c.setIp(this.ip);
                    Mediador.sendMessage(c);    
                    continue;
                }
                if (message.contains("0005")) {
                    Comando c = new SolicitudJugarRechazada();
                    c.setIp(this.ip);
                    Mediador.sendMessage(c);    
                    continue;
                }
                if (message.contains("0006")) {
                    Comando c = new SolicitudJugarAceptada();
                    c.setIp(this.ip);
                    Mediador.sendMessage(c);    
                    continue;
                }
                
                if (message.contains("0007")) {
                    Comando c = new NuevaPartida();
                    c.setIp(this.ip);
                    Mediador.sendMessage(c);
                    continue;
                }
                
                if (message.contains("0008")) {
                    Comando c = new MarcarSimbolo(message);
                    c.setIp(this.ip);
                    Mediador.sendMessage(c);
                    continue;
                }
//                if (message.contains("0009")) {
//                    Comando c = new MarcarPartida();
//                    c.setIp(this.ip);
//                    Mediador.sendMessage(c);
//                }
                if (message.contains("0010")) {
                    Comando c = new CerrarYBorrar(message);
                    c.setIp(this.ip);
                    Mediador.sendMessage(c);
                    continue;
                }
                if (message.contains("0011")) {
                    Comando c = new GodmodeMarcarSimbolo(message);
                    c.setIp(this.ip);
                    Mediador.sendMessage(c);
                    continue;
                }
                
                if (message.equals("leave")) {
                    System.exit(0);
                    continue;
                }
                if (message.equals("verde")) {
                    Mediador.onButtonGreen();
                    continue;
                }
                if (message.equals("cerrar")) {
                    Mediador.onClose();
//                    continue;
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

    public String getIp() {
        return ip;
    }
    public synchronized void closeConnection() {
        try {
            if (dout != null) {
                dout.close();
            }
            if (br != null) {
                br.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close(); // Close the socket
            }
            System.out.println("Connection closed for IP: " + ip);
//            System.exit(0);
        } catch (IOException e) {
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
