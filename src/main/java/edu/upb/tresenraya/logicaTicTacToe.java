/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya;

/**
 *
 * @author nicol
 */
public class logicaTicTacToe {
    public String[][] grilla = new String[3][3]; //La grilla.
    public String turnoActual="X"; // El turno inicial, comenzamos en X.
    public String winner="";
    public void checkWinner() {
        int winnerCount=0;
        ////Verifica horizontales
        for (int i=0;i<3;i++) {
            for (int j=0;j<3;j++) {
                if (grilla[j][i] == "X") {
                winnerCount++;
                }
                if (grilla[j][i] == "O") {
                winnerCount--;
                }
                
            }
            if (winnerCount==3) {
                winner="X";
            }
            if (winnerCount==-3) {
                winner="O";
            }
            winnerCount=0;
        }
        //Verifica verticales
        for (int i=0;i<3;i++) {
            for (int j=0;j<3;j++) {
                if (grilla[i][j] == "X") {
                winnerCount++;
                }
                if (grilla[i][j] == "O") {
                winnerCount--;
                }
                
            }
            if (winnerCount==3) {
                winner="X";
            }
            if (winnerCount==-3) {
                winner="O";
            }
            winnerCount=0;
        }
        //Verificamos las 2 diagonales
            for (int j=2;j>-1;j--) {
                if (grilla[j][grilla.length-1-j] == "X") {
                winnerCount++;
                }
                if (grilla[j][j] == "O") {
                winnerCount--;
                }
                
            }
            if (winnerCount==3) {
                winner="X";
            }
            if (winnerCount==-3) {
                winner="O";
            }
            winnerCount=0;
            //Correspondiente a la diagonal arriba abajo, izquierda derecha
            for (int j=0;j<3;j++) {
                if (grilla[j][j] == "X") {
                winnerCount++;
                }
                if (grilla[j][j] == "O") {
                winnerCount--;
                }
                
            }
            if (winnerCount==3) {
                winner="X";
            }
            if (winnerCount==-3) {
                winner="O";
            }
            winnerCount=0;
        
        
    }
    public String cambiaTurnos(String ingreso) {
        // Recibe el turno actual y devuelve su opuesto respectivo, para usar en setGrilla
        if (ingreso.equals("X"))
            return "O";
        if (ingreso.equals("O"))
            return "X";
        else {
            System.out.println("Erorr fatal, no se ingreso correctamente alguno");
            return "⬛";
        }
            
        
                    
    }

    public void setTurnoActual(String turnoActual) {
        this.turnoActual = turnoActual;
    }
    
    public void setGrilla(int posX, int posY, String ingreso) {
        if (grilla[posX][posY] != "X" &&  grilla[posX][posY] != "O") {
            grilla[posX][posY] = ingreso;
            checkWinner();
            turnoActual = cambiaTurnos(ingreso);
            return;
        }
        else return;
    }
    public void godmodeSetGrilla(int posX, int posY, String ingreso) {
        if (grilla[posX][posY] != "X" &&  grilla[posX][posY] != "O") {
            grilla[posX][posY] = ingreso;
            checkWinner();
            return;
        }
        else return;
    }
}
