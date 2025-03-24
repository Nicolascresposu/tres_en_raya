/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya;

import java.awt.Button;
import java.util.HashMap;
import java.util.Map;

public class ButtonMapper {
    private Map<Integer, Button> buttonMap;  // Maps keys (e.g., KeyEvent.VK_1) to Buttons
    private Map<String, Integer> keyNameToCodeMap;  // Maps key names (e.g., "1", "A") to key codes

    public ButtonMapper() {
        buttonMap = new HashMap<>();
        keyNameToCodeMap = new HashMap<>();
        initializeKeyMappings();
    }

    // Initialize default key mappings (e.g., "1" -> KeyEvent.VK_1, "A" -> KeyEvent.VK_A)
    private void initializeKeyMappings() {
        // Numbers
        keyNameToCodeMap.put("1", java.awt.event.KeyEvent.VK_1);
        keyNameToCodeMap.put("2", java.awt.event.KeyEvent.VK_2);
        // ... Add others as needed

        // Letters
        keyNameToCodeMap.put("A", java.awt.event.KeyEvent.VK_A);
        keyNameToCodeMap.put("B", java.awt.event.KeyEvent.VK_B);
        // ... Add others as needed
    }

    // Add a button mapped to a specific key code (e.g., KeyEvent.VK_1)
    public void addButton(int keyCode, Button button) {
        buttonMap.put(keyCode, button);
    }

    // Add a button mapped to a key name (e.g., "1" or "A")
    public void addButton(String keyName, Button button) {
        Integer keyCode = keyNameToCodeMap.get(keyName.toUpperCase());
        if (keyCode != null) {
            buttonMap.put(keyCode, button);
        } else {
            throw new IllegalArgumentException("Unknown key name: " + keyName);
        }
    }

    // Get the Button associated with a key code
    public Button getButton(int keyCode) {
        return buttonMap.get(keyCode);
    }
    
    public Integer getKeyCode(String keyName) {
    return keyNameToCodeMap.get(keyName.toUpperCase());
    }
}