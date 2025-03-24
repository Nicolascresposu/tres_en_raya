/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonSimulator {
    private ButtonMapper buttonMapper;

    public ButtonSimulator(ButtonMapper buttonMapper) {
        this.buttonMapper = buttonMapper;
    }

    // Simulate a button press by key code (e.g., KeyEvent.VK_1)
    public void simulateButtonPress(int keyCode) {
        Button button = buttonMapper.getButton(keyCode);
        if (button != null) {
            // Simulate the button press by calling its ActionListener(s)
            ActionEvent event = new ActionEvent(button, ActionEvent.ACTION_PERFORMED, "Simulated");
            for (ActionListener listener : button.getActionListeners()) {
                listener.actionPerformed(event);
            }
        }
    }

    // Simulate a button press by key name (e.g., "1" or "A")
    public void simulateButtonPress(String keyName) {
        Integer keyCode = buttonMapper.getKeyCode(keyName);
        if (keyCode != null) {
            simulateButtonPress(keyCode);
        }
    }
}