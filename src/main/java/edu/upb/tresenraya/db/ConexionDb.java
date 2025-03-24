/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.db;

import edu.upb.tresenraya.Contacto.Contactos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nicol
 */
public class ConexionDb {
//    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//    private String url="jdbc:sqlite:C:\\Users\\nicol\\OneDrive\\Documents\\Programming\\3enRaya10Feb\\3enraya11feb\\tres_en_raya-main\\src\\main\\java\\edu\\upb\\tresenraya\\db\\base.db\"";

    private String url="jdbc:sqlite:C:\\Users\\nicol\\OneDrive\\Documents\\Programming\\3enRaya10Feb\\3enraya11feb\\tres_en_raya-main\\src\\main\\java\\edu\\upb\\tresenraya\\db\\base.db";
    private String user="";
    private String contrasena="";
    private int puerto=0;
    private static ConexionDb db = new ConexionDb();
    private ConexionDb() {
        System.out.println("Iniciando");
    }
    public static ConexionDb instancia() {
        return db;
    }
    public Connection getConnection() {
        try {
            // Logica aqui

            
            System.out.println("Conectado~!");
            return DriverManager.getConnection(url);
        } catch (SQLException ex) {
            Logger.getLogger(ConexionDb.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
