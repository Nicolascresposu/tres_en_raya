/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.Comando;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author nicol
 */
@Getter
@Setter
public abstract class Comando {
    public String comando;
    public String ip;
    public abstract void parsear(String comando);
//    public abstract String getIp();
    public abstract String getComando();
}
