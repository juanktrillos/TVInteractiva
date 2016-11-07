/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jkt.tv.data;

import com.panamahitek.PanamaHitek_Arduino;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juan.trillos
 */
public class Arduino implements SerialPortEventListener {

    PanamaHitek_Arduino arduino;
    String serialPort;
    String dato;

    @SuppressWarnings("LeakingThisInConstructor")
    public Arduino(String serialPort) {

        this.arduino = new PanamaHitek_Arduino();
        this.serialPort = serialPort;
        this.dato = "";
        try {
            arduino.arduinoRX(serialPort, 9600, this);
        } catch (Exception ex) {
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void serialEvent(SerialPortEvent ev) {
        if (arduino.isMessageAvailable()) {
            String cad = arduino.printMessage();
            dato = cad;
            System.out.println("Dato is:        " + dato);
        }
    }

}
