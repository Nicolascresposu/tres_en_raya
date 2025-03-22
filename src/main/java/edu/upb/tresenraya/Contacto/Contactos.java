/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.Contacto;
import edu.upb.tresenraya.server.SocketClient;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 *
 * @author nicol
 */
public class Contactos implements SocketListener {

    private final ConcurrentMap<String, SocketClient> contatos = new ConcurrentHashMap<>();
    private static final Contactos instance = new Contactos();
    private BigDecimal pagar;
    private String cuenta;


    private Contactos() {
        System.out.println("Construyendo Contacto");
       MediadorContactos.geInstance().addListener(this);
    }

    public static Contactos getInstance() {
        return instance;
    }

    @Override
    public  void removeClient(SocketClient sc){
       contatos.remove(sc.getIp());
    }
    public  void removeClient(String ip){
       contatos.remove(ip);
    }
    
    @Override
    public void onNewClient(SocketClient sc) {
        contatos.put(sc.getIp(), sc);
        System.out.println("Cantidad de contactos: " + contatos.size());
    }

//    public void send(String ip, String msg) {
//        System.out.println("Ip en Contactos.send es: "+ip);
//        if (ip==null) {
//            //bandaid because I am going to die
//            ip="127.0.0.1";
//            System.out.println("das ip ist schwerkodifiziert");
//        }
//        System.out.println("Enviando: \"" + msg + "\" a " +ip+ " a.k.a "+this.contatos.get(ip).getName());
//        SocketClient sc = this.contatos.get(ip);
//        if (sc != null) {
//            sc.send(msg.getBytes());
//        }
//    }
    public void send(String ip, String msg) {
        System.out.println("Ip en Contactos.send es: " + ip);

        // Check if the IP is null or empty
//        if (ip == null || ip.trim().isEmpty()) {
////            System.out.println("Error: IP is null or empty.");
////            return; // Exit the method to avoid NullPointerException
//            ip="127.0.0.1";
//        }

        // Retrieve the SocketClient from the map
        SocketClient sc = this.contatos.get(ip);

        // Check if the SocketClient exists
        if (sc == null) {
            System.out.println("Error: No SocketClient found for IP: " + ip);
            System.out.println("Current contacts in map: " + contatos.keySet());
            return; // Exit the method to avoid NullPointerException
        }

        // Send the message
        System.out.println("Enviando: \"" + msg + "\" a " + ip + " a.k.a " + sc.getName());
        sc.send(msg.getBytes());
    }
    

}
