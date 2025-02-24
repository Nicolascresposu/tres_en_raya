/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya;

/**
 *
 * @author nicol
 */
public class logicaTicTacToeBuilder {
    private logicaTicTacToe logicaTicTacToe;
    public logicaTicTacToeBuilder() {
        logicaTicTacToe = new logicaTicTacToe();
    }
    //Establece un valor en la grilla recibiendo la posicion en X (0) y Y (1)
    public logicaTicTacToeBuilder grilla(int[] posicion,String ingreso) {
        logicaTicTacToe.setGrilla(posicion[0], posicion[1], ingreso);
        return this;
    }
    //Solo debe recibir false (O) o true (X)
    public logicaTicTacToeBuilder turno(boolean turno) {
        if (turno==true)
            logicaTicTacToe.setTurnoActual("X");
        else
            logicaTicTacToe.setTurnoActual("O");
        return this;
    }
    
    
}
