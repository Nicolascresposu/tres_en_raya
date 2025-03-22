/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.Contacto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author nicol
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Contacto  implements Serializable{
    private String name;
    private String ip;
    private boolean stateConnect = false;

//    public Contacto(String name, String ip, boolean stateConnect) {
//        this.name = name;
//        this.ip = ip;
//        this.stateConnect = stateConnect;
//    }
    
}